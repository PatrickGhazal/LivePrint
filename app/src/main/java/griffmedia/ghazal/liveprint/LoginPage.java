package griffmedia.ghazal.liveprint;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import java.util.Scanner;

import io.fabric.sdk.android.Fabric;

public class LoginPage extends AppCompatActivity {


    //TODO: fix fillCredentials method, make path relative, remove hardcodeCreds
    private static final String fileSep = System.getProperty("file.separator");
//    private static final String credentialsPath = fileSep + "src" + fileSep + "main" + fileSep + "creds.txt";
    //private static final String credentialsPath = "C:\\Users\\Admin\\android-workspace\\LivePrint\\app\\src\\main\\creds.txt";
    private static final String credentialsPath = "src\\main\\creds.txt";

    private static List<String> credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fillCredentials();
        //hardcodeCreds();
    }

    private void hardcodeCreds() {
        credentials = new ArrayList<String>();

        credentials.add("u1-hello");
        credentials.add("u2-mypass");
        credentials.add("u3-paq");

        View empty = findViewById(R.id.empty2);
        empty.setBackgroundColor(Color.parseColor("#00FF00"));
    }

//    private void fillCredentials() {
//
//        credentials = new ArrayList<String>();
//
//        try {
//            FileReader fr = new FileReader(credentialsPath);
//            BufferedReader br = new BufferedReader(fr);
//
//            String line = br.readLine();
//            while (line != null) {
//                System.out.println("------------------------------");
//                System.out.println(line);
//                System.out.println("------------------------------");
//                credentials.add(line);
//                line = br.readLine();
//            }
//
//        } catch (FileNotFoundException e) {
//            System.out.println("file not found");
//
//        } catch (IOException e) {
//            System.out.println("io exception");
//        }
//
//        View empty = findViewById(R.id.empty2);
//        empty.setBackgroundColor(Color.parseColor("#FF0000"));
//
//    }

    private void fillCredentials() {

        try {
            File file = new File(credentialsPath);
            System.out.println("----------------------" + file.createNewFile());
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println("--------------" + line + "----------");
                credentials.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("file was not found");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public boolean validLogin(String user, String pass) {
        String cred = user + "-" + pass;
        return credentials.contains(cred);
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

    public void override(View v) {
        Intent intent = new Intent(this, ControlPanel.class);
        startActivity(intent);
    }

}
