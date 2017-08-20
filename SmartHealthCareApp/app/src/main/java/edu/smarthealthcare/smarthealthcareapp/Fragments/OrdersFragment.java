package edu.smarthealthcare.smarthealthcareapp.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.smarthealthcare.smarthealthcareapp.Adapters.FistAidKitAdapter;
import edu.smarthealthcare.smarthealthcareapp.Adapters.OrderDetailsAdapter;
import edu.smarthealthcare.smarthealthcareapp.Classes.FirstAidKitModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.OrderModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.ServerResponse;
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
public class OrdersFragment extends Fragment {

    private ProgressDialog progressDialog;
    private List<OrderModel>
            orderModelList = new ArrayList<OrderModel>();
    OrderDetailsAdapter orderDetailsAdapter;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private View view;
    String customer_id, order_id;

    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_orders, container, false);

        if (NetConnect.isNetworkConnected(getContext())){

            customer_id = SharedPreferenceReader.getUserID(getContext());

            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            progressDialog.setTitle("Please wait!!");
            progressDialog.setMessage("Please wait!!");
            progressDialog.setCancelable(false);

            loadOrdersData(customer_id);

        }else{

            new MaterialDialog.Builder(getContext())
                    .title("No Internet")
                    .content("Please check your WiFi/Mobile Data settings and try again.")
                    .positiveText("OK")
                    .positiveColor(ContextCompat.getColor(getContext(), R.color.material_green))
                    .build().show();

        }

        recyclerView = (RecyclerView) view.findViewById(R.id.listview_order_items);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        orderDetailsAdapter = new OrderDetailsAdapter(orderModelList);
        recyclerView.setAdapter(orderDetailsAdapter);

        attachItemTouchHelper();

        return view;
    }

    private void loadOrdersData(String customer_id) {

        APIService apiService = ServiceGenerator.createService(APIService.class);
        Call<List<OrderModel>> call = apiService.getOrderDetails(customer_id);

        call.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {

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

                        orderModelList.clear();
                        orderModelList.addAll(response.body());

                        orderDetailsAdapter.setOrderModelList(orderModelList);
                        orderDetailsAdapter.notifyDataSetChanged();
                        progressDialog.cancel();

                    }

                }else{

                    Toast.makeText(getContext(), "Something Wrong, Try again later!", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.cancel();
            }
        });
    }

    private void attachItemTouchHelper(){

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//
//                int sourceP = viewHolder.getAdapterPosition();
//                int targetP = target.getAdapterPosition();
//                Collections.swap(orderModelList, sourceP, targetP);
//                recyclerView.getAdapter().notifyItemMoved(sourceP,targetP);
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

                new MaterialDialog.Builder(getContext())
                        .title("Cancel Order")
                        .content("Do you really need to cancel your order!.")
                        .positiveText("Confirm")
                        .negativeText("No")
                        .cancelable(false)
                        .positiveColor(ContextCompat.getColor(getContext(), R.color.material_green))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                int position = viewHolder.getAdapterPosition();
                                order_id = orderModelList.get(position).getOrderId();
                                orderModelList.remove(position);
                                removeOrdersData(order_id);
                                recyclerView.getAdapter().notifyItemRemoved(position);


                                
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                loadOrdersData(customer_id);
                            }
                        })
                        .build().show();


            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void removeOrdersData(String order_id) {

        APIService apiService = ServiceGenerator.createService(APIService.class);
        Call<ServerResponse> call = apiService.deleteOrderDetails(order_id);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                if (response.isSuccessful()) {
                    ServerResponse serverResponse = response.body();
                    if (serverResponse.getResult()){
                        Toast.makeText(getContext(), serverResponse.getMessage(), Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(getContext(), serverResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getContext(), "Something Wrong, Try again later!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
