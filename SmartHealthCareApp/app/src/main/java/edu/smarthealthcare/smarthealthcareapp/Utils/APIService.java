package edu.smarthealthcare.smarthealthcareapp.Utils;

import java.util.HashMap;
import java.util.List;

import edu.smarthealthcare.smarthealthcareapp.Classes.FirstAidKitModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.OrderModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.PatientModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.ServerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface APIService {

    @POST("getLogin")
    @FormUrlEncoded
    Call<PatientModel> getPatientData(@Field("email") String email, @Field("password") String password);

    @GET("getDrugPackDetails")
    Call<List<FirstAidKitModel>> getDrugPackData();

    @POST("getDrugPackDetailsByID")
    @FormUrlEncoded
    Call<FirstAidKitModel> getDrugPackOneData(@Field("DrugId") String drug_id);

    @POST("makeOrder")
    @FormUrlEncoded
    Call<ServerResponse> addOrderData(@Field("CustomerId") String CustomerId, @Field("Quantity") String Quantity, @Field("TotalAmount") String TotalAmount, @Field("PackId") String PackId);

}
