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

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.firebase.iid.FirebaseInstanceId;

import org.w3c.dom.Text;

import java.util.HashMap;

import edu.smarthealthcare.smarthealthcareapp.Classes.PatientModel;
import edu.smarthealthcare.smarthealthcareapp.Utils.APIService;
import edu.smarthealthcare.smarthealthcareapp.Utils.NetConnect;
import edu.smarthealthcare.smarthealthcareapp.Utils.ServiceGenerator;
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

                        HashMap<String,String> patientData = new HashMap<String, String>();
                        patientData.put("email",textUsername);
                        patientData.put("password",textPassword);
                        login(patientData);
                    }
                    else{
                        btnLogin.setProgress(-1); //fail
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() { // set to normal after 2 seconds
                                btnLogin.setProgress(0);
                            }
                        },2000);
                        Toast.makeText(LoginActivity.this, R.string.error_no_internet_connection, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void login(HashMap<String, String> patientData) {

//        Toast.makeText(LoginActivity.this, "Loged in", Toast.LENGTH_SHORT).show();
        APIService apiService = ServiceGenerator.createService(APIService.class);
        Call<PatientModel> call = apiService.patient(patientData);
        call.enqueue(new Callback<PatientModel>() {
            @Override
            public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                if (response.isSuccessful()){
                    PatientModel patientResponse = response.body();
                    if(patientResponse.getSuccess().equals("true")) { //login success, update values and show home page


                        btnLogin.setProgress(100); //success
//                        SharedPreferenceReader.createLoginSession(Login.this, patientData.get("email"), patientData.get("password"), loginResponse.getUser_id(), loginResponse.getUser_name(), loginResponse.getUser_profile_url());
//                        SharedPreferenceReader.createLoginSession(LoginActivity.this, patientData.get("email"), patientData.get("password"), loginResponse.getUser_id(), loginResponse.getUser_name() ,loginResponse.getUser_first_name(), loginResponse.getUser_last_name(), loginResponse.getUser_profile_url());
//                        FirebaseTokenManager.sendTokenToServer(Login.this, FirebaseInstanceId.getInstance().getToken());
                        Intent i = new Intent(getApplication(), MainActivity.class);
                        startActivity(i);

                        finish();

                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Invalid User",Toast.LENGTH_LONG).show();
                        btnLogin.setProgress(-1); //fail
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() { // set to normal after 2 seconds
                                btnLogin.setProgress(0);
                            }
                        },2000);
                    }

                }else{

                    Toast.makeText(getApplicationContext(),"Something went wrong with the service provider. Please contact the developer team", Toast.LENGTH_LONG).show();
                    btnLogin.setProgress(-1); //fail
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() { // set to normal after 2 seconds
                            btnLogin.setProgress(0);
                        }
                    },2000);
                }

            }

            @Override
            public void onFailure(Call<PatientModel> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(getApplicationContext(),"Unexpected response from server. Please try again", Toast.LENGTH_LONG).show();
                btnLogin.setProgress(-1); //fail
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() { // set to normal after 2 seconds
                        btnLogin.setProgress(0);
                    }
                },2000);
            }
        });

    }
}
