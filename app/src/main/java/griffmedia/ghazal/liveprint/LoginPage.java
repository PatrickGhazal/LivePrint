package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;

import java.io.File;
import java.io.FileNotFoundException;
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

    private static final double gapPerc = 1.75;

    private static List<String> credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFormatting();

        //fillCredentials();
        //hardcodeCreds();
    }

    private void hardcodeCreds() {
        credentials = new ArrayList<String>();

        credentials.add("u1-hello");
        credentials.add("u2-mypass");
        credentials.add("u3-paq");

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
            Funcs.setBGColour("#EEEEEE", v1, v2, v3, v4);
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
