package edu.smarthealthcare.smarthealthcareapp.Utils;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Admin on 3/13/2017.
 */

public class ServiceGenerator {

    private static final String BASE_URL = "https://127.0.0.1/testing/";

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)).connectTimeout(30,TimeUnit.SECONDS).readTimeout(30,TimeUnit.SECONDS);


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                    .client(httpClient.build());

    private static Retrofit retrofit = builder.build();



    public static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}