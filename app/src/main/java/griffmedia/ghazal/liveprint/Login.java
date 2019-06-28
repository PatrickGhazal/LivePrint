package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

/**
 * Implements login for companies.
 *
 * @author Patrick Ghazal
 * @version 1.0
 */
public class Login extends AppCompatActivity {

    public static Company currLoggedInComp = null;

    private static final double gapPerc = 1.75;

    private static ArrayList<String> credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFormatting();

        loadCredentials();
    }

    /**
     * Loads the credentials from the company data.
     */
    private void loadCredentials() {
        Data data = Data.getInstance();

        credentials = new ArrayList<String>();

        for (Company comp : data.getPartnerComps()) {
            credentials.add(comp.getName() + "---" + comp.getPassword());
        }
    }

    /**
     * Checks for the existence of the provided credentials.
     *
     * @param user provided username
     * @param pass provided password
     * @return true if the credentials exist
     */
    public boolean validLogin(String user, String pass) {
        String cred = user + "---" + pass;
        return credentials.contains(cred);
    }

    /**
     * <code>onClick</code> method for the <code>Login</code> button.
     * Attempts to login based on the provided credentials.
     *
     * @param v view that contains the clicked button
     */
    public void login(View v) {
        EditText etUser = (EditText) findViewById(R.id.lp_login_user);
        EditText etPass = (EditText) findViewById(R.id.lp_login_pass);

        String givenUser = etUser.getText().toString().trim();
        String givenPass = etPass.getText().toString().trim();

        boolean valid = validLogin(givenUser, givenPass);

        if (valid) {

            Data data = Data.getInstance();

            currLoggedInComp = data.getCompByName(givenUser);
            Intent intent = new Intent(this, ControlPanel.class);
            startActivity(intent);
        } else {
            TextView tvError = (TextView) findViewById(R.id.login_error);
            tvError.setText("Credentials unrecognized. Please try again.");
        }
    }

    /**
     * <code>onClick</code> method for the <code>Override</code> button.
     * Opens the Control Panel without a logged-in company for testing purposes.
     *
     * @param v view that contains the clicked button
     */
    public void override(View v) {
        currLoggedInComp = null;
        Intent intent = new Intent(this, ControlPanel.class);
        startActivity(intent);
    }

    /**
     * <code>onClick</code> method for the <code>Register</code> button.
     * Opens the <code>Registration</code> page.
     *
     * @param v view that contains the clicked button
     */
    public void register(View v) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    /**
     * Sets the gap sizes and background colour.
     */
    private void setFormatting() {
        View v1 = findViewById(R.id.lpc1_empty1);
        View v2 = findViewById(R.id.lpc1_empty2);
        View v3 = findViewById(R.id.lpc1_empty3);

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
        Intent intent = new Intent(this, LPHome.class);
        finish();
        startActivity(intent);
    }

}