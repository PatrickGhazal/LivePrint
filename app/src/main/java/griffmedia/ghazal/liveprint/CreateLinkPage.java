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

public class CreateLinkPage extends AppCompatActivity {

    private static final double gapPerc1 = 1.31;
    private static final double gapPerc24 = 0.88;
    private static final double gapPerc3 = 2.62;
    private static final double gapPerc5 = 4.38;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_create_link_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFormatting();
    }

    public void loadPhoto(View v) {
        TextView tvImage = findViewById(R.id.selected_image);
        tvImage.setText("chosen_image.jpg");
    }

    public void loadVideo(View v) {
        TextView tvVideo = findViewById(R.id.selected_video);
        tvVideo.setText("chosen_video.mp4");
    }

    public void createLink(View v) {
        TextView tvImage = findViewById(R.id.selected_image);
        TextView tvVideo = findViewById(R.id.selected_video);

        String selectedImage = tvImage.getText().toString();
        String selectedVideo = tvVideo.getText().toString();

        if (!selectedImage.equals("") && !selectedVideo.equals("")) {

            Link newLink = new Link(selectedImage, selectedVideo, "defaultCo");
            LPHomePage.addLink(newLink);
//            Funcs.writeToFile(this, "allLinks.txt", newLink.toString());
//            LPHomePage.setFileContext(this);

            Intent intent = new Intent(this, ControlPanel.class);
            startActivity(intent);
        } else {
            TextView tvSuccess = findViewById(R.id.create_success);
            tvSuccess.setText("Failure..");
        }
    }

    private void setFormatting() {
        View v1 = findViewById(R.id.lpc3_empty1);
        View v2 = findViewById(R.id.lpc3_empty2);
        View v3 = findViewById(R.id.lpc3_empty3);
        View v4 = findViewById(R.id.lpc3_empty4);
        View v5 = findViewById(R.id.lpc3_empty5);

        setGapSizes(v1, v2, v3, v4, v5);

        // versions before M have a different default background colour
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Funcs.setBGColour("#EEEEEE", v1, v2, v3, v4, v5);
        }
    }

    private void setGapSizes(View v1, View v2, View v3, View v4, View v5) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        double fullWindowHeight = displayMetrics.heightPixels + 0.0;
        double density = displayMetrics.density;

        // add a dividing factor for screens with height < 1000 px
        if (fullWindowHeight < 1000)
            density /= 1.5;

        Funcs.setGapHeight(v1, Funcs.resizeHeight(gapPerc1, fullWindowHeight), density);
        Funcs.setGapHeight(v2, Funcs.resizeHeight(gapPerc24, fullWindowHeight), density);
        Funcs.setGapHeight(v3, Funcs.resizeHeight(gapPerc3, fullWindowHeight), density);
        Funcs.setGapHeight(v4, Funcs.resizeHeight(gapPerc24, fullWindowHeight), density);
        Funcs.setGapHeight(v5, Funcs.resizeHeight(gapPerc5, fullWindowHeight), density);
    }

}
