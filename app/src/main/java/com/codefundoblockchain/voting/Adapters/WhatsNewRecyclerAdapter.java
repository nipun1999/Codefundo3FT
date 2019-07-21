package com.codefundoblockchain.voting.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.RecyclerModels.WhatsNewModel;

import java.util.List;

public class WhatsNewRecyclerAdapter extends RecyclerView.Adapter<WhatsNewRecyclerAdapter.MyViewHolder> {

    private List<WhatsNewModel> newsList;
    private View itemView;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.titleWhatsNew);

        }
    }

    public WhatsNewRecyclerAdapter(List<WhatsNewModel> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public WhatsNewRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.whats_new_card, parent, false);
        return new WhatsNewRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WhatsNewRecyclerAdapter.MyViewHolder holder, int position) {

        WhatsNewModel news = newsList.get(position);
        holder.title.setText(news.getTitle());

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
