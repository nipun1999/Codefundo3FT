package com.codefundoblockchain.voting.retrofit;

import com.codefundoblockchain.voting.APIModels.GetAllCandidates;
import com.codefundoblockchain.voting.APIModels.GetAllElections;
import com.codefundoblockchain.voting.APIModels.CreateOTPModel;
import com.codefundoblockchain.voting.APIModels.GetUserInfo;
import com.codefundoblockchain.voting.APIModels.VerifyOTPModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("api/v1/applications?")
    Call<GetAllElections> getAllElections();

    @FormUrlEncoded
    @POST("Verifications")
    Call<CreateOTPModel> getOTP(@Field("To") String to,
                                @Field("Channel") String channel);


    @FormUrlEncoded
    @POST("VerificationCheck")
    Call<VerifyOTPModel> verifyOTP(@Field("To") String to,
                                @Field("Code") String code);

    @GET("api/v1/users/me")
    Call<GetUserInfo> getUserInfo();

    @GET("api/v1/contracts")
    Call<GetAllCandidates> getAllCandidates(@Query("workflowId") String id);

}
