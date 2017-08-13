package edu.smarthealthcare.smarthealthcareapp.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dd.processbutton.iml.ActionProcessButton;

import edu.smarthealthcare.smarthealthcareapp.R;
import edu.smarthealthcare.smarthealthcareapp.SignUpActivity;
import edu.smarthealthcare.smarthealthcareapp.Utils.NetConnect;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyAccount extends Fragment {

    View view;

    private ActionProcessButton btnUpdateProf;

    private TextInputEditText txt_first_name_prof, txt_last_name_prof,txt_user_email_prof,txt_user_age_prof, txt_user_address_prof,
            txt_user_phone_prof,txt_user_rfid_prof,txt_user_password_prof,txt_user_confirm_password_prof;

    private RadioGroup radioGroupGenderProf;
    String gender = "";

    public FragmentMyAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_my_account, container, false);

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

    private void updatePatient(String textFirstName, String textLasttName, String textEmail, String textAge, String textAddress, String textPhone, String textRfid, String textPassword, String gender) {


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
