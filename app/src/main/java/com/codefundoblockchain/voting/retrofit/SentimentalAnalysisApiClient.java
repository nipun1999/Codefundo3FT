package com.codefundoblockchain.voting.retrofit;

import android.util.Log;

import com.codefundoblockchain.voting.Utils.App;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SentimentalAnalysisApiClient {
    private static ApiInterface apiInterface;

    private static Gson gson = new GsonBuilder().serializeNulls().create();


    public static ApiInterface getClient() {
        if (apiInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://electionreview.cognitiveservices.azure.com/text/analytics/v2.1/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
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
                String authToken = App.getInstance().getSessionManager().getBEARER_TOKEN();
                Log.e("token",App.getInstance().getSessionManager().getBEARER_TOKEN());
                builder.addHeader("Ocp-Apim-Subscription-Key", "3ad7e3027f3e485385bce5b7df40d1e9");
                builder.addHeader("Content-Type","application/json");
                request = builder.build();
                return chain.proceed(request);
            }
        });
        return httpClient.build();
    }
}
