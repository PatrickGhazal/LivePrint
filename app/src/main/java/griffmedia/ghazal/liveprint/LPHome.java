package griffmedia.ghazal.liveprint;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

/**
 * Home Page of the LivePrint section of the app.
 *
 * @author Patrick Ghazal
 * @version 1.0
 */
public class LPHome extends AppCompatActivity {

    private static String currLang = "en";

    private static ArrayList<Link> links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_lp_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * <code>onClick</code> method for the <code>Start LP</code> button.
     * Opens the <code>LPList</code> page.
     *
     * @param v view that contains the clicked button
     */
    public void openCompanyList(View v) {
        Funcs.startActivityFunc(this, LPList.class);
    }

    /**
     * <code>onClick</code> method for the <code>Login</code> button.
     * Opens the <code>Login</code> page.
     *
     * @param v view that contains the clicked button
     */
    public void openLogin(View v) {
        Funcs.startActivityFunc(this, Login.class);
    }

    /**
     * <code>onClick</code> method for the language switch button.
     * Updates the language of the app from English to French or opposite.
     *
     * @param v view that contains the clicked button
     */
    public void updateLang(View v) {
        String lg = (currLang.equals("en") ? "fr" : "en");
        currLang = lg;
        updateLanguage(lg);
    }

    /**
     * Updates the language of the app.
     *
     * @param lg two-letter language code
     */
    private void updateLanguage(String lg) {

        Locale locale = new Locale(lg);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        recreate();
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
