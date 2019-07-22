package com.codefundoblockchain.voting.RecyclerModels;

public class AllCandidatesModel {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAssets() {
        return assets;
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }

    public AllCandidatesModel(String name, String party, String age, String area, String assets,String id) {
        this.name = name;
        this.party = party;
        this.age = age;
        this.area = area;
        this.assets = assets;
        this.id = id;
    }

    String name;
    String party;
    String age;
    String area;
    String assets;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AllCandidatesModel(){

    }

    String id;
}
