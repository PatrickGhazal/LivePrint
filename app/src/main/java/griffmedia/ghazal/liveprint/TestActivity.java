package griffmedia.ghazal.liveprint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class TestActivity extends AppCompatActivity {

    private static final String testString = "Hello World !";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void testBehaviour(View v) {
        try {
            String found = Funcs.read(this, "sample.txt");
            System.out.println(found.equals(testString));
        } catch (FileNotFoundException e) {
            System.out.println("fnf exc");
        } catch (IOException e) {
            System.out.println("io exc");
        }
    }
}
