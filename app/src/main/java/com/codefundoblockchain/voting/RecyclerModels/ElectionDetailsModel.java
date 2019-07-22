package com.codefundoblockchain.voting.RecyclerModels;

public class ElectionDetailsModel {

    String title;
    String desc;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    String appId;

    public ElectionDetailsModel(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ElectionDetailsModel(String title, String desc,String appId) {
        this.title = title;
        this.desc = desc;
        this.appId = appId;
    }


}
