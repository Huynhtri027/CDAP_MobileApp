package edu.smarthealthcare.smarthealthcareapp.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import edu.smarthealthcare.smarthealthcareapp.Adapters.FistAidKitAdapter;

import edu.smarthealthcare.smarthealthcareapp.Classes.FirstAidKitModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.PatientModel;
import edu.smarthealthcare.smarthealthcareapp.LoginActivity;
import edu.smarthealthcare.smarthealthcareapp.R;
import edu.smarthealthcare.smarthealthcareapp.Utils.APIService;
import edu.smarthealthcare.smarthealthcareapp.Utils.NetConnect;
import edu.smarthealthcare.smarthealthcareapp.Utils.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFirstAidKit extends Fragment {

    private ProgressDialog progressDialog;
    private List<FirstAidKitModel>
            firstAidKitModelList = new ArrayList<FirstAidKitModel>();
    FistAidKitAdapter firFistAidKitAdapter;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private View view;


    public FragmentFirstAidKit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        SlideImageView slideImageView = (SlideImageView) findViewById(R.id.img_horizontal_slide);
//        slideImageView.setSource(R.drawable.wide_background);
//        slideImageView.setRate(0.3f);
//        slideImageView.setAxis(SlideImageView.Axis.HORIZONTAL);


        view = inflater.inflate(R.layout.fragment_first_aid_kit, container, false);


        if (NetConnect.isNetworkConnected(getContext())){

            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            progressDialog.setTitle("Please wait!!");
            progressDialog.setMessage("Please wait!!");
            progressDialog.setCancelable(false);

            loadFirstAidKitData();

        }else{

            new MaterialDialog.Builder(getContext())
                    .title("No Internet")
                    .content("Please check your WiFi/Mobile Data settings and try again.")
                    .positiveText("OK")
                    .positiveColor(ContextCompat.getColor(getContext(), R.color.material_green))
                    .build().show();

        }

        recyclerView = (RecyclerView) view.findViewById(R.id.listview_firstaid_kit);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        firFistAidKitAdapter = new FistAidKitAdapter(firstAidKitModelList);
        recyclerView.setAdapter(firFistAidKitAdapter);


        return view;
    }

    private void loadFirstAidKitData() {

        APIService apiService = ServiceGenerator.createService(APIService.class);
        Call<List<FirstAidKitModel>> call = apiService.getDrugPackData();
        call.enqueue(new Callback<List<FirstAidKitModel>>() {
            @Override
            public void onResponse(Call<List<FirstAidKitModel>> call, Response<List<FirstAidKitModel>> response) {

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

                        firstAidKitModelList.clear();
                        firstAidKitModelList.addAll(response.body());

                        firFistAidKitAdapter.setFirstAidKits(firstAidKitModelList);
                        firFistAidKitAdapter.notifyDataSetChanged();
                        progressDialog.cancel();
                    }

                }else{

                    Toast.makeText(getContext(), "Something Wrong, Try again later!", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }

            }

            @Override
            public void onFailure(Call<List<FirstAidKitModel>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.cancel();
            }
        });
    }

}
