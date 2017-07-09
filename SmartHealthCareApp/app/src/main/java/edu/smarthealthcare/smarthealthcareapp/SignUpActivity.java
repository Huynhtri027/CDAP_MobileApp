package edu.smarthealthcare.smarthealthcareapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    private Button btnSignup;
    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtUserEmail;
    private EditText txtConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSignup = (Button) findViewById(R.id.btnSignUp);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtUserPassword);
        txtConfirmPass = (EditText) findViewById(R.id.txtUserPasswordConfirm);
        txtUserEmail = (EditText) findViewById(R.id.txtUserEmailId);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
