package griffmedia.ghazal.liveprint;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Funcs {

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

    public static String readFromFile(Context context, String fileName) {
        FileInputStream inputStream = null;
        String contents = "";
        int ch;
        try {
            inputStream = context.openFileInput(fileName);

            while ((ch = inputStream.read()) != -1) {
                contents += ("" + (char)(ch));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return contents;
    }

    public static void writeToFile(Context context, String fileName, String contents) {
        FileOutputStream outputStream = null;

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(contents.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}