package griffmedia.ghazal.liveprint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.IOException;

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
            String found = Funcs.read(this, "CompaniesLinks.txt");
            System.out.println(found);
        } catch (FileNotFoundException e) {
            System.out.println("fnf exc");
        } catch (IOException e) {
            System.out.println("io exc");
        }
    }

    public void genRandLink(View v) {

        Data data = Data.getInstance();

        String randComp = "" + (10.0 * Math.random());
        String randPName = "" + (100.0 * Math.random());
        String randVName = "" + (100.0 * Math.random());
        Company c = new Company(randComp);
        Link l = new Link(randPName, randVName);
        c.addLink(l);
        data.addComp(c);
    }

    public void saveData(View v) {
        Funcs.saveFullData(this);
    }
}
