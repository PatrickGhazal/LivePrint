package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Implements registration for companies.
 *
 * @author Patrick Ghazal
 * @version 1.0
 */
public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * <code>onClick</code> method for the <code>Register</code> button.
     * Creates a new company with the given credentials, if it doesn't exist yet.
     *
     * @param v view that contains the clicked button
     */
    public void registerCo(View v) {

        EditText companyET = (EditText) findViewById(R.id.lpc1b_company);
        EditText passwordET = (EditText) findViewById(R.id.lpc1b_password);

        if (validCreds(companyET, passwordET)) {

            Data data = Data.getInstance();

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
                Funcs.saveFullData(this);

                Intent loginIntent = new Intent(this, Login.class);
                startActivity(loginIntent);
            } else {
                TextView errorTV = (TextView) findViewById(R.id.lpc1b_error);
                errorTV.setText(R.string.lpc1b_error_account_exists);
            }
        } else {
            TextView errorTV = (TextView) findViewById(R.id.lpc1b_error);
            errorTV.setText(R.string.lpc1b_error_invalid_creds);
        }
    }

    /**
     * Checks for the validity of the credentials.
     *
     * @param compET EditText instance containing the company name
     * @param pwET   EditText instance containing the password
     * @return true if the credentials are valid
     */
    private boolean validCreds(EditText compET, EditText pwET) {
        boolean valid = true;

        String comp = compET.getText().toString();
        String pw = pwET.getText().toString();

        if (comp.length() < 2)
            valid = false;

        if (pw.length() < 6)
            valid = false;

        return valid;
    }

    /**
     * <code>onClick</code> method for the <code>Back</code> button.
     * Calls <code>finish</code> on <code>this</code> Activity and launches the previous page (the one <code>this</code> was opened from).
     *
     * @param v view that contains the clicked button
     */
    public void backButton(View v) {
        Intent intent = new Intent(this, Login.class);
        finish();
        startActivity(intent);
    }

}
