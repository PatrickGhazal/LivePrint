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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import io.fabric.sdk.android.Fabric;

public class LoginPage extends AppCompatActivity {

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

    private void loadCredentials() {
        Data data = Data.getInstance();

        credentials = new ArrayList<String>();

        for (Company comp : data.getPartnerComps()) {
            credentials.add(comp.getName() + "---" + comp.getPassword());
        }
    }

    public boolean validLogin(String user, String pass) {
        String cred = user + "---" + pass;
        return credentials.contains(cred);
    }

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

    public void override(View v) {
        Intent intent = new Intent(this, ControlPanel.class);
        startActivity(intent);
    }

    public void register(View v) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private void setFormatting() {
        View v1 = findViewById(R.id.lpc1_empty1);
        View v2 = findViewById(R.id.lpc1_empty2);
        View v3 = findViewById(R.id.lpc1_empty3);
        View v4 = findViewById(R.id.lpc1_empty4);

        setGapSizes(v1, v2, v3, v4);

        // versions before M have a different default background colour
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Funcs.setBGColour(Data.bgColourBeforeM, v1, v2, v3, v4);
        }
    }

    private void setGapSizes(View v1, View v2, View v3, View v4) {
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
        Funcs.setGapHeight(v4, Funcs.resizeHeight(gapPerc, fullWindowHeight), density);
    }

}
