package griffmedia.ghazal.liveprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void testBehaviour(View v) {
        try {
            String found = Funcs.read(this, Data.compLinksFileName);
            System.out.println(found);
        } catch (FileNotFoundException e) {
            System.out.println("fnf exc");
        } catch (IOException e) {
            System.out.println("io exc");
        }

    }

    public void genRandLink(View v) {

        Data data = Data.getInstance();

        String randComp = "RandComp" + randNum();
        String randPName = "RandPN" + randNum();
        String randVName = "RandVN" + randNum();
        String randPW = "RandPW" + randNum();
        Company c = new Company(randComp);
        c.setPassword(randPW);
        Link l = new Link(randPName, randVName);
        c.addLink(l);
        data.addComp(c);
    }

    private int randNum() {
        return (int) Math.floor(100.0 * Math.random());
    }

    public void saveData(View v) {
        Funcs.saveFullData(this);
    }

    public void eraseData(View v) {
        try {
            Funcs.write(this, Data.compLinksFileName, "", false);
            Data data = Data.getInstance();
            data.eraseAllData();
        } catch (FileNotFoundException e) {
            System.out.println("fnf exc");
        } catch (IOException e) {
            System.out.println("io exc");
        }
    }

    public void backButton(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
