package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

/**
 * Home Page of the LivePrint section of the app.
 *
 * @author Patrick Ghazal
 * @version 1.0
 */
public class LPHome extends AppCompatActivity {

    private static final double gapPerc = 1.75;

    private static ArrayList<Link> links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_lp_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFormatting();
    }

    /**
     * <code>onClick</code> method for the <code>Start LP</code> button.
     * Opens the <code>LPList</code> page.
     *
     * @param v view that contains the clicked button
     */
    public void openCompanyList(View v) {
        Intent intent = new Intent(this, LPList.class);
        startActivity(intent);
    }

    /**
     * <code>onClick</code> method for the <code>Login</code> button.
     * Opens the <code>Login</code> page.
     *
     * @param v view that contains the clicked button
     */
    public void openLogin(View v) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    /**
     * Sets the gap sizes and background colour.
     */
    private void setFormatting() {

        links = new ArrayList<Link>();

        View v1 = findViewById(R.id.lp0_empty1);
        View v2 = findViewById(R.id.lp0_empty2);
        View v3 = findViewById(R.id.lp0_empty3);

        setGapSizes(v1, v2, v3);

        // versions before M have a different default background colour
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Funcs.setBGColour(Data.bgColourBeforeM, v1, v2, v3);
        }
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
     * <code>onClick</code> method for the <code>Back</code> button.
     * Calls <code>finish</code> on <code>this</code> Activity and launches the previous page (the one <code>this</code> was opened from).
     *
     * @param v view that contains the clicked button
     */
    public void backButton(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
