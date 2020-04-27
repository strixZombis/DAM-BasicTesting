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


@RunWith(AndroidJUnit4.class)
public class LoginActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);



    private MockWebServer server;
    private AccountDAO accountDAO;

    @Before
    public void setUp() throws IOException {
        mActivityRule.getActivity();

        server = new MockWebServer();
        server.start();

        OkHttpClient okHttpClient = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server.url("/").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        accountDAO = retrofit.create(AccountDAO.class);
        mActivityRule.getActivity().getLoginViewModel().getAccountRepo().setAccountDAO(accountDAO);

        Log.d("testValidLoginShowsSuccessErrorMsg","+++"+server.getHostName());


    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void errorMsgTextViewNotDisplayed(){
        Espresso.onView(ViewMatchers.withId(R.id.errorMessage)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
    }

    @Test
    public void testInvalidPasswordShowsErrorMsg(){
        Espresso.onView(ViewMatchers.withId(R.id.editText_pass)).perform(ViewActions.typeText("12345678"));
        Espresso.onView(ViewMatchers.withId(R.id.button_login)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.errorMessage))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(ViewMatchers.withText("Bad password")));

    }

//    @Test
//    public void testValidPasswordNotShowsErrorMsg(){
//        Espresso.onView(ViewMatchers.withId(R.id.editTest_username)).perform(ViewActions.typeText("user1"));
//        Espresso.onView(ViewMatchers.withId(R.id.editText_pass)).perform(ViewActions.typeText("1a2S3d4f5@"));
//        Espresso.onView(ViewMatchers.withId(R.id.button_login)).perform(ViewActions.click());
//
//        Espresso.onView(ViewMatchers.withId(R.id.errorMessage)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
//    }

    @Test
    public void testValidLoginShowsSuccessErrorMsg(){


        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(""));

        Espresso.onView(ViewMatchers.withId(R.id.editTest_username)).perform(ViewActions.typeText("user1"));
        Espresso.onView(ViewMatchers.withId(R.id.editText_pass)).perform(ViewActions.typeText("1a2S3d4f5@"));
        Espresso.onView(ViewMatchers.withId(R.id.button_login)).perform(ViewActions.click());



        Espresso.onView(ViewMatchers.withId(R.id.errorMessage))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(ViewMatchers.withText("Success")));
    }


}
