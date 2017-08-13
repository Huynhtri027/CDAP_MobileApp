package edu.smarthealthcare.smarthealthcareapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dd.processbutton.iml.ActionProcessButton;
import com.google.firebase.iid.FirebaseInstanceId;

import org.w3c.dom.Text;

import java.util.HashMap;

import edu.smarthealthcare.smarthealthcareapp.Classes.PatientModel;
import edu.smarthealthcare.smarthealthcareapp.Utils.APIService;
import edu.smarthealthcare.smarthealthcareapp.Utils.NetConnect;
import edu.smarthealthcare.smarthealthcareapp.Utils.ServiceGenerator;
import edu.smarthealthcare.smarthealthcareapp.Utils.SharedPreferenceReader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActionProcessButton btnLogin;

    private TextInputEditText txtUsername;
    private TextInputEditText txtPassword;
    private TextInputEditText txtUserEmail;
    private TextInputEditText txtConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_login);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnLogin = (ActionProcessButton)findViewById(R.id.btnLogin);
        btnLogin.setMode(ActionProcessButton.Mode.ENDLESS);
        btnLogin.setColorScheme(ContextCompat.getColor(this,R.color.colorPrimary),ContextCompat.getColor(this,R.color.white),ContextCompat.getColor(this,R.color.colorPrimary),ContextCompat.getColor(this,R.color.white));

        btnLogin = (ActionProcessButton) findViewById(R.id.btnLogin);
        txtUsername = (TextInputEditText) findViewById(R.id.tiet_user_name_login);
        txtPassword = (TextInputEditText) findViewById(R.id.tiet_user_password_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String textPassword = txtPassword.getText().toString().trim();
                final String textUsername = txtUsername.getText().toString().trim();

                if (textPassword.length()==0 || textUsername.length() ==0){

                    if (textUsername.length()==0)
                        txtUsername.setError("Please enter your user name");
                    if (textPassword.length()==0)
                        txtPassword.setError("Please enter your password");
                }else{
//
                    if (NetConnect.isNetworkConnected(LoginActivity.this)){
                        btnLogin.setProgress(50);

                        String email = txtUsername.getText().toString();
                        String password = txtPassword.getText().toString();

                        login(email,password);
                    }
                    else{
                        btnLogin.setProgress(-1);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btnLogin.setProgress(0);
                            }
                        },2000);
                        new MaterialDialog.Builder(LoginActivity.this)
                                .title("No Internet")
                                .content("Please check your WiFi/Mobile Data settings and try again.")
                                .positiveText("OK")
                                .positiveColor(ContextCompat.getColor(LoginActivity.this, R.color.material_green))
                                .build().show();
                    }
                }

            }
        });
    }

    private void login(String email, String password) {

        APIService apiService = ServiceGenerator.createService(APIService.class);
        Call<PatientModel> call = apiService.getPatientData(email,password);
        call.enqueue(new Callback<PatientModel>() {
            @Override
            public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {

                if (response.isSuccessful()){

                    if (response.body() == null){

                        btnLogin.setProgress(-1);

                        new MaterialDialog.Builder(LoginActivity.this)
                                .title("No account found")
                                .content("Sorry, We can not find your account in our server!.")
                                .positiveText("Ok")
                                .positiveColor(ContextCompat.getColor(LoginActivity.this, R.color.material_green))
                                .build().show();

                    }else{

                        btnLogin.setProgress(100);
                        PatientModel patientModel = response.body();


                        SharedPreferenceReader.createLoginSession(LoginActivity.this,patientModel.getEmail(),patientModel.getPassword(),patientModel.getId(),patientModel.getFirstName() + " " +patientModel.getLastName());
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();

                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Something Wrong, Try again later!", Toast.LENGTH_LONG).show();
                }



            }

            @Override
            public void onFailure(Call<PatientModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
