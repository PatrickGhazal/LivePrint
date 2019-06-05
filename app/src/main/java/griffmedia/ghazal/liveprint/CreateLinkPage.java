package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class CreateLinkPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_create_link_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

            String link = selectedImage + "/" + selectedVideo;

            View inflatedView = getLayoutInflater().inflate(R.layout.activity_control_panel, null);
            TextView tvExistingLinks = (TextView) inflatedView.findViewById(R.id.existing_links);

            String currText = tvExistingLinks.getText().toString();
            tvExistingLinks.setText(currText + link + "\n");

            finish();

//            Intent intent = new Intent(this, ControlPanel.class);
//            startActivity(intent);
        } else {
            TextView tvSuccess = findViewById(R.id.create_success);
            tvSuccess.setText("Failure..");
        }
    }

}
