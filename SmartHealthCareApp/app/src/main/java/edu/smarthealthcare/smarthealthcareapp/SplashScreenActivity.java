package edu.smarthealthcare.smarthealthcareapp;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gigamole.slideimageview.lib.SlideImageView;

import edu.smarthealthcare.smarthealthcareapp.Classes.PatientModel;
import edu.smarthealthcare.smarthealthcareapp.Utils.APIService;
import edu.smarthealthcare.smarthealthcareapp.Utils.NetConnect;
import edu.smarthealthcare.smarthealthcareapp.Utils.ServiceGenerator;
import edu.smarthealthcare.smarthealthcareapp.Utils.SharedPreferenceReader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final SlideImageView slideImageView = (SlideImageView) findViewById(R.id.img_horizontal_slide_view);
        slideImageView.setSource(R.mipmap.backgroud);
        slideImageView.setRate(0.3f);
        slideImageView.setAxis(SlideImageView.Axis.HORIZONTAL);
        
        
        launch();
    }

    private void launch() {

        if (NetConnect.isNetworkConnected(this)) { //internet available

            if (SharedPreferenceReader.isLoggedIn(this)) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        verifyUser();
                    }
                }, 3000);

            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashScreenActivity.this, GetStartActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 3000);
            }

        } else {
            new MaterialDialog.Builder(this)
                    .title("No Internet")
                    .content("Please check your WiFi/Mobile Data settings and try again.")
                    .positiveText("RETRY")
                    .positiveColor(ContextCompat.getColor(this, R.color.material_green))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            launch();
                        }
                    }).show();
        }
    }

    private void verifyUser() {

        String user_email = SharedPreferenceReader.getUserEmail(this);
        String user_password = SharedPreferenceReader.getUserPassword(this);
        Boolean isLoggedIn = SharedPreferenceReader.isLoggedIn(this);

        if (isLoggedIn) {

            APIService apiService = ServiceGenerator.createService(APIService.class);
            Call<PatientModel> call = apiService.getPatientData(user_email, user_password);

            call.enqueue(new Callback<PatientModel>() {
                @Override
                public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                    if (response.isSuccessful()){

                        if (response.body() == null){
                            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else{
                            PatientModel patientModel = response.body();
                            SharedPreferenceReader.createLoginSession(SplashScreenActivity.this,patientModel.getEmail(),patientModel.getPassword(),patientModel.getId(),patientModel.getFirstName() + " " +patientModel.getLastName());
                            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }else{
                        Toast.makeText(SplashScreenActivity.this, "Something Wrong, Try again later!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<PatientModel> call, Throwable t) {
                    Log.d("Error :", t.getMessage().toString());
                    Toast.makeText(SplashScreenActivity.this, "Failed : " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }else{

            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
