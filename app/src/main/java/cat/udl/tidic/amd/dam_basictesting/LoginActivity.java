package cat.udl.tidic.amd.dam_basictesting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import java.io.IOException;


public class LoginActivity extends AppCompatActivity {


    private EditText password;
    private TextView errorMsg;
    private LoginUtils loginUtils;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText username = findViewById(R.id.editTest_username);
        password = findViewById(R.id.editText_pass);
        errorMsg = findViewById(R.id.errorMessage);
        loginUtils = new LoginUtils();
        Button login = findViewById(R.id.button_login);

        loginViewModel = new LoginViewModel(getApplication());

        loginViewModel.getToken().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String state) {
                errorMsg.setText(state);
                errorMsg.setVisibility(View.VISIBLE);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = password.getText().toString();
                String user = username.getText().toString();
                if (!loginUtils.isValidPassword(pass)){
                    errorMsg.setText("Bad password");
                    errorMsg.setVisibility(View.VISIBLE);
                }else{
                    String encodeString = loginUtils.encodeLogin(user,pass);
                    try {
                        loginViewModel.createToken(encodeString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public LoginViewModel getLoginViewModel(){
        return this.loginViewModel;
    }



}
