package griffmedia.ghazal.liveprint;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {

    @Test
    public void testFuncs() {
        testResizeHeight();
    }

    public void testResizeHeight() {
        double randPerc = Math.random() * 100.0;
        double randHeight = Math.random() * 1500;
        double thResizeHeightVal = randPerc * randHeight / 100.0;
        int roundedThVal = (int) Math.floor(thResizeHeightVal + 1.0 / 2.0);
        int foundResizeHeightVal = Funcs.resizeHeight(randPerc, randHeight);
        assertEquals(roundedThVal, foundResizeHeightVal);
    }




}