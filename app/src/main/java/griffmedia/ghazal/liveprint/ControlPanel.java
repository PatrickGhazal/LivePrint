package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
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

    private static final double gapPerc = 0.88;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_control_panel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFormatting();
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
     * Sets the gap sizes and background colour.
     */
    private void setFormatting() {

        View v1 = findViewById(R.id.lpc2_empty1);
        View v2 = findViewById(R.id.lpc2_empty2);
        View v3 = findViewById(R.id.lpc2_empty3);

        setGapSizes(v1, v2, v3);

        // versions before M have a different default background colour
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Funcs.setBGColour(Data.bgColourBeforeM, v1, v2, v3);
        }

        dispExistingLinks();
    }

    /**
     * Sets each gap size.
     *
     * @param v1 first gap
     * @param v2 second gap
     * @param v3 third gap
     */
    private void setGapSizes(View v1, View v2, View v3) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        double fullWindowHeight = displayMetrics.heightPixels + 0.0;
        double density = displayMetrics.density;

        // add a dividing factor for screens with height < 1000 px
        if (fullWindowHeight < 1000)
            density /= 1.5;

        Funcs.setGapHeight(v1, Funcs.resizeHeight(gapPerc, fullWindowHeight), density);
        Funcs.setGapHeight(v2, Funcs.resizeHeight(gapPerc, fullWindowHeight), density);
        Funcs.setGapHeight(v3, Funcs.resizeHeight(gapPerc, fullWindowHeight), density);
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
