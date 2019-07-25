package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

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

        String givenCompany = companyET.getText().toString();
        String givenPassword = passwordET.getText().toString();

        if (validCreds(givenCompany, givenPassword)) {

            Data data = Data.getInstance();

            Company newCo = new Company(givenCompany);
            newCo.setPassword(givenPassword);
            data.addComp(newCo);
            Funcs.saveFullData(this);

            Intent loginIntent = new Intent(this, Login.class);
            startActivity(loginIntent);

        }
    }

    /**
     * Checks for the validity of the credentials.
     *
     * @param company  String containing the company name
     * @param password String containing the password
     * @return true if the credentials are valid
     */
    private boolean validCreds(String company, String password) {

        TextView errorTV = (TextView) findViewById(R.id.lpc1b_error);
        errorTV.setText("");

        boolean valid = true;

        if (company.length() < 2 || password.length() < 6) {
            String newErrorMessage = errorTV.getText().toString() + "\n" + getString(R.string.lpc1b_error_invalid_creds);
            errorTV.setText(newErrorMessage);
            valid = false;
        }

        Data data = Data.getInstance();

        if (data.getCompByName(company) != null) {
            String newErrorMessage = errorTV.getText().toString() + "\n" + getString(R.string.lpc1b_error_account_exists);
            errorTV.setText(newErrorMessage);
            valid = false;
        }

        ArrayList<String> allClientComps = Funcs.readClientCompaniesFile(this);
        boolean found = false;
        for (String comp : allClientComps) {
            if (company.equals(comp)) {
                found = true;
                break;
            }
        }

        if (!found) {
            String newErrorMessage = errorTV.getText().toString() + "\n" + getString(R.string.lpc1b_error_not_client);
            errorTV.setText(newErrorMessage);
            valid = false;
        }

        return valid;
    }

    /**
     * <code>onClick</code> method for the <code>Back</code> button.
     * Calls <code>finish</code> on <code>this</code> Activity and launches the previous page (the one <code>this</code> was opened from).
     *
     * @param v view that contains the clicked button
     */
    public void backButton(View v) {
        Funcs.startActivityFunc(this, Login.class);
    }

}
