package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class NatTreasureHunt extends AppCompatActivity {

    // percentage of screen height that the gap should be
    private static final double gapPerc1 = 2.06;
    private static final double gapPerc2 = 3.62;
    private static final double gapPerc34 = 4.1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_nat_treasure_hunt);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView getBookTV = findViewById(R.id.get_book_text);
        getBookTV.setMovementMethod(LinkMovementMethod.getInstance());

        setFormatting();
    }

    public void openCamera(View v) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

    private void setFormatting() {
        View v1 = findViewById(R.id.th_empty1);
        View v2 = findViewById(R.id.th_empty2);
        View v3 = findViewById(R.id.th_empty3);
        View v4 = findViewById(R.id.th_empty4);

        setGapSizes(v1, v2, v3, v4);

        // versions before M have a different default background colour
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Funcs.setBGColour("#EEEEEE", v1, v2, v3, v4);
        }
    }

    private void setGapSizes(View v1, View v2, View v3, View v4) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        double fullWindowHeight = displayMetrics.heightPixels + 0.0;
        double density = displayMetrics.density;

        // add a dividing factor for screens with height < 1000 px
        if (fullWindowHeight < 1000)
            density /= 1.5;

        Funcs.setGapHeight(v1, Funcs.resizeHeight(gapPerc1, fullWindowHeight), density);
        Funcs.setGapHeight(v2, Funcs.resizeHeight(gapPerc2, fullWindowHeight), density);
        Funcs.setGapHeight(v3, Funcs.resizeHeight(gapPerc34, fullWindowHeight), density);
        Funcs.setGapHeight(v4, Funcs.resizeHeight(gapPerc34, fullWindowHeight), density);
    }

}
