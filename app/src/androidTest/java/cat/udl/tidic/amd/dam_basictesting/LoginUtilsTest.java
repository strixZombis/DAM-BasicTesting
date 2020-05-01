package cat.udl.tidic.amd.dam_basictesting;

import android.util.Log;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import cat.udl.tidic.amd.dam_basictesting.Users.AccountDAO;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class LoginUtilsTest {
    private LoginUtils loginUtils;
    @Before
    public void setUp(){
        loginUtils = new LoginUtils();
    }
    @Test
    public void validAddressPasses() throws Exception {
        //LoginUtils loginUtils = new LoginUtils();
        assertTrue(loginUtils.isValidEmailAddress("xxx@hotmail.com"));
    }
    @Test
    public void invalidGmailAddressPasses() throws Exception {
        //LoginUtils loginUtils = new LoginUtils();
        assertFalse(loginUtils.isValidGmailAddress("xxx@hotmail.com"));
    }
    @Test
    public void validGmailAddressPasses() throws Exception {
        //LoginUtils loginUtils = new LoginUtils();
        assertTrue(loginUtils.isValidGmailAddress("xxx@gmail.com"));
    }
    @Test
    public void validUsernamePasses() throws Exception {
        //LoginUtils loginUtils = new LoginUtils();
        assertTrue(loginUtils.getUserName("xxx@gmail.com"));
        assertTrue(loginUtils.getUserName("hol1x@gmail.com"));
    }
    @Test
    public void invalidUsernamePasses() throws Exception {
        //LoginUtils loginUtils = new LoginUtils();
        assertFalse(loginUtils.getUserName("x@x+``x@gmail.com"));
        assertFalse(loginUtils.getUserName("x+`--?`x@gmail.com"));
    }


}
