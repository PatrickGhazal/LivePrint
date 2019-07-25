package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

/**
 * Allows the user to specify the company they are using LivePrint for.
 *
 * @author Patrick Ghazal
 * @version 1.0
 */
public class LPList extends AppCompatActivity {

    public static Company chosenCompany = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_lp_list_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupSpinner();
    }

    /**
     * <code>onClick</code> method for the <code>Open Camera</code> button.
     * Identifies the selected company and opens the camera to detect links of that company.
     *
     * @param v view that contains the clicked button
     */
    public void openCamera(View v) {

        Data data = Data.getInstance();

        Spinner spinner = (Spinner) findViewById(R.id.comp_spinner);
        String selectedCompStr = String.valueOf(spinner.getSelectedItem());
        Company selectedComp = data.getCompByName(selectedCompStr);
        if (selectedComp != null) {
            chosenCompany = selectedComp;
        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

    /**
     * Fills the spinner with company names.
     */
    private void setupSpinner() {

        Data data = Data.getInstance();

        ArrayList<String> compNames = new ArrayList<String>();

        for (Company comp : data.getPartnerComps()) {
            compNames.add(comp.getName());
        }

        Spinner spinner = (Spinner) findViewById(R.id.comp_spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, compNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    /**
     * <code>onClick</code> method for the <code>Back</code> button.
     * Calls <code>finish</code> on <code>this</code> Activity and launches the previous page (the one <code>this</code> was opened from).
     *
     * @param v view that contains the clicked button
     */
    public void backButton(View v) {
        Funcs.startActivityFunc(this, LPHome.class);
    }

}
