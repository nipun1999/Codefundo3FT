package com.codefundoblockchain.voting.RecyclerModels;

public class ElectionDetailsModel {

    String title,desc;

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

    public ElectionDetailsModel(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }


}
