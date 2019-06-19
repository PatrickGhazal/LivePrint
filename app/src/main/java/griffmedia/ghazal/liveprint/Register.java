package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void registerCo(View v) {

        Data data = Data.getInstance();

        EditText companyET = (EditText) findViewById(R.id.lpc1b_company);
        EditText passwordET = (EditText) findViewById(R.id.lpc1b_password);

        String compName = companyET.getText().toString();
        boolean exists = false;
        for (Company co : data.getPartnerComps()) {
            if (co.getName().equals(compName)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            Company newCo = new Company(compName);
            newCo.setPassword(passwordET.getText().toString());
            data.addComp(newCo);

            Intent loginIntent = new Intent(this, LoginPage.class);
            startActivity(loginIntent);
        } else {
            TextView errorTV = (TextView) findViewById(R.id.lpc1b_error);
            errorTV.setText("Error: this company already has a registered account.");
        }
    }

}
