package griffmedia.ghazal.liveprint;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
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

    private void setFormatting() {
        // the four gaps
        View v1 = findViewById(R.id.empty_block_1);
        View v2 = findViewById(R.id.empty_block_2);
        View v3 = findViewById(R.id.empty_block_3);
        View v4 = findViewById(R.id.empty_block_4);

        setGapSizes(v1, v2, v3, v4);

        // versions before M have a different default background colour
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            v1.setBackgroundColor(Color.parseColor("#EEEEEE"));
            v2.setBackgroundColor(Color.parseColor("#EEEEEE"));
            v3.setBackgroundColor(Color.parseColor("#EEEEEE"));
            v4.setBackgroundColor(Color.parseColor("#EEEEEE"));
        } else {
            v1.setBackgroundColor(Color.parseColor("#FAFAFA"));
            v2.setBackgroundColor(Color.parseColor("#FAFAFA"));
            v3.setBackgroundColor(Color.parseColor("#FAFAFA"));
            v4.setBackgroundColor(Color.parseColor("#FAFAFA"));
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

        setGapHeight(v1, resizeHeight(gapPerc1, fullWindowHeight), density);
        setGapHeight(v2, resizeHeight(gapPerc2, fullWindowHeight), density);
        setGapHeight(v3, resizeHeight(gapPerc34, fullWindowHeight), density);
        setGapHeight(v4, resizeHeight(gapPerc34, fullWindowHeight), density);
    }

    // compute height from percentage of total height
    private int resizeHeight(double gapPerc, double windowHeight) {
        return (int) Math.round(gapPerc * windowHeight / 100.0);
    }

    // set the newly computed height
    private void setGapHeight(View view, int heightVal, double density) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) Math.round(heightVal * density);
        view.setLayoutParams(layoutParams);
    }

}
