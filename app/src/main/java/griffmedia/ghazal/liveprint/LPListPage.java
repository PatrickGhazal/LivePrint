package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class LPListPage extends AppCompatActivity {

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

    public void openCamera(View v) {

        Data data = Data.getInstance();

        Spinner spinner = (Spinner) findViewById(R.id.comp_spinner);
        String selectedCompStr = String.valueOf(spinner.getSelectedItem());
        Company selectedComp = data.getCompByName(selectedCompStr);
        if (selectedComp != null) {
            chosenCompany = selectedComp;
            System.out.println(selectedCompStr);
        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

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

}
