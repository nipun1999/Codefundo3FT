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
import com.codefundoblockchain.voting.Utils.SessionManager;

import java.util.List;

public class GetAllCandidatesResultsAdapter extends RecyclerView.Adapter<GetAllCandidatesResultsAdapter.MyViewHolder>  {


    private List<AllCandidatesResultsModel> candidatesList;
    private View itemView;
    private Context mcontext;
    private SessionManager sessionManager;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,party,votes;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.candidateName);
            party = view.findViewById(R.id.candidateParty);
            votes = view.findViewById(R.id.candidateVotes);
            sessionManager = new SessionManager(mcontext);

        }
    }



    public GetAllCandidatesResultsAdapter(List<AllCandidatesResultsModel> candidatesList, Context context){
        this.candidatesList = candidatesList;
        this.mcontext = context;
    }


    @NonNull
    @Override
    public GetAllCandidatesResultsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.election_candidates_result, parent, false);
        return new GetAllCandidatesResultsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllCandidatesResultsAdapter.MyViewHolder holder, int position) {
        AllCandidatesResultsModel candidates = candidatesList.get(position);
        holder.name.setText(candidates.getName());
        holder.party.setText(candidates.getParty());
        holder.votes.setText(candidates.getVotes());
    }

    @Override
    public int getItemCount() {
        return candidatesList.size();
    }
}
