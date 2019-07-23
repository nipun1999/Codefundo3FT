package com.codefundoblockchain.voting.RecyclerModels;

public class AllCandidatesResultsModel {
    public AllCandidatesResultsModel(String name, String party, String votes) {
        this.name = name;
        this.party = party;
        this.votes = votes;
    }

    String name,party,votes;

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

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }
    public AllCandidatesResultsModel(){

    }
}
