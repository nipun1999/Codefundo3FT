package com.codefundoblockchain.voting.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codefundoblockchain.voting.Activity.HomeActivity;
import com.codefundoblockchain.voting.Activity.mobileVerificationActivity;
import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.Utils.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class Candidate_detail_fragment extends Fragment {


    private String party;
    private String name;

    public Candidate_detail_fragment() {
        // Required empty public constructor
    }


    String id;
    private TextView nameTxt,partyTxt;
    private Button voteBtn;
    private DatabaseReference mdatabase;
    private SessionManager sessionManager;
    int flag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_candidate_detail_fragment, container, false);
        Bundle bundle = this.getArguments();


        if(bundle!=null){
            id = bundle.getString("id");
            name = bundle.getString("name");
            party = bundle.getString("party");
        }

        sessionManager = new SessionManager(getActivity());

        sessionManager.setCONTRACT_ID(id);


        nameTxt = view.findViewById(R.id.candidateName);
        partyTxt = view.findViewById(R.id.candidateParty);

        mdatabase = FirebaseDatabase.getInstance().getReference().child("users").child("profile").child(sessionManager.getUSER_ID()).child("electionsVoted");

        nameTxt.setText(name);
        partyTxt.setText(party);

        voteBtn = view.findViewById(R.id.voteBtn);

        voteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                mdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot elections : dataSnapshot.getChildren()){
                            Log.e("candidatesf",elections.child("id").getValue().toString());
                            Log.e("candidates",sessionManager.getAPPLICATION_ID());

                            if(elections.child("id").getValue().toString().equals(sessionManager.getAPPLICATION_ID())){
                                flag=1;
                            }
                        }

                        if(flag==1){
                            Toast.makeText(getActivity(), "You have Already voted for this election", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(),HomeActivity.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getActivity(),mobileVerificationActivity.class);
                            intent.putExtra("activity","vote");
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });




        return view;
    }

}
