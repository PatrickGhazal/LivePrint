package griffmedia.ghazal.liveprint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.content.Context.*;

/**
 * General-use static functions for the app.
 *
 * @author Patrick Ghazal
 * @version 1.0
 */
public class Funcs {

    /**
     * Reads the data file and calls the load method to parse it and initialize data elements.
     *
     * @param context Activity that calls the method
     * @see Funcs#loadCompLinkData(String)
     */
    public static void setup(Context context) {
        try {
            String compLinkData = Funcs.read(context, Data.compLinksFileName);
            loadCompLinkData(compLinkData);
        } catch (IOException e) {
            System.out.println("Could not load data for companies and links.");
        }
    }

    /**
     * Parses the data file and initializes the data elements.
     *
     * @param strData String that contains the full file contents
     */
    private static void loadCompLinkData(String strData) {

        Data data = Data.getInstance();
        data.eraseAllData();

        String[] compData = strData.split("\\|\\|");
        for (String cData : compData) {
            if (cData.length() > 0) {
                String[] parts = cData.split("::");
                String compName = parts[0].trim(), compPass = parts[1].trim(), photoName = "", videoName = "";
                Company foundComp = new Company(compName);
                foundComp.setPassword(compPass);
                data.addComp(foundComp);
                String[] links = parts[2].split("---");
                for (String link : links) {
                    String[] photoVideoNames = link.split("//");
                    if (photoVideoNames.length > 1) {
                        photoName = photoVideoNames[0].trim();
                        videoName = photoVideoNames[1].trim();
                        Link foundLink = new Link(photoName, videoName);
                        foundComp.addLink(foundLink);
                    }
                }
            }
        }
    }

    /**
     * Saves all of the data elements currently stored.
     *
     * @param context Activity that calls the method
     */
    public static void saveFullData(Context context) {

        Data data = Data.getInstance();

        ArrayList<String> fullData = data.dataToString();
        boolean first = true;
        while (fullData.size() != 0) {
            String dataElement = fullData.remove(0);
            try {
                if (first) {
                    first = false;
                    write(context, Data.compLinksFileName, dataElement, false);
                } else {
                    write(context, Data.compLinksFileName, dataElement, true);
                }
            } catch (IOException e) {
                System.out.println("Could not save data.");
            }
        }
    }

    /**
     * Reads a file and places its full contents in a String.
     *
     * @param context  Activity that calls the method
     * @param fileName String that contains the name of the file to read
     * @return the full contents of the specified file
     * @throws IOException
     */
    public static String read(Context context, String fileName) throws IOException {

        FileInputStream fIn = context.openFileInput(fileName);
        InputStreamReader isr = new InputStreamReader(fIn);

        char[] inputBuffer = new char[Data.bufferSize];

        String fullFound = "";

        while (fIn.available() != 0) {
            isr.read(inputBuffer);
            String found = new String(inputBuffer);
            fullFound += found;
        }

        isr.close();

        return fullFound.trim();

    }

    /**
     * Writes data to the specified file.
     *
     * @param context  Activity that calls the method
     * @param fileName String that contains the name of the file to write to
     * @param toWrite  String that contains the data to be written
     * @param append   boolean that specified whether to append or overwrite the file
     * @throws IOException
     */
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

    /**
     * Closes the previous <code>Activity</code> and starts the new one.
     *
     * @param previous Activity that calls the method and is being closed
     * @param toOpen   class to open
     */
    public static void startActivityFunc(Activity previous, Class toOpen) {
        Intent intent = new Intent(previous, toOpen);
        previous.finish();
        previous.startActivity(intent);
    }

}