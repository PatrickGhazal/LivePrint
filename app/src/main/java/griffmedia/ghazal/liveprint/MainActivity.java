package griffmedia.ghazal.liveprint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Entry-point into the app.
 *
 * @author Patrick Ghazal
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Funcs.setup(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * <code>onClick</code> method for the <code>Open LP</code> button.
     * Opens the <code>LPHome</code> page.
     *
     * @param v view that contains the clicked button
     */
    public void openLP(View v) {
        Funcs.startActivityFunc(this, LPHome.class);
    }

    /**
     * <code>onClick</code> method for the <code>Open TH</code> button.
     * Opens the <code>NatTreasureHunt</code> page.
     *
     * @param v view that contains the clicked button
     */
    public void openTH(View v) {
        Funcs.startActivityFunc(this, NatTreasureHunt.class);
    }

    /**
     * <code>onClick</code> method for the <code>Open Test</code> button.
     * Opens the <code>TestActivity</code> page.
     *
     * @param v view that contains the clicked button
     */
    public void openTest(View v) {
        Funcs.startActivityFunc(this, TestActivity.class);
    }

    /**
     * <code>onClick</code> method for the <code>Open Demo</code> button.
     * Opens the <code>Demo</code> page.
     *
     * @param v view that contains the clicked button
     */
    public void demoButton(View v) {
        Funcs.startActivityFunc(this, Demo.class);
    }

}