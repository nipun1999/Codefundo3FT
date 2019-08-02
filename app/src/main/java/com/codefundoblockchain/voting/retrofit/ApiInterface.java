package com.codefundoblockchain.voting.retrofit;

import com.codefundoblockchain.voting.APIModels.CreateFaceIDBodyModel;
import com.codefundoblockchain.voting.APIModels.CreateFaceIDModel;
import com.codefundoblockchain.voting.APIModels.GetAllCandidates;
import com.codefundoblockchain.voting.APIModels.GetAllElections;
import com.codefundoblockchain.voting.APIModels.CreateOTPModel;
import com.codefundoblockchain.voting.APIModels.GetUserInfo;
import com.codefundoblockchain.voting.APIModels.SentimentalAnalysisBodyModel;
import com.codefundoblockchain.voting.APIModels.SentimentalAnalysisModel;
import com.codefundoblockchain.voting.APIModels.VerifyBodyModel;
import com.codefundoblockchain.voting.APIModels.VerifyFaceIDModel;
import com.codefundoblockchain.voting.APIModels.VerifyOTPModel;
import com.codefundoblockchain.voting.APIModels.VoteBodyModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    @POST("api/v1/contracts/{id}/actions")
    Call<Void> voteCandidate(@Path("id") String id, @Body VoteBodyModel body);

    @POST("Verify")
    Call<VerifyFaceIDModel> verifyFaceId(@Body VerifyBodyModel body);

    @POST("detect")
    Call<List<CreateFaceIDModel>> createFaceId(@Body CreateFaceIDBodyModel body);

    @POST("sentiment")
    Call<SentimentalAnalysisModel> analyseText(@Body SentimentalAnalysisBodyModel body);


}
