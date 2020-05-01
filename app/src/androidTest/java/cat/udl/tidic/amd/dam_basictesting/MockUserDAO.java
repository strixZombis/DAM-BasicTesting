package cat.udl.tidic.amd.dam_basictesting;

import android.util.Log;

import java.io.IOException;

import cat.udl.tidic.amd.dam_basictesting.Users.AccountDAO;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.Calls;

public class MockUserDAO implements AccountDAO {

    private final static int SUCCESS = 200;
    private final static int UNAUTHORIZED = 401;
    private final static int RESOURCENOTFOUND = 404;
    private final static String ERR_UNAUTHORIZED = "Unauthorized";
    private final static String ERR_USERNOTFOUND = "User not found";
    private final static String TOKEN = "a89234kdejwqu9dksd90";


    private final BehaviorDelegate<AccountDAO> delegate;

    public MockUserDAO(BehaviorDelegate<AccountDAO> service) {
        this.delegate = service;
    }

    @Override
    public Call<ResponseBody> createTokenUser(String auth) throws IOException {

        String AUTH_EXISTING_USER = "Authentication:  am9yZGk6MTIzNA==";
        String AUTH_NON_EXISTING_USER = "Authentication:  bm9yZGk6MTIzNA==";


        Response response;
        String msg = "";

        Log.d("MockUserDAO", "Auth " + auth);

        if (auth.equals(AUTH_EXISTING_USER)) {

            msg = ("\"{token:" + TOKEN+"}\"");
            response = Response.success( SUCCESS,
                    ResponseBody.create(msg,
                            MediaType.get("application/json; charset=utf-8")));

        }else if (auth.equals(AUTH_NON_EXISTING_USER)) {
            msg = ("\"{message:" + ERR_USERNOTFOUND+"}\"");
            response = Response.error( RESOURCENOTFOUND,
                    ResponseBody.create(msg,
                            MediaType.get("application/json; charset=utf-8")));
        }else{
            msg = ("\"{message:" + ERR_UNAUTHORIZED+"}\"");
            response = Response.error(UNAUTHORIZED,
                    ResponseBody.create(msg,
                            MediaType.get("application/json; charset=utf-8")));
        }

        return delegate.returning(Calls.response(response)).createTokenUser(auth);

    }
}
