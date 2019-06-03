package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class LPListPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_lp_list_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void openCamera(View v) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

}
