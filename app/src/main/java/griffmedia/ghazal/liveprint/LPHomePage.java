package griffmedia.ghazal.liveprint;

import android.content.Context;
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

public class LPHomePage extends AppCompatActivity {

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

    public void openCompanyList(View v) {
        Intent intent = new Intent(this, LPListPage.class);
        startActivity(intent);
    }

    public void openLogin(View view) {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }

    private void setFormatting() {

        links = new ArrayList<Link>();

        View v1 = findViewById(R.id.lp0_empty1);
        View v2 = findViewById(R.id.lp0_empty2);
        View v3 = findViewById(R.id.lp0_empty3);

        setGapSizes(v1, v2, v3);

        // versions before M have a different default background colour
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Funcs.setBGColour("#EEEEEE", v1, v2, v3);
        }
    }

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

    public static ArrayList<Link> getLinks() {
        return links;
    }

    public static void addLink(Link link) {
        links.add(link);
    }
}
