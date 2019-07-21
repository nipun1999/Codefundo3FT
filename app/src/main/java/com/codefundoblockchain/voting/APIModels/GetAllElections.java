package com.codefundoblockchain.voting.APIModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllElections {
    @SerializedName("nextLink")
    @Expose
    private String nextLink;
    @SerializedName("applications")
    @Expose
    private List<Application> applications = null;

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public class Application {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("version")
        @Expose
        private String version;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("displayName")
        @Expose
        private String displayName;
        @SerializedName("createdByUserId")
        @Expose
        private Integer createdByUserId;
        @SerializedName("createdDtTm")
        @Expose
        private String createdDtTm;
        @SerializedName("enabled")
        @Expose
        private Boolean enabled;
        @SerializedName("blobStorageURL")
        @Expose
        private Object blobStorageURL;
        @SerializedName("isLatestVersion")
        @Expose
        private Object isLatestVersion;
        @SerializedName("applicationRoles")
        @Expose
        private Object applicationRoles;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public Integer getCreatedByUserId() {
            return createdByUserId;
        }

        public void setCreatedByUserId(Integer createdByUserId) {
            this.createdByUserId = createdByUserId;
        }

        public String getCreatedDtTm() {
            return createdDtTm;
        }

        public void setCreatedDtTm(String createdDtTm) {
            this.createdDtTm = createdDtTm;
        }

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public Object getBlobStorageURL() {
            return blobStorageURL;
        }

        public void setBlobStorageURL(Object blobStorageURL) {
            this.blobStorageURL = blobStorageURL;
        }

        public Object getIsLatestVersion() {
            return isLatestVersion;
        }

        public void setIsLatestVersion(Object isLatestVersion) {
            this.isLatestVersion = isLatestVersion;
        }

        public Object getApplicationRoles() {
            return applicationRoles;
        }

        public void setApplicationRoles(Object applicationRoles) {
            this.applicationRoles = applicationRoles;
        }

    }
}
