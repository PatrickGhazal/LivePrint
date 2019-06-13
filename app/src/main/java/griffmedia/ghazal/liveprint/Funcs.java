package griffmedia.ghazal.liveprint;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Funcs {

    final static int bufferSize = 8096;

    // compute height from percentage of total height
    public static int resizeHeight(double gapPerc, double windowHeight) {
        return (int) Math.round(gapPerc * windowHeight / 100.0);
    }

    // set the newly computed height
    public static void setGapHeight(View view, int heightVal, double density) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) Math.round(heightVal * density);
        view.setLayoutParams(layoutParams);
    }

    public static void setBGColour(String colour, View... views) {
        for (View v : views) {
            v.setBackgroundColor(Color.parseColor(colour));
        }
    }

    public static String linksToString(String company) {
        ArrayList<Link> links = LPHomePage.getLinks();

        String returned = "";

        for (Link link : links) {
            if (link.getCompany().equals(company)) {
                String newLink = link.getPhotoName() + "/" + link.getVideoName() + "\n";
                returned += newLink;
            }
        }

        return returned;
    }

    public static ArrayList<String> extractCompNames() {
        ArrayList<Link> allLinks = LPHomePage.getLinks();
        ArrayList<String> compNames = new ArrayList<String>();

        for (Link link : allLinks) {
            String compName = link.getCompany();
            compNames.add(compName);
        }

        return compNames;
    }

    public static void setup() {

    }

    public static String read(Context context, String fileName) throws FileNotFoundException, IOException {
        //Reading the file back...

        /* We have to use the openFileInput()-method
         * the ActivityContext provides.
         * Again for security reasons with
         * openFileInput(...) */

        FileInputStream fIn = context.openFileInput(fileName);
        InputStreamReader isr = new InputStreamReader(fIn);

        /* Prepare a char-Array that will
         * hold the chars we read back in. */

        char[] inputBuffer = new char[bufferSize];

        // Fill the Buffer with data from the file
        isr.read(inputBuffer);

        // Transform the chars to a String
        String readString = new String(inputBuffer);

        isr.close();

        return readString.trim();
    }

    public static void write(Context context, String fileName, String toWrite) throws FileNotFoundException, IOException {
        /* We have to use the openFileOutput()-method
         * the ActivityContext provides, to
         * protect your file from others and
         * This is done for security-reasons.
         * We chose MODE_WORLD_READABLE, because
         *  we have nothing to hide in our file */
        FileOutputStream fOut = context.openFileOutput(fileName, MODE_PRIVATE);
        OutputStreamWriter osw = new OutputStreamWriter(fOut);

        // Write the string to the file
        osw.write(toWrite);

        /* ensure that everything is
         * really written out and close */
        osw.close();
    }



}