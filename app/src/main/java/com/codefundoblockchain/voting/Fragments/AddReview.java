package com.codefundoblockchain.voting.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codefundoblockchain.voting.APIModels.SentimentalAnalysisBodyModel;
import com.codefundoblockchain.voting.APIModels.SentimentalAnalysisModel;
import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.RecyclerModels.AllCandidatesReviews;
import com.codefundoblockchain.voting.retrofit.SentimentalAnalysisApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddReview extends Fragment {


    private ArrayAdapter<String> adapter;
    private ProgressDialog pd;

    public AddReview() {
        // Required empty public constructor
    }

    EditText review;
    Button submitBtn;
    Spinner spinner;
    DatabaseReference mdatabase;
    List<String> spinnerArray = new ArrayList<>();
    List<SentimentalAnalysisBodyModel.Document> bodyList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_review, container, false);

        review = v.findViewById(R.id.reviewEdtTxt);
        submitBtn = v.findViewById(R.id.submitBtn);
        spinner = v.findViewById(R.id.nameSpinner);

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Candidate_Reviews");


        adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        pd = new ProgressDialog(getActivity());
        pd.setMessage("loading");
        pd.setCancelable(false);


        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot AllNews : dataSnapshot.getChildren()){
                    spinnerArray.add(AllNews.child("Name").getValue().toString());

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.show();

                bodyList.clear();
                String selected = spinner.getSelectedItem().toString();
                String reviewTxt = review.getText().toString();

                SentimentalAnalysisBodyModel.Document document = new SentimentalAnalysisBodyModel.Document();
                document.setId("1");
                document.setLanguage("en");
                document.setText(reviewTxt);
                bodyList.add(document);

                SentimentalAnalysisBodyModel body = new SentimentalAnalysisBodyModel();

                body.setDocuments(bodyList);
                Call<SentimentalAnalysisModel> call = SentimentalAnalysisApiClient.getClient().analyseText(body);
                call.enqueue(new Callback<SentimentalAnalysisModel>() {
                    @Override
                    public void onResponse(Call<SentimentalAnalysisModel> call, Response<SentimentalAnalysisModel> response) {
                        pd.dismiss();
                        if(response.code()==200){
                            Double score = Double.parseDouble(response.body().getDocuments().get(0).getScore().toString());
                            if(score>0.5){
                                mdatabase.child(selected).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        int count = Integer.parseInt(dataSnapshot.child("Positive").getValue().toString());
                                        count+=1;
                                        HashMap<String,Object> map = new HashMap<>();
                                        map.put("Positive",count);
                                        mdatabase.child(selected).updateChildren(map);
                                        Toast.makeText(getActivity(), "Review Successfullly Added", Toast.LENGTH_SHORT).show();

                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction transaction = fragmentManager.beginTransaction();

                                        transaction.replace(R.id.content, new GetAllCandidateReviews()).addToBackStack("tag").commit();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }else{
                                mdatabase.child(selected).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        int count = Integer.parseInt(dataSnapshot.child("Negative").getValue().toString());
                                        count+=1;
                                        HashMap<String,Object> map = new HashMap<>();
                                        map.put("Negative",count);
                                        mdatabase.child(selected).updateChildren(map);

                                        Toast.makeText(getActivity(), "Review Successfully Added", Toast.LENGTH_SHORT).show();

                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction transaction = fragmentManager.beginTransaction();

                                        transaction.replace(R.id.content, new GetAllCandidateReviews()).addToBackStack("tag").commit();

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }

                        }else{
                            Log.e("analysis",response.message());
                            Toast.makeText(getActivity(), "Failed to analyze text. Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SentimentalAnalysisModel> call, Throwable t) {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Some error occured", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        return v;
    }

}
