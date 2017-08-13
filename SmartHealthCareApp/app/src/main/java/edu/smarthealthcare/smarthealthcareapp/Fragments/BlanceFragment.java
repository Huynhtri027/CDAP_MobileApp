
package edu.smarthealthcare.smarthealthcareapp.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import edu.smarthealthcare.smarthealthcareapp.Classes.BalanceModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.OrderModel;
import edu.smarthealthcare.smarthealthcareapp.R;
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
public class BlanceFragment extends Fragment {

    private View mView;
    private TextView textViewCurrentBalance, textViewValidTill;
    private ProgressDialog progressDialog;


    public BlanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_blance, container, false);

        textViewValidTill = (TextView) mView.findViewById(R.id.textViewValidTill);
        textViewCurrentBalance = (TextView) mView.findViewById(R.id.textViewCurrentBalance);

        if (NetConnect.isNetworkConnected(getContext())){

            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            progressDialog.setTitle("Please wait!!");
            progressDialog.setMessage("Please wait!!");
            progressDialog.setCancelable(false);

            String user_id = SharedPreferenceReader.getUserID(getContext());
            loadCurrentBalance(user_id);
        }else {

            new MaterialDialog.Builder(getContext())
                    .title("No Internet")
                    .content("Please check your WiFi/Mobile Data settings and try again.")
                    .positiveText("OK")
                    .positiveColor(ContextCompat.getColor(getContext(), R.color.material_green))
                    .build().show();
        }

        return mView;
    }

    private void loadCurrentBalance(String user_id) {

        APIService apiService = ServiceGenerator.createService(APIService.class);
        Call<BalanceModel> call = apiService.getBalance(user_id);

        call.enqueue(new Callback<BalanceModel>() {
            @Override
            public void onResponse(Call<BalanceModel> call, Response<BalanceModel> response) {
                if (response.isSuccessful()){

                    BalanceModel balanceModel = response.body();

                    if (balanceModel.getPatientId() == "" || balanceModel.getPatientId().isEmpty()){

                        new MaterialDialog.Builder(getContext())
                                .title("No data found")
                                .content("Sorry, We can not find relavent data in our server!.")
                                .positiveText("Ok")
                                .positiveColor(ContextCompat.getColor(getContext(), R.color.material_green))
                                .build().show();
                        progressDialog.cancel();

                    }else{

                        textViewCurrentBalance.setText("Rs. "+balanceModel.getBalance().toString());
                        textViewValidTill.setText("Valid Till: \n"+balanceModel.getAddedDate().toString());
                        progressDialog.cancel();
                    }

                }else{

                    Toast.makeText(getContext(), "Something Wrong, Try again later!", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }

            }

            @Override
            public void onFailure(Call<BalanceModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.cancel();
            }
        });
    }

}
