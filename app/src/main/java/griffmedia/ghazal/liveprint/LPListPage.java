package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class LPListPage extends AppCompatActivity {

    //placeholder until we can extract companies
    private static final String[] companies = {"Apple", "Samsung", "Google"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_lp_list_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setCompNames();

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.content_lp_list_element, companies);

        ListView listView = (ListView) findViewById(R.id.companies_list);
        listView.setAdapter(adapter);
    }

    public void openCamera(View v) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

    private void setCompNames() {
        ArrayList<String> compNames = Funcs.extractCompNames();
        //TODO TextView was changed to ListView, finish impl
        //TextView tvCompNames = findViewById(R.id.comp_names);
        String allComps = "";

        for (String company : compNames) {
            allComps += (company + "\n");
        }

        //tvCompNames.setText(allComps);

    }

}
