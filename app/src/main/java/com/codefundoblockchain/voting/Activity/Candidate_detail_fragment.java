package com.codefundoblockchain.voting.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codefundoblockchain.voting.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Candidate_detail_fragment extends Fragment {


    public Candidate_detail_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_candidate_detail_fragment, container, false);
    }

}
