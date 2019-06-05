package griffmedia.ghazal.liveprint;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

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

}
