package com.codefundoblockchain.voting.RecyclerModels;

public class AllCandidatesReviews {
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

    public String getPositive() {
        return positive;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

    public String getNegative() {
        return negative;
    }

    public void setNegative(String negative) {
        this.negative = negative;
    }

    public AllCandidatesReviews(){

    }

    public AllCandidatesReviews(String name, String party, String votes, String positive, String negative) {
        this.name = name;
        this.party = party;
        this.votes = votes;
        this.positive = positive;
        this.negative = negative;
    }

    String name,party,votes,positive,negative;

}
