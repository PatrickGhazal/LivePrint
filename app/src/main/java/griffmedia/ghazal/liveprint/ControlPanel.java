package griffmedia.ghazal.liveprint;

import android.content.Intent;
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

        resetExistingLinks();
    }

    public void openCreateLink(View v) {
        Intent intent = new Intent(this, CreateLinkPage.class);
        startActivity(intent);
    }

    private void resetExistingLinks() {
        TextView tvExistingLinks = findViewById(R.id.existing_links);
        tvExistingLinks.setText(Funcs.linksToString("defaultCo"));
    }

    private void setFormatting() {
        // the four gaps
        View v1 = findViewById(R.id.lpc2_empty1);
        View v2 = findViewById(R.id.lpc2_empty2);

        setGapSizes(v1, v2);

        // versions before M have a different default background colour
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Funcs.setBGColour("#EEEEEE", v1, v2);
        }
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

}
