package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

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

    public void openCreateLink(View v) {
        Intent intent = new Intent(this, CreateLinkPage.class);
        startActivity(intent);
    }

    private void setFormatting() {

        View v1 = findViewById(R.id.lpc2_empty1);
        View v2 = findViewById(R.id.lpc2_empty2);

        setGapSizes(v1, v2);

        // versions before M have a different default background colour
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Funcs.setBGColour(Data.bgColourBeforeM, v1, v2);
        }

        dispExistingLinks();
    }

    private void setGapSizes(View v1, View v2) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        double fullWindowHeight = displayMetrics.heightPixels + 0.0;
        double density = displayMetrics.density;

        // add a dividing factor for screens with height < 1000 px
        if (fullWindowHeight < 1000)
            density /= 1.5;

        Funcs.setGapHeight(v1, Funcs.resizeHeight(gapPerc, fullWindowHeight), density);
        Funcs.setGapHeight(v2, Funcs.resizeHeight(gapPerc, fullWindowHeight), density);

    }

    private void dispExistingLinks() {
        TextView linksTV = (TextView) findViewById(R.id.lpc2_existing_links);
        TextView linksTitleTV = (TextView) findViewById(R.id.lpc2_existing_links_title);
        linksTitleTV.setTypeface(null, Typeface.BOLD);
        String linksDisp = "";
        Company comp = LoginPage.currLoggedInComp;
        if (comp != null) {
            linksTitleTV.setText("Links of " + comp.getName() + ":");
            if (comp.getLinks().size() == 0) {
                linksDisp += "No existing links.";
            }
            for (Link link : comp.getLinks()) {
                linksDisp += (link.toString() + "\n");
            }
        } else {
            linksDisp = "No company logged in.";
        }
        linksTV.setText(linksDisp);
    }

}
