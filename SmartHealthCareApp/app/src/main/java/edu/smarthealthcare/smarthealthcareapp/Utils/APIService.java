package edu.smarthealthcare.smarthealthcareapp.Utils;

import java.util.HashMap;
import java.util.List;

import edu.smarthealthcare.smarthealthcareapp.Classes.PatientModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface APIService {

    @POST("patient")
    Call<PatientModel> patient(@Body HashMap<String, String> patientData);

//    @GET("order_count/{user_id}")
//    Call<DashboardData> getDashboardData(@Path("user_id") String userId);
//
//    @GET("status_orders/{user_id}/{status}")
//    Call<List<OrderItem>> getOrdersForStatus(@Path("user_id") String userId, @Path("status") String status);
//
//    @POST("insert_invoice_history")
//    Call<ServerResponse> updateStatus(@Body HashMap<String, String> statusUpdateData);
//
//    @POST("get_delivery_companies")
//    Call<GDOptionsResponse> getGeniusDeliveryOptions(@Body HashMap<String, String> geniusDeliveryRequestData);
//
//    @POST("insert_genius_record")
//    Call<ServerResponse> submitGeniusDeliveryOption(@Body HashMap<String, String> geniusDeliverySubmitData);
//
//    @POST("update_fcm_token")
//    Call<ServerResponse> updateFirebaseToken(@Body HashMap<String, String> firebaseTokenData);
//
//    @POST("orders/search")
//    Call<List<OrderItem>> searchOrders(@Body HashMap<String, String> searchData);

}
