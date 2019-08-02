package com.codefundoblockchain.voting.APIModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SentimentalAnalysisModel {
    @SerializedName("documents")
    @Expose
    private List<Document> documents = null;
    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }
    public class Document {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("score")
        @Expose
        private Double score;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

    }
}
