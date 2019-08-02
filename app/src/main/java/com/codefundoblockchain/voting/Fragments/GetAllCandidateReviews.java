package com.codefundoblockchain.voting.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codefundoblockchain.voting.Adapters.GetAllCandidatesReviews;
import com.codefundoblockchain.voting.Adapters.UpcomingElectionsRecyclerAdapter;
import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.RecyclerModels.AllCandidatesResultsModel;
import com.codefundoblockchain.voting.RecyclerModels.AllCandidatesReviews;
import com.codefundoblockchain.voting.RecyclerModels.WhatsNewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GetAllCandidateReviews extends Fragment {


    private List<AllCandidatesReviews> reviewsList = new ArrayList<>();
    private RecyclerView GetAllCandidatesReviewsRecycler;
    private GetAllCandidatesReviews electionsRecyclerAdapter;
    private String id;
    private DatabaseReference mdatabase;
    private FloatingActionButton addBtn;

    public GetAllCandidateReviews() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get_all_candidate_reviews, container, false);


        GetAllCandidatesReviewsRecycler = view.findViewById(R.id.reviewsRecycler);
        RecyclerView.LayoutManager electionsLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Candidate_Reviews");


        Bundle bundle = this.getArguments();
        if(bundle!=null){
            id = bundle.getString("id");
        }

        addBtn = view.findViewById(R.id.addReviewBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.content, new AddReview()).addToBackStack("tag").commit();

            }
        });




//        AllCandidatesReviews reviews = new AllCandidatesReviews();
//        reviews.setName("Nipun Agarwal");
//        reviews.setParty("BJP");
//        reviews.setPositive("100");
//        reviews.setNegative("100");
//        reviewsList.add(reviews);
//        reviewsList.add(reviews);
//        reviewsList.add(reviews);



        electionsRecyclerAdapter = new GetAllCandidatesReviews(reviewsList,getActivity());

        GetAllCandidatesReviewsRecycler.setLayoutManager(electionsLayoutManager);
        GetAllCandidatesReviewsRecycler.setItemAnimator(new DefaultItemAnimator());
        GetAllCandidatesReviewsRecycler.setAdapter(electionsRecyclerAdapter);


        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reviewsList.clear();
                for(DataSnapshot AllNews : dataSnapshot.getChildren()){
                    AllCandidatesReviews news = new AllCandidatesReviews();
                    news.setName(AllNews.child("Name").getValue().toString());
                    news.setParty(AllNews.child("Party").getValue().toString());
                    news.setNegative(AllNews.child("Negative").getValue().toString());
                    news.setPositive(AllNews.child("Positive").getValue().toString());
                    reviewsList.add(news);
                }

                electionsRecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return view;
    }

}
