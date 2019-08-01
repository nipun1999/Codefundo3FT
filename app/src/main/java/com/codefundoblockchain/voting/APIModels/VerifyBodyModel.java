package com.codefundoblockchain.voting.APIModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyBodyModel {
    @SerializedName("faceId1")
    @Expose
    private String faceId1;
    @SerializedName("faceid2")
    @Expose
    private String faceid2;

    public String getFaceId1() {
        return faceId1;
    }

    public void setFaceId1(String faceId1) {
        this.faceId1 = faceId1;
    }

    public String getFaceid2() {
        return faceid2;
    }

    public void setFaceid2(String faceid2) {
        this.faceid2 = faceid2;
    }
}
