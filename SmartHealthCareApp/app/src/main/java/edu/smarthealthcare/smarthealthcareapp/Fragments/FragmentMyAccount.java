package edu.smarthealthcare.smarthealthcareapp.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dd.processbutton.iml.ActionProcessButton;

import edu.smarthealthcare.smarthealthcareapp.Classes.PatientModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.ServerResponse;
import edu.smarthealthcare.smarthealthcareapp.GetStartActivity;
import edu.smarthealthcare.smarthealthcareapp.LoginActivity;
import edu.smarthealthcare.smarthealthcareapp.MainActivity;
import edu.smarthealthcare.smarthealthcareapp.R;
import edu.smarthealthcare.smarthealthcareapp.SignUpActivity;
import edu.smarthealthcare.smarthealthcareapp.Utils.APIService;
import edu.smarthealthcare.smarthealthcareapp.Utils.NetConnect;
import edu.smarthealthcare.smarthealthcareapp.Utils.ServiceGenerator;
import edu.smarthealthcare.smarthealthcareapp.Utils.SharedPreferenceReader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyAccount extends Fragment {

    View view;

    private PatientModel patientModel;
    private ActionProcessButton btnUpdateProf;
    private ProgressDialog progressDialog;

    private TextInputEditText txt_first_name_prof, txt_last_name_prof,txt_user_email_prof,txt_user_age_prof, txt_user_address_prof,
            txt_user_phone_prof,txt_user_rfid_prof,txt_user_password_prof,txt_user_confirm_password_prof;

    private RadioGroup radioGroupGenderProf;
    private RadioButton radio_male_Prof, radio_female_Prof;
    String gender = "";

    public FragmentMyAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_my_account, container, false);



        if (NetConnect.isNetworkConnected(getContext())){

            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            progressDialog.setTitle("Please wait!!");
            progressDialog.setMessage("Please wait!!");
            progressDialog.setCancelable(false);

            String user_email = SharedPreferenceReader.getUserEmail(getContext());
            String user_password = SharedPreferenceReader.getUserPassword(getContext());
            getPatientData(user_email, user_password);

        }else{

            new MaterialDialog.Builder(getContext())
                    .title("No Internet")
                    .content("Please check your WiFi/Mobile Data settings and try again.")
                    .positiveText("OK")
                    .positiveColor(ContextCompat.getColor(getContext(), R.color.material_green))
                    .build().show();

        }


        btnUpdateProf = (ActionProcessButton)view.findViewById(R.id.btnUpdateProf);
        btnUpdateProf.setMode(ActionProcessButton.Mode.ENDLESS);
        btnUpdateProf.setColorScheme(ContextCompat.getColor(getContext(),R.color.colorPrimary),ContextCompat.getColor(getContext(),R.color.white),ContextCompat.getColor(getContext(),R.color.colorPrimary),ContextCompat.getColor(getContext(),R.color.white));

        btnUpdateProf = (ActionProcessButton) view.findViewById(R.id.btnUpdateProf);
        
        txt_first_name_prof = (TextInputEditText) view.findViewById(R.id.txt_first_name_prof);
        txt_last_name_prof = (TextInputEditText) view.findViewById(R.id.txt_last_name_prof);
        txt_user_email_prof = (TextInputEditText) view.findViewById(R.id.txt_user_email_prof);
        txt_user_age_prof = (TextInputEditText) view.findViewById(R.id.txt_user_age_prof);
        txt_user_address_prof = (TextInputEditText) view.findViewById(R.id.txt_user_address_prof);
        txt_user_phone_prof = (TextInputEditText) view.findViewById(R.id.txt_user_phone_prof);
        txt_user_rfid_prof = (TextInputEditText) view.findViewById(R.id.txt_user_rfid_prof);
        txt_user_password_prof = (TextInputEditText) view.findViewById(R.id.txt_user_password_prof);
        txt_user_confirm_password_prof = (TextInputEditText) view.findViewById(R.id.txt_user_confirm_password_prof);
        radioGroupGenderProf = (RadioGroup)view.findViewById(R.id.radioGroupGenderProf);

        radio_male_Prof = (RadioButton)view.findViewById(R.id.radio_male_Prof);
        radio_female_Prof = (RadioButton)view.findViewById(R.id.radio_female_Prof);

        radioGroupGenderProf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int selected = radioGroup.getCheckedRadioButtonId();

                if (selected == R.id.radio_male_reg){
                    gender = "Male";
                }else if (selected == R.id.radio_female_reg){
                    gender = "Female";
                }
            }
        });

        btnUpdateProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//

                final String textFirstName = txt_first_name_prof.getText().toString().trim();
                final String textLasttName = txt_last_name_prof.getText().toString().trim();
                final String textEmail = txt_user_email_prof.getText().toString().trim();
                final String textAge = txt_user_age_prof.getText().toString().trim();
                final String textAddress = txt_user_address_prof.getText().toString().trim();
                final String textPhone = txt_user_phone_prof.getText().toString().trim();
                final String textRfid = txt_user_rfid_prof.getText().toString().trim();
                final String textPassword = txt_user_password_prof.getText().toString().trim();
                final String textConfirmPassword = txt_user_confirm_password_prof.getText().toString().trim();

                if (textFirstName.length() == 0 || textLasttName.length()==0 || textEmail.length() ==0 || textAge.length()==0
                        || textAddress.length()==0 || textPhone.length() ==0
                        || textPassword.length() ==0 || textConfirmPassword.length()==0){

//                    Toast.makeText(SignUpActivity.this, "Working", Toast.LENGTH_LONG).show();
                    if (textFirstName.length()==0)
                        txt_first_name_prof.setError("Please enter your first name");
                    if (textLasttName.length()==0)
                        txt_last_name_prof.setError("Please enter your last name");
                    if (textEmail.length()==0)
                        txt_user_email_prof.setError("Please enter your email address");

                    if (textAge.length()==0)
                        txt_user_age_prof.setError("Please enter your age");
                    if (textAddress.length()==0)
                        txt_user_address_prof.setError("Please enter your address");
                    if (textPhone.length()==0)
                        txt_user_phone_prof.setError("Please enter your phone number");
                    if (textPassword.length()==0)
                        txt_user_password_prof.setError("Please enter your password");
                    if (textConfirmPassword.length()==0)
                        txt_user_confirm_password_prof.setError("Please enter your confirm password");

                }

                else if(!isValidEmail(textEmail)){
                    txt_user_email_prof.setError("Please enter a valid email address");
                }
                else if(!isMatching(textPassword, textConfirmPassword)){
                    txt_user_confirm_password_prof.setError("Please enter a matching password");
                }
                else{

                    if (NetConnect.isNetworkConnected(getContext())){
                        btnUpdateProf.setProgress(50);

                        if (radio_male_Prof.isChecked()){
                            gender = "Male";

                        }else if (radio_female_Prof.isChecked()){
                            gender = "Female";
                        }

                        updatePatient(textFirstName, textLasttName, textEmail, textAge, textAddress, textPhone, textRfid , textPassword, gender);
                    }
                    else{
                        btnUpdateProf.setProgress(-1); //fail
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() { // set to normal after 2 seconds
                                btnUpdateProf.setProgress(0);
                            }
                        },2000);
                        new MaterialDialog.Builder(getContext())
                                .title("No Internet")
                                .content("Please check your WiFi/Mobile Data settings and try again.")
                                .positiveText("OK")
                                .positiveColor(ContextCompat.getColor(getContext(), R.color.material_green))
                                .build().show();
                    }
                }
            }
        });

        return view;
    }

    private void getPatientData(String user_email, String user_password) {

        APIService apiService = ServiceGenerator.createService(APIService.class);
        Call<PatientModel> call = apiService.getPatientData(user_email,user_password);

        call.enqueue(new Callback<PatientModel>() {
            @Override
            public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {

                if (response.isSuccessful()){

                    if (response.body() == null){
                        progressDialog.cancel();
                        new MaterialDialog.Builder(getContext())
                                .title("No account found")
                                .content("Sorry, We can not find your account in our server!.")
                                .positiveText("Ok")
                                .positiveColor(ContextCompat.getColor(getContext(), R.color.material_green))
                                .build().show();

                    }else{
                        patientModel = response.body();
                        txt_first_name_prof.setText(patientModel.getFirstName().toString());
                        txt_last_name_prof.setText(patientModel.getLastName().toString());
                        txt_user_email_prof.setText(patientModel.getEmail().toString());
                        txt_user_age_prof.setText(patientModel.getAge().toString());

                        txt_user_address_prof.setText(patientModel.getAddress().toString());
                        txt_user_phone_prof.setText(patientModel.getContactNo().toString());
                        txt_user_rfid_prof.setText(patientModel.getRfidCode().toString());
                        txt_user_password_prof.setText(patientModel.getPassword().toString());
                        txt_user_confirm_password_prof.setText(patientModel.getPassword().toString());

                        if (patientModel.getSex().equals("Male")){
                            radio_male_Prof.setChecked(true);
                            radio_female_Prof.setChecked(false);
                        }else{
                            radio_male_Prof.setChecked(false);
                            radio_female_Prof.setChecked(true);
                        }

                        SharedPreferenceReader.createLoginSession(getContext(),patientModel.getEmail(),patientModel.getPassword(),patientModel.getId(),patientModel.getFirstName() + " " +patientModel.getLastName());


                        progressDialog.cancel();
                    }
                }else{
                    progressDialog.cancel();
                    Toast.makeText(getContext(), "Something Wrong, Try again later!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PatientModel> call, Throwable t) {
                progressDialog.cancel();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updatePatient(String textFirstName, String textLasttName, String textEmail, String textAge, String textAddress, String textPhone, String textRfid, String textPassword, String gender) {

        APIService apiService = ServiceGenerator.createService(APIService.class);
        Call<ServerResponse> call = apiService.updatePatientData(textFirstName,textLasttName,textEmail,
                textAge,textAddress,textPhone,textRfid,textPassword, gender);

        final String email_add = textEmail;
        final String user_pass = textPassword;

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                if (response.isSuccessful()) {

                    ServerResponse serverResponse = response.body();
                    if (serverResponse.getResult()){

                        btnUpdateProf.setProgress(100);
                        Toast.makeText(getContext(), serverResponse.getMessage(), Toast.LENGTH_LONG).show();
                        getPatientData(email_add,user_pass);


                    }else{

                        Toast.makeText(getContext(), serverResponse.getMessage(), Toast.LENGTH_LONG).show();

                    }

                }else{

                    btnUpdateProf.setProgress(-1);
                    Toast.makeText(getContext(), "Something Wrong, Try again later!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                btnUpdateProf.setProgress(-1);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
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
}
