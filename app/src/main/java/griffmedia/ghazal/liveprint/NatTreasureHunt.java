package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Home Page of the National Treasure Hunt section of the app.
 *
 * @author Patrick Ghazal
 * @version 1.0
 */
public class NatTreasureHunt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_nat_treasure_hunt);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView getBookTV = findViewById(R.id.get_book_text);
        getBookTV.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * <code>onClick</code> method for the <code>Open Camera</code> button.
     * TODO: Update the Javadoc when the method is properly implemented
     *
     * @param v view that contains the clicked button
     */
    public void openCamera(View v) {

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);

    }

    /**
     * <code>onClick</code> method for the <code>Back</code> button.
     * Calls <code>finish</code> on <code>this</code> Activity and launches the previous page (the one <code>this</code> was opened from).
     *
     * @param v view that contains the clicked button
     */
    public void backButton(View v) {
        Funcs.startActivityFunc(this, MainActivity.class);
    }
}
