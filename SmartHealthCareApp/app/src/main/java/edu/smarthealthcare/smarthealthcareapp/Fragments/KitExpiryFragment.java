package edu.smarthealthcare.smarthealthcareapp.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import edu.smarthealthcare.smarthealthcareapp.Adapters.ExpiryDetailsAdapter;
import edu.smarthealthcare.smarthealthcareapp.Adapters.FistAidKitAdapter;
import edu.smarthealthcare.smarthealthcareapp.Classes.ExpiryModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.FirstAidKitModel;
import edu.smarthealthcare.smarthealthcareapp.R;
import edu.smarthealthcare.smarthealthcareapp.ScannerActivity;
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
public class KitExpiryFragment extends Fragment {

    private ProgressDialog progressDialog;
    private List<ExpiryModel>
            expiryModelList = new ArrayList<ExpiryModel>();
    ExpiryDetailsAdapter expiryDetailsAdapter;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private View view;
    private Button btnViewScan;


    public KitExpiryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kit_expiry, container, false);
        btnViewScan = (Button) view.findViewById(R.id.btnViewScan);

        btnViewScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), ScannerActivity.class);
                startActivity(i); // activity opens here

            }
        });


        if (NetConnect.isNetworkConnected(getContext())){

            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            progressDialog.setTitle("Please wait!!");
            progressDialog.setMessage("Please wait!!");
            progressDialog.setCancelable(false);

            loadExpiryData();

        }else{

            new MaterialDialog.Builder(getContext())
                    .title("No Internet")
                    .content("Please check your WiFi/Mobile Data settings and try again.")
                    .positiveText("OK")
                    .positiveColor(ContextCompat.getColor(getContext(), R.color.material_green))
                    .build().show();

        }


        recyclerView = (RecyclerView) view.findViewById(R.id.listview_expiry_items); // to change
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        expiryDetailsAdapter = new ExpiryDetailsAdapter(expiryModelList);
        recyclerView.setAdapter(expiryDetailsAdapter);

        return view;
    }

    private void loadExpiryData() {

        String patient_id = SharedPreferenceReader.getUserID(getContext());

        APIService apiService = ServiceGenerator.createService(APIService.class);
        Call<List<ExpiryModel>> call = apiService.getExpiryDetails(patient_id);

        call.enqueue(new Callback<List<ExpiryModel>>() {
            @Override
            public void onResponse(Call<List<ExpiryModel>> call, Response<List<ExpiryModel>> response) {

                if (response.isSuccessful()){

                    if (response.body().isEmpty()){

                        new MaterialDialog.Builder(getContext())
                                .title("No data found")
                                .content("Sorry, We can not find relavent data in our server!.")
                                .positiveText("Ok")
                                .positiveColor(ContextCompat.getColor(getContext(), R.color.material_green))
                                .build().show();
                        progressDialog.cancel();

                    }else{

                        expiryModelList.clear();
                        expiryModelList.addAll(response.body());

                        expiryDetailsAdapter.setOrderModelList(expiryModelList);
                        expiryDetailsAdapter.notifyDataSetChanged();
                        progressDialog.cancel();
                    }

                }else{

                    Toast.makeText(getContext(), "Something Wrong, Try again later!", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }

            }

            @Override
            public void onFailure(Call<List<ExpiryModel>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.cancel();
            }
        });

    }

}
