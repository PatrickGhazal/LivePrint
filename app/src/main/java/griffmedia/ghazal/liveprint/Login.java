package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class Login extends AppCompatActivity {

    private static final String credentialsPath = "/src/res/credentials.txt";
    private static List<String> credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fillCredentials();
    }

    private void fillCredentials() {

        credentials = new ArrayList<String>();

        try {
            File credFiles = new File(credentialsPath);
            FileReader fr = new FileReader(credFiles);
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            while (line != null) {
                credentials.add(line);
                line = br.readLine();
            }

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

    }

    public static List<String> getCredentials() {
        return credentials;
    }

    public boolean validLogin(String user, String pass) {
        return getCredentials().contains(user += "-" + pass);
    }

    public void login(View v) {
        EditText etUser = (EditText) findViewById(R.id.lp_login_user);
        EditText etPass = (EditText) findViewById(R.id.lp_login_pass);

        String givenUser = etUser.getText().toString();
        String givenPass = etPass.getText().toString();

        boolean valid = validLogin(givenUser, givenPass);

        if (valid) {
            Intent intent = new Intent(this, ControlPanel.class);
            startActivity(intent);
        }
    }

}
