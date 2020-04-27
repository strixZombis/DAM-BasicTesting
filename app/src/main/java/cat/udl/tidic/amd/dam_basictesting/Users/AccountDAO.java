package cat.udl.tidic.amd.dam_basictesting.Users;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AccountDAO {

    @POST("account/create_token")
    Call<ResponseBody> createTokenUser(@Header("Authorization") String auth) throws IOException;

}
