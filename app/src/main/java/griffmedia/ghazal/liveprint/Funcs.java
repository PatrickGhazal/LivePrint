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

    public static void setup() {

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

    public static void write(Context context, String fileName, String toWrite) throws IOException {

        FileOutputStream fOut = context.openFileOutput(fileName, MODE_PRIVATE);
        OutputStreamWriter osw = new OutputStreamWriter(fOut);

        osw.write(toWrite);

        osw.close();
    }



}