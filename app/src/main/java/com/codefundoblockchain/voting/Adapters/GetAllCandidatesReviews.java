package com.codefundoblockchain.voting.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.RecyclerModels.AllCandidatesModel;
import com.codefundoblockchain.voting.RecyclerModels.AllCandidatesResultsModel;
import com.codefundoblockchain.voting.RecyclerModels.AllCandidatesReviews;
import com.codefundoblockchain.voting.Utils.SessionManager;

import java.util.List;

public class GetAllCandidatesReviews extends RecyclerView.Adapter<GetAllCandidatesReviews.MyViewHolder> {


    private List<AllCandidatesReviews> candidatesList;
    private View itemView;
    private Context mcontext;
    private SessionManager sessionManager;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,party,positive,negative;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.candidateName);
            party = view.findViewById(R.id.candidateParty);
            positive = view.findViewById(R.id.positiveReviews);
            negative = view.findViewById(R.id.negativeReviews);

            sessionManager = new SessionManager(mcontext);

        }
    }

    public GetAllCandidatesReviews(List<AllCandidatesReviews> candidatesList, Context context){
        this.candidatesList = candidatesList;
        this.mcontext = context;
    }



    @NonNull
    @Override
    public GetAllCandidatesReviews.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reviews_card_view, parent, false);
        return new GetAllCandidatesReviews.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllCandidatesReviews.MyViewHolder holder, int position) {
        AllCandidatesReviews candidates = candidatesList.get(position);
        holder.name.setText(candidates.getName());
        holder.party.setText(candidates.getParty());
        holder.positive.setText(candidates.getPositive());
        holder.negative.setText(candidates.getNegative());
    }

    @Override
    public int getItemCount() {
        return candidatesList.size();
    }
}
