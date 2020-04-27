package cat.udl.tidic.amd.dam_basictesting;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GeoUtilsInstrumentedTest {

    private GeoUtils geoUtils;

    @Before
    public void setUp(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        geoUtils = new GeoUtils(context);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testShouldThrowIndexOutOfBoundsException() throws IOException {
        geoUtils.getCountry(0,0);
    }

    @Test
    public void testSpainThatShouldPassWithIncorrectCoordinates() throws IOException {
        String countryName = geoUtils.getCountry(1.6172,41.58098);
        assertNotEquals("Spain", countryName);
    }

    @Test
    public void testSpainThatShouldPassWithRightCoordinates() throws IOException {
        String countryName = geoUtils.getCountry(41.58098,1.6172);
        assertEquals("Spain", countryName);
    }
}
