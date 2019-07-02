package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Control Panel for companies to create/update/delete links.
 *
 * @author Patrick Ghazal
 * @version 1.0
 */
public class ControlPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_control_panel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dispExistingLinks();
    }

    /**
     * <code>onClick</code> method for the <code>Create Link</code> button.
     * Opens the <code>Create Link</code> page.
     *
     * @param v view that contains the clicked button
     */
    public void openCreateLink(View v) {
        Intent intent = new Intent(this, CreateLink.class);
        startActivity(intent);
    }

    /**
     * Displays the existing links for the relevant company.
     */
    private void dispExistingLinks() {
        TextView linksTV = (TextView) findViewById(R.id.lpc2_existing_links);
        TextView linksTitleTV = (TextView) findViewById(R.id.lpc2_existing_links_title);
        linksTitleTV.setTypeface(null, Typeface.BOLD);
        String linksDisp = "";
        Company comp = Login.currLoggedInComp;
        if (comp != null) {
            linksTitleTV.setText("Links of " + comp.getName() + ":");
            if (comp.getLinks().size() == 0) {
                linksDisp += "No existing links.";
            }
            int counter = 1;
            for (Link link : comp.getLinks()) {
                linksDisp += ("" + counter + ") " + link.toString() + "\n");
                counter++;
            }
        } else {
            linksDisp = "No company logged in.";
        }
        linksTV.setText(linksDisp);
    }

    /**
     * <code>onClick</code> method for the <code>Update Link</code> button.
     * Opens the <code>Update Link</code> page for the chosen link.
     *
     * @param v view that contains the clicked button
     */
    public void updateLink(View v) {
        EditText linkNumET = (EditText) findViewById(R.id.lpc2_update_link_value);
        String linkNumStr = linkNumET.getText().toString();
        int linkNum = 0;
        try {
            linkNum = Integer.parseInt(linkNumStr);
        } catch (NumberFormatException e) {
            TextView errorTV = (TextView) findViewById(R.id.lpc2_link_num_error);
            errorTV.setText("Link value invalid.");
        }
        Company currComp = Login.currLoggedInComp;
        if (currComp != null) {
            try {
                Link link = currComp.getLinks().get(linkNum - 1); // -1 because they're listed 1, 2, 3 not 0, 1, 2
                UpdateLink.currComp = currComp;
                UpdateLink.currLink = link;
                Intent intent = new Intent(this, UpdateLink.class);
                startActivity(intent);
            } catch (IndexOutOfBoundsException e) {
                TextView errorTV = (TextView) findViewById(R.id.lpc2_link_num_error);
                errorTV.setText("Link value invalid.");
            }

        }
    }

    /**
     * <code>onClick</code> method for the <code>Back</code> button.
     * Calls <code>finish</code> on <code>this</code> Activity and launches the previous page (the one <code>this</code> was opened from).
     *
     * @param v view that contains the clicked button
     */
    public void backButton(View v) {
        Intent intent = new Intent(this, Login.class);
        finish();
        startActivity(intent);
    }
}
