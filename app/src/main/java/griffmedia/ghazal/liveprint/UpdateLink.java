package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class UpdateLink extends AppCompatActivity {

    public static Company currComp = null;
    public static Link currLink = null;

    private static final double gapPerc = 1.75;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_update_link);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setup();
    }

    private void setup() {

        setFormatting();

        if (currComp != null && currLink != null) {
            TextView introTV = (TextView) findViewById(R.id.lpc3b_intro_tv);
            introTV.setText("Currently updating Link " + currLink.toString() + "\nof Company " + currComp.getName() + "\n");
        } else {
            finish();
        }
    }

    public void updateLink(View v) {
        EditText photoNameET = (EditText) findViewById(R.id.lpc3b_update_photo_name_et);
        EditText videoNameET = (EditText) findViewById(R.id.lpc3b_update_video_name_et);

        String newPhotoName = photoNameET.getText().toString();
        String newVideoName = videoNameET.getText().toString();

        Link newLink = null;

        for (Link compLink : currComp.getLinks()) {
            if (compLink.identical(currLink)) {
                newLink = new Link(compLink.getPhotoName(), compLink.getVideoName());
                currComp.removeLink(compLink);
                if (!newPhotoName.equals("")) {
                    newLink.setPhotoName(newPhotoName);
                }
                if (!newVideoName.equals("")) {
                    newLink.setVideoName(newVideoName);
                }
            }
            break;
        }

        if (newLink != null) {
            currComp.addLink(newLink);
        }

        Funcs.saveFullData(this);

        Intent intent = new Intent(this, ControlPanel.class);
        startActivity(intent);

    }

    public void deleteLink(View v) {
        currComp.removeLink(currLink);
        Funcs.saveFullData(this);
        Intent intent = new Intent(this, ControlPanel.class);
        startActivity(intent);
    }

    private void setFormatting() {
        View v1 = findViewById(R.id.lpc3b_empty1);
        View v2 = findViewById(R.id.lpc3b_empty2);

        setGapSizes(v1, v2);

        // versions before M have a different default background colour
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Funcs.setBGColour(Data.bgColourBeforeM, v1, v2);
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
