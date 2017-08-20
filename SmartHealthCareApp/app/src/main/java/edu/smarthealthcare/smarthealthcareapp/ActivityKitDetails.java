package edu.smarthealthcare.smarthealthcareapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDButton;
import com.gigamole.slideimageview.lib.SlideImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.smarthealthcare.smarthealthcareapp.Adapters.DrugLocationAdapter;
import edu.smarthealthcare.smarthealthcareapp.Adapters.FistAidKitAdapter;
import edu.smarthealthcare.smarthealthcareapp.Classes.DrugLocationModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.FirstAidKitModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.OrderModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.SectionsPagerAdaper;
import edu.smarthealthcare.smarthealthcareapp.Classes.ServerResponse;
import edu.smarthealthcare.smarthealthcareapp.Utils.APIService;
import edu.smarthealthcare.smarthealthcareapp.Utils.NetConnect;
import edu.smarthealthcare.smarthealthcareapp.Utils.ServiceGenerator;
import edu.smarthealthcare.smarthealthcareapp.Utils.SharedPreferenceReader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityKitDetails extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private ViewPager viewPager;
    private SectionsPagerAdaper mSectionsPagerAdaper;

    private TabLayout mTabLayout;
    private MaterialDialog.Builder dialog;

    private Button btnOder, btnProductAvail;
    private TextView prodName;
    private TextView prodID;
    private Spinner prodSize;
    private Spinner prodQty;

    private String ProductName;
    private int ProductID;
    private int size;
    private int quantity;

    private String global_product_name, global_product_id, global_product_price;
    private TextView txtProductDescDetails, txtProductPriceProdDetails,txtProductNameProdDetails;
    private ImageView imageViewProductDetail;


    private List<DrugLocationModel>
            drugLocationModelList = new ArrayList<DrugLocationModel>();
    DrugLocationAdapter drugLocationAdapter;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kit_details);
        //Tabs
