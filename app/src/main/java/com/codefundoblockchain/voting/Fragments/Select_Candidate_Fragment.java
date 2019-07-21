package com.codefundoblockchain.voting.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codefundoblockchain.voting.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Select_Candidate_Fragment extends Fragment {


    public Select_Candidate_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select__candidate_, container, false);
    }

}
