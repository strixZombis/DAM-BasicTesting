package cat.udl.tidic.amd.dam_basictesting;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Objects;

import cat.udl.tidic.amd.dam_basictesting.Users.AccountDAO;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

@RunWith(AndroidJUnit4.class)
public class UserDAOTest {

    private MockRetrofit mockRetrofit;
    private Retrofit retrofit;

    @Before
    public void setUp() throws Exception {
        retrofit = new Retrofit.Builder().baseUrl("http://127.0.0.1")
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NetworkBehavior behavior = NetworkBehavior.create();

        mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
    }

    @Test
    public void noAuthorizedRequestShouldFail() throws IOException {
        BehaviorDelegate<AccountDAO> delegate = mockRetrofit.create(AccountDAO.class);
        AccountDAO mockUserDAO = new MockUserDAO(delegate);

        //Actual Test
        Call<ResponseBody> createToken = mockUserDAO.createTokenUser("Authentication: fjdsalkfjcoinds");
        Response  response = createToken.execute();

        Log.d("UserRepoTest", response.code() + " ... " + response.toString() + " ... " + response.body());

        //Asserting response
        Assert.assertEquals(401, response.code());
        Assert.assertEquals("\"{message:Unauthorized}\"",
                Objects.requireNonNull(response.errorBody()).string());
    }

    @Test
    public void authorizedRequestExistingUserShouldGetToken() throws IOException {
        final String TOKEN = "a89234kdejwqu9dksd90";
        BehaviorDelegate<AccountDAO> delegate = mockRetrofit.create(AccountDAO.class);
        AccountDAO mockUserDAO = new MockUserDAO(delegate);

        //Actual Test
        Call<ResponseBody> createToken = mockUserDAO.createTokenUser("Authentication:  am9yZGk6MTIzNA==");
        Response<ResponseBody>  response = createToken.execute();

        //Asserting response
        Assert.assertEquals(200, response.code());
        Assert.assertEquals("\"{token:"+TOKEN+"}\"", response.body().string());
    }

    @Test
    public void authorizedRequestNonExistingUserShouldNotGetToken() throws IOException {
        BehaviorDelegate<AccountDAO> delegate = mockRetrofit.create(AccountDAO.class);
        AccountDAO mockUserDAO = new MockUserDAO(delegate);

        //Actual Test
        Call<ResponseBody> createToken = mockUserDAO.createTokenUser("Authentication:  bm9yZGk6MTIzNA==");
        Response<ResponseBody>  response = createToken.execute();

        //Asserting response
        Assert.assertEquals(404, response.code());
        Assert.assertEquals("\"{message:User not found}\"", response.errorBody().string());
    }
}
