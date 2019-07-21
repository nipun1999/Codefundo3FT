package com.codefundoblockchain.voting.retrofit;

import android.util.Log;

import com.codefundoblockchain.voting.Utils.App;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TwillioApiClient {

    private static ApiInterface apiInterface;

    public static ApiInterface getClient() {
        if (apiInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://verify.twilio.com/v2/Services/VA08f284e8797f961c1e0ff725139a16e0/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getHttpClient()).build();

            apiInterface = retrofit.create(ApiInterface.class);
        }
        return apiInterface;
    }

    private static OkHttpClient getHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder = request.newBuilder();
                builder.addHeader("Authorization", "Basic QUMzNjQyOWIxNmI2MGYxZTdjMDZhMDBjMzFjZTdjNjY5OTpkMzA1MjA1ODQ5YjY2MTQ5NmQxOWU1ODliMjBmNTU2OA==");
                request = builder.build();
                return chain.proceed(request);
            }
        });
        return httpClient.build();
    }
}
