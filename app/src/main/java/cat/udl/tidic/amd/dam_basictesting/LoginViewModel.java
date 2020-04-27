package cat.udl.tidic.amd.dam_basictesting;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import cat.udl.tidic.amd.dam_basictesting.Users.AccountRepo;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<String> mToken;
    private AccountRepo accountRepo;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        this.accountRepo = new AccountRepo();
        mToken = this.accountRepo.getToken();
    }

    public void createToken(String encodeHeader) throws IOException {
        this.accountRepo.createToken(encodeHeader);
    }

    public MutableLiveData<String> getToken(){
        return mToken;
    }

    public AccountRepo getAccountRepo(){
        return this.accountRepo;
    }
}
