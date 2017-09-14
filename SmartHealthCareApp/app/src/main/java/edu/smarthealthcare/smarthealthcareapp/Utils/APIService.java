package edu.smarthealthcare.smarthealthcareapp.Utils;

import java.util.HashMap;
import java.util.List;

import edu.smarthealthcare.smarthealthcareapp.Classes.BalanceModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.DrugLocationModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.ExpiryModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.FirstAidKitModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.KioskModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.OrderModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.PatientModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.PurchaseHistoryModel;
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

    @POST("register")
    @FormUrlEncoded
    Call<ServerResponse> registerPatientData(@Field("Ifname") String Ufname, @Field("Ilname") String Ulname,
                                             @Field("Iemail") String Uemail, @Field("Iage") String Uage,
                                             @Field("IaddCustomer") String UaddCustomer, @Field("ItelCustomer") String UtelCustomer,
                                             @Field("Irfid") String Urfid, @Field("Ipwd") String Upwd,@Field("Igenradio") String Igenradio);
    @POST("update_profile")
    @FormUrlEncoded
    Call<ServerResponse> updatePatientData(@Field("Ufname") String Ufname, @Field("Ulname") String Ulname,
                                             @Field("Uemail") String Uemail, @Field("Uage") String Uage,
                                             @Field("UaddCustomer") String UaddCustomer, @Field("UtelCustomer") String UtelCustomer,
                                             @Field("Urfid") String Urfid, @Field("Upwd") String Upwd,@Field("Ugenradio") String Igenradio);



    @GET("getDrugPackDetails")
    Call<List<FirstAidKitModel>> getDrugPackData();

    @POST("getDrugPackDetailsByID")
    @FormUrlEncoded
    Call<FirstAidKitModel> getDrugPackOneData(@Field("DrugId") String drug_id);

    @POST("makeOrder")
    @FormUrlEncoded
    Call<ServerResponse> addOrderData(@Field("CustomerId") String CustomerId, @Field("Quantity") String Quantity, @Field("TotalAmount") String TotalAmount, @Field("PackId") String PackId);

    @POST("getOrderDetails")
    @FormUrlEncoded
    Call<List<OrderModel>> getOrderDetails(@Field("CustomerId") String CustomerId);

    @POST("getBalance")
    @FormUrlEncoded
    Call<BalanceModel> getBalance(@Field("patientId") String patientId);

    @POST("getHistory")
    @FormUrlEncoded
    Call<List<PurchaseHistoryModel>> getHistory(@Field("customerId") String customerId);

    @POST("kioskSearchByDrugAvail")
    @FormUrlEncoded
    Call<List<DrugLocationModel>> getDrugPackAvailableLocation(@Field("PackId") String PackId);

    @GET("getKioskLocationByDrug")
    Call<List<KioskModel>> getKioskLocation();

    @GET("getKioskLocationByDrug")
    Call<List<KioskModel>> getKioskLocationByDrugName(@Field("drug_name") String drug_name);

    @POST("deleteOrderDetails")
    @FormUrlEncoded
    Call<ServerResponse> deleteOrderDetails(@Field("OrderId") String OrderId);

    @POST("requestExpiryDateNotification")
    @FormUrlEncoded
    Call<ServerResponse> requestExpiryDateNotification(@Field("patientID") String patientID, @Field("barCode") String barCode);

    @POST("getExpiryDetails")
    @FormUrlEncoded
    Call<List<ExpiryModel>> getExpiryDetails(@Field("patientId") String patientId);

    @POST("updateToken")
    @FormUrlEncoded
    Call<ServerResponse> updateFcmToken(@Field("email") String email, @Field("token") String token);



}
