package griffmedia.ghazal.liveprint;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.content.Context.*;

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

    public static void setup(Context context) {

        MainActivity.dataInstance = Data.getInstance();
        try {
            String compLinkData = Funcs.read(context, "CompaniesLinks.txt");
            loadCompLinkData(compLinkData);
        } catch (IOException e) {
            System.out.println("Could not load data for companies and links.");
        }
    }

    private static void loadCompLinkData(String strData) {

        Data data = Data.getInstance();

        String[] links = strData.split("\\|\\|");
        for (String link : links) {
            if (link.length() > 0) {
                String[] parts = link.split("::");
                String compName = parts[0].trim();
                String[] photoVideoNames = parts[1].trim().split("//");
                String photoName = photoVideoNames[0].trim();
                String videoName = photoVideoNames[1].trim();
                Company foundComp = new Company(compName);
                data.addComp(foundComp);
                Link foundLink = new Link(photoName, videoName);
                foundComp.addLink(foundLink);
            }
        }
    }

    public static void saveFullData(Context context) {

        Data data = Data.getInstance();

        ArrayList<String> fullData = data.dataToString();
        boolean first = true;
        while (fullData.size() != 0) {
            String dataElement = fullData.remove(0);
            try {
                if (first) {
                    first = false;
                    write(context, "CompaniesLinks.txt", dataElement, false);
                } else {
                    write(context, "CompaniesLinks.txt", dataElement, true);
                }
            } catch (IOException e) {
                System.out.println("Could not save data.");
            }
        }
    }

    public static String read(Context context, String fileName) throws IOException {

        FileInputStream fIn = context.openFileInput(fileName);
        InputStreamReader isr = new InputStreamReader(fIn);

        char[] inputBuffer = new char[bufferSize];

        String fullFound = "";

        while (fIn.available() != 0) {
            isr.read(inputBuffer);
            String found = new String(inputBuffer);
            fullFound += found;
        }

        isr.close();

        return fullFound.trim();

    }

    public static void write(Context context, String fileName, String toWrite, boolean append) throws IOException {

        FileOutputStream fOut;

        if (append) {
            fOut = context.openFileOutput(fileName, MODE_PRIVATE | MODE_APPEND);
        } else {
            fOut = context.openFileOutput(fileName, MODE_PRIVATE);
        }

        OutputStreamWriter osw = new OutputStreamWriter(fOut);

        toWrite += "||";

        osw.write(toWrite);

        osw.close();
    }


}