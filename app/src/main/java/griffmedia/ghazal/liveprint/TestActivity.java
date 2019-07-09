package griffmedia.ghazal.liveprint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Used for in-app testing purposes.
 *
 * @author Patrick Ghazal
 * @version 1.0
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * <code>onClick</code> method for the <code>readAndPrint</code> button.
     * Reads the current data in the file and prints it.
     *
     * @param v view that contains the clicked button
     */
    public void readAndPrint(View v) {
        try {
            String found = Funcs.read(this, Data.compLinksFileName);
            System.out.println(found);
        } catch (FileNotFoundException e) {
            System.out.println("fnf exc");
        } catch (IOException e) {
            System.out.println("io exc");
        }

    }

    /**
     * <code>onClick</code> method for the <code>genRandLink</code> button.
     * Generates a company and link with randomized data and adds them to the data instance.
     *
     * @param v view that contains the clicked button
     */
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

    /**
     * Computes a random number between 1 and 100.
     *
     * @return a random number between 1 and 100
     */
    private int randNum() {
        return (int) Math.floor(100.0 * Math.random());
    }

    /**
     * <code>onClick</code> method for the <code>saveData</code> button.
     * Saves the full data.
     *
     * @param v view that contains the clicked button
     * @see Funcs#saveFullData(Context)
     */
    public void saveData(View v) {
        Funcs.saveFullData(this);
    }

    /**
     * <code>onClick</code> method for the <code>eraseData</code> button.
     * Erases the entire data.
     *
     * @param v view that contains the clicked button
     * @see Data#eraseAllData()
     */
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

    /**
     * <code>onClick</code> method for the <code>Back</code> button.
     * Calls <code>finish</code> on <code>this</code> Activity and launches the previous page (the one <code>this</code> was opened from).
     *
     * @param v view that contains the clicked button
     */
    public void backButton(View v) {
        Funcs.startActivityFunc(this, MainActivity.class);
    }

    public void readInspectionData(View v) {
        try {
            String inspectionReport = Funcs.read(this, "inspectionReport.txt");
            System.out.println(inspectionReport);
        } catch (FileNotFoundException e) {
            System.out.println("fnf exc");
        } catch (IOException e) {
            System.out.println("io exc");
        }
    }

}