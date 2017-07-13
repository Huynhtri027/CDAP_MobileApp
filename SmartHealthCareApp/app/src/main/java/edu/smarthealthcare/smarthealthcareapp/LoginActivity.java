package edu.smarthealthcare.smarthealthcareapp;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

import org.w3c.dom.Text;

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
                    Intent i = new Intent(getApplication(), MainActivity.class);
                    startActivity(i);
                }


            }
        });
    }
}
