package com.codefundoblockchain.voting.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.RecyclerModels.ElectionDetailsModel;
import com.codefundoblockchain.voting.RecyclerModels.WhatsNewModel;

import java.util.List;

public class UpcomingElectionsRecyclerAdapter extends RecyclerView.Adapter<UpcomingElectionsRecyclerAdapter.MyViewHolder> {



    private List<ElectionDetailsModel> electionsList;
    private View itemView;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,desc;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.titleUpcomingElection);
            desc = view.findViewById(R.id.descUpcomingElection);

        }
    }

    public UpcomingElectionsRecyclerAdapter(List<ElectionDetailsModel> electionsList) {
        this.electionsList = electionsList;
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

    }

    @Override
    public int getItemCount() {
        return electionsList.size();
    }
}
