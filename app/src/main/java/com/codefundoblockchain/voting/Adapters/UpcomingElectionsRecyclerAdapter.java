package com.codefundoblockchain.voting.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codefundoblockchain.voting.Fragments.All_Elections_Resuls_Fragment;
import com.codefundoblockchain.voting.Fragments.Election_Result_Fragment;
import com.codefundoblockchain.voting.Fragments.Home_Fragment;
import com.codefundoblockchain.voting.Fragments.Select_Candidate_Fragment;
import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.RecyclerModels.ElectionDetailsModel;
import com.codefundoblockchain.voting.RecyclerModels.WhatsNewModel;

import java.util.List;

public class UpcomingElectionsRecyclerAdapter extends RecyclerView.Adapter<UpcomingElectionsRecyclerAdapter.MyViewHolder> {



    private List<ElectionDetailsModel> electionsList;
    private String string;
    private View itemView;
    private Context mcontext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,desc;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.titleUpcomingElection);
            desc = view.findViewById(R.id.descUpcomingElection);

        }
    }

    public UpcomingElectionsRecyclerAdapter(List<ElectionDetailsModel> electionsList, Context context, String activity) {
        this.electionsList = electionsList;
        this.mcontext = context;
        this.string = activity;
    }




    @NonNull
    @Override
    public UpcomingElectionsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.election_name_card_view, parent, false);
        return new UpcomingElectionsRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingElectionsRecyclerAdapter.MyViewHolder holder, int position) {

        ElectionDetailsModel elections = electionsList.get(position);
        holder.title.setText(elections.getTitle());
        holder.desc.setText(elections.getDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(string.equals("vote")){
                    FragmentManager fragmentManager = ((AppCompatActivity) mcontext).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Select_Candidate_Fragment getAllCandidates = new Select_Candidate_Fragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("id",electionsList.get(position).getAppId());
                    getAllCandidates.setArguments(bundle);
                    transaction.replace(R.id.content, getAllCandidates).addToBackStack("tag").commit();
                }else{
                    FragmentManager fragmentManager = ((AppCompatActivity) mcontext).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Election_Result_Fragment getAllCandidates = new Election_Result_Fragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("id",electionsList.get(position).getAppId());
                    getAllCandidates.setArguments(bundle);
                    transaction.replace(R.id.content, getAllCandidates).addToBackStack("tag").commit();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return electionsList.size();
    }
}
