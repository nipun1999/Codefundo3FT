package com.codefundoblockchain.voting.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codefundoblockchain.voting.Adapters.GetAllCandidatesReviews;
import com.codefundoblockchain.voting.Adapters.UpcomingElectionsRecyclerAdapter;
import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.RecyclerModels.AllCandidatesReviews;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GetAllCandidateReviews extends Fragment {


    private List<AllCandidatesReviews> reviewsList = new ArrayList<>();
    private RecyclerView GetAllCandidatesReviewsRecycler;
    private GetAllCandidatesReviews electionsRecyclerAdapter;

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

        AllCandidatesReviews reviews = new AllCandidatesReviews();
        reviews.setName("Nipun Agarwal");
        reviews.setParty("BJP");
        reviews.setPositive("100");
        reviews.setNegative("100");
        reviewsList.add(reviews);
        reviewsList.add(reviews);
        reviewsList.add(reviews);



        electionsRecyclerAdapter = new GetAllCandidatesReviews(reviewsList,getActivity());

        GetAllCandidatesReviewsRecycler.setLayoutManager(electionsLayoutManager);
        GetAllCandidatesReviewsRecycler.setItemAnimator(new DefaultItemAnimator());
        GetAllCandidatesReviewsRecycler.setAdapter(electionsRecyclerAdapter);



        return view;
    }

}
