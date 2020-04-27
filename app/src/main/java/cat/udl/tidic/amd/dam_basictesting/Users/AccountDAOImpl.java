package cat.udl.tidic.amd.dam_basictesting.Users;

import java.io.IOException;

import cat.udl.tidic.amd.dam_basictesting.network.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class AccountDAOImpl implements AccountDAO{

    private Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
    //private Retrofit retrofit;

    @Override
    public Call<ResponseBody> createTokenUser(String auth) throws IOException {
        return retrofit.create(AccountDAO.class).createTokenUser(auth);
    }

    private  void setRetrofit(Retrofit retrofit){
        this.retrofit = retrofit;
    }
}
