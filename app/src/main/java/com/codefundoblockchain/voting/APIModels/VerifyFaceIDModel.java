package com.codefundoblockchain.voting.APIModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyFaceIDModel {
    @SerializedName("isIdentical")
    @Expose
    private Boolean isIdentical;
    @SerializedName("confidence")
    @Expose
    private Double confidence;

    public Boolean getIsIdentical() {
        return isIdentical;
    }

    public void setIsIdentical(Boolean isIdentical) {
        this.isIdentical = isIdentical;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }
}

