package cat.udl.tidic.amd.dam_basictesting.Users;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepo {

    private AccountDAO accountDAO;
    private MutableLiveData<String> mToken;

    public AccountRepo( ){
        mToken = new MutableLiveData<>();
        accountDAO = new AccountDAOImpl();
    }

    public void createToken(String auth) throws IOException {

        accountDAO.createTokenUser(auth).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call,
                                   @NotNull Response<ResponseBody> response) {
                if (response.code() == 200 ){
                    // String token = Objects.requireNonNull(response.body()).string();
                    // token = token.split(":")[1];
                    // token = token.substring(2,token.length()-2);
                    mToken.setValue("Success");
                }
                else if (response.code() == 401 ){
                    // String message = Objects.requireNonNull(response.errorBody()).string();
                    mToken.setValue("Unauthorized");
                } else if (response.code() == 404 ){
                    // String message = Objects.requireNonNull(response.errorBody()).string();
                    mToken.setValue("Not found");
                }

            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                Log.d("AccountRepo", t.getMessage());
                mToken.setValue("Network error");
            }
        });
    }

    public MutableLiveData<String> getToken() {
        return mToken;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
}
