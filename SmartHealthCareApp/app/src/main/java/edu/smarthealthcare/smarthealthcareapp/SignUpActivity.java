package edu.smarthealthcare.smarthealthcareapp;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.auth.api.signin.internal.SignInHubActivity;

import java.util.HashMap;

import edu.smarthealthcare.smarthealthcareapp.Classes.PatientModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.ServerResponse;
import edu.smarthealthcare.smarthealthcareapp.Utils.APIService;
import edu.smarthealthcare.smarthealthcareapp.Utils.NetConnect;
import edu.smarthealthcare.smarthealthcareapp.Utils.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private ActionProcessButton btnSignup;

    private TextInputEditText txt_first_name_reg, txt_last_name_reg,txt_user_email_reg,txt_user_age_reg, txt_user_address_reg,
            txt_user_phone_reg,txt_user_rfid_reg,txt_user_password_reg,txt_user_confirm_password_reg;

    private RadioGroup radioGroupGenderReg;
    String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_register);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSignup = (ActionProcessButton)findViewById(R.id.btnSignUp);
        btnSignup.setMode(ActionProcessButton.Mode.ENDLESS);
        btnSignup.setColorScheme(ContextCompat.getColor(this,R.color.colorPrimary),ContextCompat.getColor(this,R.color.white),ContextCompat.getColor(this,R.color.colorPrimary),ContextCompat.getColor(this,R.color.white));

        btnSignup = (ActionProcessButton) findViewById(R.id.btnSignUp);
        txt_first_name_reg = (TextInputEditText) findViewById(R.id.txt_first_name_reg);
        txt_last_name_reg = (TextInputEditText) findViewById(R.id.txt_last_name_reg);
        txt_user_email_reg = (TextInputEditText) findViewById(R.id.txt_user_email_reg);
        txt_user_age_reg = (TextInputEditText) findViewById(R.id.txt_user_age_reg);
        txt_user_address_reg = (TextInputEditText) findViewById(R.id.txt_user_address_reg);
        txt_user_phone_reg = (TextInputEditText) findViewById(R.id.txt_user_phone_reg);
        txt_user_rfid_reg = (TextInputEditText) findViewById(R.id.txt_user_rfid_reg);
        txt_user_password_reg = (TextInputEditText) findViewById(R.id.txt_user_password_reg);
        txt_user_confirm_password_reg = (TextInputEditText) findViewById(R.id.txt_user_confirm_password_reg);
        radioGroupGenderReg = (RadioGroup)findViewById(R.id.radioGroupGenderReg);

        radioGroupGenderReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int selected = radioGroup.getCheckedRadioButtonId();

                if (selected == R.id.radio_male_reg){
                    gender = "Male";
                }else if (selected == R.id.radio_female_reg){
                    gender = "Female";
                }else{
                    gender = "";
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//

                final String textFirstName = txt_first_name_reg.getText().toString().trim();
                final String textLasttName = txt_last_name_reg.getText().toString().trim();
                final String textEmail = txt_user_email_reg.getText().toString().trim();
                final String textAge = txt_user_age_reg.getText().toString().trim();
                final String textAddress = txt_user_address_reg.getText().toString().trim();
                final String textPhone = txt_user_phone_reg.getText().toString().trim();
                final String textRfid = txt_user_rfid_reg.getText().toString().trim();
                final String textPassword = txt_user_password_reg.getText().toString().trim();
                final String textConfirmPassword = txt_user_confirm_password_reg.getText().toString().trim();

                if (textFirstName.length() == 0 || textLasttName.length()==0 || textEmail.length() ==0 || textAge.length()==0
                        || textAddress.length()==0 || textPhone.length() ==0
                        || textPassword.length() ==0 || textConfirmPassword.length()==0){

//                    Toast.makeText(SignUpActivity.this, "Working", Toast.LENGTH_LONG).show();
                    if (textFirstName.length()==0)
                        txt_first_name_reg.setError("Please enter your first name");
                    if (textLasttName.length()==0)
                        txt_last_name_reg.setError("Please enter your last name");
                    if (textEmail.length()==0)
                        txt_user_email_reg.setError("Please enter your email address");

                    if (textAge.length()==0)
                        txt_user_age_reg.setError("Please enter your age");
                    if (textAddress.length()==0)
                        txt_user_address_reg.setError("Please enter your address");
                    if (textPhone.length()==0)
                        txt_user_phone_reg.setError("Please enter your phone number");
                    if (textPassword.length()==0)
                        txt_user_password_reg.setError("Please enter your password");
                    if (textConfirmPassword.length()==0)
                        txt_user_confirm_password_reg.setError("Please enter your confirm password");

                }

                else if(!isValidEmail(textEmail)){
                    txt_user_email_reg.setError("Please enter a valid email address");
                }
                else if(!isMatching(textPassword, textConfirmPassword)){
                    txt_user_confirm_password_reg.setError("Please enter a matching password");
                }
                else{

                    if (NetConnect.isNetworkConnected(SignUpActivity.this)){
                        btnSignup.setProgress(50);

                        registerPatient(textFirstName, textLasttName, textEmail, textAge, textAddress, textPhone, textRfid , textPassword, gender);
                    }
                    else{
                        btnSignup.setProgress(-1); //fail
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() { // set to normal after 2 seconds
                                btnSignup.setProgress(0);
                            }
                        },2000);
                        new MaterialDialog.Builder(SignUpActivity.this)
                                .title("No Internet")
                                .content("Please check your WiFi/Mobile Data settings and try again.")
                                .positiveText("OK")
                                .positiveColor(ContextCompat.getColor(SignUpActivity.this, R.color.material_green))
                                .build().show();
                    }
                }
            }
        });

    }

    private void registerPatient(String textFirstName, String textLasttName, String textEmail,
                                 String textAge, String textAddress, String textPhone, String textRfid, String textPassword, String gender) {

        APIService apiService = ServiceGenerator.createService(APIService.class);
        Call<ServerResponse> call = apiService.registerPatientData(textFirstName,textLasttName,textEmail,
                textAge,textAddress,textPhone,textRfid,textPassword, gender);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                if (response.isSuccessful()) {

                    ServerResponse serverResponse = response.body();
                    if (serverResponse.getResult()){

                        btnSignup.setProgress(100);
                        Toast.makeText(SignUpActivity.this, serverResponse.getMessage(), Toast.LENGTH_LONG).show();

                        Intent i = new Intent(SignUpActivity.this,GetStartActivity.class);
                        startActivity(i);
                        finish();

                    }else{

                        Toast.makeText(SignUpActivity.this, serverResponse.getMessage(), Toast.LENGTH_LONG).show();

                    }

                }else{
                    btnSignup.setProgress(-1);
                    Toast.makeText(SignUpActivity.this, "Something Wrong, Try again later!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public boolean isMatching(String pass, String cPass){
        if (cPass.equals(pass))
            return true;
        return false;

    }
    public boolean isNumber(String number){
        try {
            int num = Integer.parseInt(number);
            Log.i("",num+" is a number");
            return true;
        } catch (NumberFormatException e) {
            Log.i("",number+" is not a number");
            return false;
        }
    }
}
