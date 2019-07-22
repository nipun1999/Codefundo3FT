package com.codefundoblockchain.voting.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codefundoblockchain.voting.APIModels.GetAllCandidates;
import com.codefundoblockchain.voting.Fragments.Candidate_detail_fragment;
import com.codefundoblockchain.voting.Fragments.Select_Candidate_Fragment;
import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.RecyclerModels.AllCandidatesModel;
import com.codefundoblockchain.voting.RecyclerModels.ElectionDetailsModel;

import java.util.List;

public class GetAllCandidatesRecyclerAdapter extends RecyclerView.Adapter<GetAllCandidatesRecyclerAdapter.MyViewHolder> {


    private List<AllCandidatesModel> candidatesList;
    private View itemView;
    private Context mcontext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,party;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.candidateName);
            party = view.findViewById(R.id.candidateParty);

        }
    }


    public GetAllCandidatesRecyclerAdapter(List<AllCandidatesModel> candidatesList, Context context){
        this.candidatesList = candidatesList;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public GetAllCandidatesRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.candidate_detail_card, parent, false);
        return new GetAllCandidatesRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllCandidatesRecyclerAdapter.MyViewHolder holder, int position) {

        AllCandidatesModel candidates = candidatesList.get(position);
        holder.name.setText(candidates.getName());
        holder.party.setText(candidates.getParty());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity) mcontext).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Candidate_detail_fragment Candidate = new Candidate_detail_fragment();
                Bundle bundle=new Bundle();
                bundle.putString("id",candidatesList.get(position).getId());
                bundle.putString("name",candidates.getName());
                bundle.putString("party",candidates.getParty());
                Candidate.setArguments(bundle);
                transaction.replace(R.id.content, Candidate).addToBackStack("tag").commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return candidatesList.size();
    }
}