//

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_new);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        txtProductNameProdDetails = (TextView)findViewById(R.id.txtProductNameProdDetails);
        txtProductPriceProdDetails = (TextView)findViewById(R.id.txtProductPriceProdDetails);
        txtProductDescDetails = (TextView)findViewById(R.id.txtProductDescDetails);

        imageViewProductDetail = (ImageView)findViewById(R.id.imageViewProductDetail);

        btnOder = (Button)findViewById(R.id.btn_order_now);
        btnProductAvail = (Button)findViewById(R.id.btnProductAvail);

        if (NetConnect.isNetworkConnected(this)){

            Intent i = getIntent();
            String drug_id = i.getStringExtra("Id");

            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setTitle("Please wait!!");
            progressDialog.setMessage("Please wait!!");
            progressDialog.setCancelable(false);

            getDrugPackOneData(drug_id);


        }else{

            new MaterialDialog.Builder(this)
                    .title("No Internet")
                    .content("Please check your WiFi/Mobile Data settings and try again.")
                    .positiveText("OK")
                    .positiveColor(ContextCompat.getColor(this, R.color.material_green))
                    .build().show();

        }


    }

    private void getDrugPackOneData(String drug_id) {

        APIService apiService = ServiceGenerator.createService(APIService.class);
        Call<FirstAidKitModel> call = apiService.getDrugPackOneData(drug_id);

        call.enqueue(new Callback<FirstAidKitModel>() {
            @Override
            public void onResponse(Call<FirstAidKitModel> call, Response<FirstAidKitModel> response) {

                if (response.isSuccessful()){

                    FirstAidKitModel firstAidKitModel = response.body();

                    global_product_id = firstAidKitModel.getDrugPackId().toString();
                    global_product_name = firstAidKitModel.getDrugPackName().toString();
                    global_product_price = firstAidKitModel.getUnitPrice().toString();

                    txtProductDescDetails.setText(firstAidKitModel.getInstruction().toString());
                    txtProductNameProdDetails.setText(firstAidKitModel.getDrugPackName().toString());
                    txtProductPriceProdDetails.setText("Rs." + firstAidKitModel.getUnitPrice().toString());


                    if (!firstAidKitModel.getImage().isEmpty() || !(firstAidKitModel.getImage() == "") || !(firstAidKitModel.getImage() == null)){
                        Picasso.with(imageViewProductDetail.getContext())
                                .load("http://smarthealthcaresystem.000webhostapp.com/assets/img/"+firstAidKitModel.getImage().toString())
                                .placeholder(R.drawable.default_image)
                                .error(R.drawable.default_image)
                                .into(imageViewProductDetail);
                    }


                    btnOder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            createDialog(global_product_name,global_product_id, global_product_price);
                        }
                    });

                    btnProductAvail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getDrugPackAvailableLocation(global_product_id);
                            createAvailableDialog(global_product_id);
                        }
                    });

                    progressDialog.cancel();


                }else{
                    Toast.makeText(ActivityKitDetails.this, "Something Wrong, Try again later!", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }

            }

            @Override
            public void onFailure(Call<FirstAidKitModel> call, Throwable t) {
                Toast.makeText(ActivityKitDetails.this, t.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.cancel();
            }
        });
    }

    private void getDrugPackAvailableLocation(String global_product_id) {

        APIService apiService = ServiceGenerator.createService(APIService.class);
        Call<List<DrugLocationModel>> call = apiService.getDrugPackAvailableLocation(global_product_id);

        call.enqueue(new Callback<List<DrugLocationModel>>() {
            @Override
            public void onResponse(Call<List<DrugLocationModel>> call, Response<List<DrugLocationModel>> response) {

                if (response.isSuccessful()){

                    if (response.body().isEmpty()){

                        new MaterialDialog.Builder(ActivityKitDetails.this)
                                .title("No data found")
                                .content("Sorry, We can not find relavent data in our server!.")
                                .positiveText("Ok")
                                .positiveColor(ContextCompat.getColor(ActivityKitDetails.this, R.color.material_green))
                                .build().show();
                        progressDialog.cancel();

                    }else{

                        drugLocationModelList.clear();
                        drugLocationModelList.addAll(response.body());

                        drugLocationAdapter.setDrugLocationModelList(drugLocationModelList);
                        drugLocationAdapter.notifyDataSetChanged();
                        progressDialog.cancel();
                    }

                }else{

                    Toast.makeText(ActivityKitDetails.this, "Something Wrong, Try again later!", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }

            }

            @Override
            public void onFailure(Call<List<DrugLocationModel>> call, Throwable t) {

                Toast.makeText(ActivityKitDetails.this, t.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.cancel();
            }
        });
    }

    private void createAvailableDialog(String global_product_id) {

        View mView = getLayoutInflater().inflate(R.layout.drug_location_layout, null);
        dialog = new MaterialDialog.Builder(ActivityKitDetails.this);



        recyclerView = (RecyclerView) mView.findViewById(R.id.rv_drug_location);
        linearLayoutManager = new LinearLayoutManager(mView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        drugLocationAdapter = new DrugLocationAdapter(drugLocationModelList);
        recyclerView.setAdapter(drugLocationAdapter);


        dialog.title(R.string.available_location);


        dialog.customView(mView, true);
        dialog.negativeText(R.string.close);
        dialog.cancelable(false);
        dialog.build();
        dialog.show();

    }


    public void createDialog(String product_name, String product_id, final String price){

        View mView = getLayoutInflater().inflate(R.layout.fragment_order_product, null);

        dialog = new MaterialDialog.Builder(ActivityKitDetails.this);
        dialog.title(R.string.Confirm_Order);

        prodQty = (Spinner) mView.findViewById(R.id.sel_prod_quantity);
        prodName = (TextView) mView.findViewById(R.id.txtOrderProdName);
        prodID = (TextView)mView.findViewById(R.id.txtOrderProdID);

        prodName.setText(product_name.trim());
        prodID.setText(product_id.trim());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityKitDetails.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.qty_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prodQty.setAdapter(adapter);

        dialog.customView(mView, true);
        dialog.positiveText(R.string.confirm);
        dialog.negativeText(android.R.string.cancel);
        dialog.cancelable(false);
        dialog.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                ProductName = prodName.getText().toString();
                ProductID = Integer.parseInt(prodID.getText().toString());
                quantity = Integer.parseInt(prodQty.getSelectedItem().toString());

                orderNow(ProductID, quantity, price);
            }
        });
        dialog.build();
        dialog.show();
    }

    private void orderNow(int ProductID, int Qty, String price) {

        double price_item = Double.parseDouble(price);
        double total = 0;
        total = Qty * price_item;

        String customer_id = SharedPreferenceReader.getUserID(ActivityKitDetails.this);


        APIService apiService = ServiceGenerator.createService(APIService.class);
        Call<ServerResponse> call = apiService.addOrderData(customer_id,Qty+"",total+"",ProductID+"");

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                if (response.isSuccessful()){

                    ServerResponse serverResponse = response.body();

                    if (serverResponse.getResult()){
                        Toast.makeText(ActivityKitDetails.this, serverResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(ActivityKitDetails.this, "Something Wrong, Try again later!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(ActivityKitDetails.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
