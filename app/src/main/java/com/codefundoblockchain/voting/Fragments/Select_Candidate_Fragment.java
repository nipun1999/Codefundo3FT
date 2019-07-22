package com.codefundoblockchain.voting.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codefundoblockchain.voting.APIModels.GetAllCandidates;
import com.codefundoblockchain.voting.Activity.MicrosoftLoginActivity;
import com.codefundoblockchain.voting.Adapters.GetAllCandidatesRecyclerAdapter;
import com.codefundoblockchain.voting.Adapters.UpcomingElectionsRecyclerAdapter;
import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.RecyclerModels.AllCandidatesModel;
import com.codefundoblockchain.voting.RecyclerModels.ElectionDetailsModel;
import com.codefundoblockchain.voting.Utils.SessionManager;
import com.codefundoblockchain.voting.retrofit.AzureApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Select_Candidate_Fragment extends Fragment {


    private String id;
    private String name,party;
    private RecyclerView allCandidatesRecycler;
    private SessionManager sessionManager;
    private GetAllCandidatesRecyclerAdapter allCandidatesRecyclerAdapter;
    private List<AllCandidatesModel> candidatesList = new ArrayList<>();
    private ProgressDialog pd;


    public Select_Candidate_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select__candidate_, container, false);
        Bundle bundle = this.getArguments();
        allCandidatesRecycler = view.findViewById(R.id.allCandidatesRecyclerView);

        sessionManager = new SessionManager(this.getActivity());



        RecyclerView.LayoutManager electionsLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        allCandidatesRecyclerAdapter = new GetAllCandidatesRecyclerAdapter(candidatesList,getActivity());

        if(bundle!=null){
            id = bundle.getString("id");
        }

        Log.e("candidates",id);

        pd = new ProgressDialog(getActivity());
        pd.setMessage("loading");
        pd.setCancelable(false);


        sessionManager.setAPPLICATION_ID(id);


        allCandidatesRecycler.setLayoutManager(electionsLayoutManager);
        allCandidatesRecycler.setItemAnimator(new DefaultItemAnimator());
        allCandidatesRecycler.setAdapter(allCandidatesRecyclerAdapter);

        getCandidatesData();

        return view;
    }

    private void getCandidatesData() {
        pd.show();
        Call<GetAllCandidates> call = AzureApiClient.getClient().getAllCandidates(id);
        call.enqueue(new Callback<GetAllCandidates>() {
            @Override
            public void onResponse(Call<GetAllCandidates> call, Response<GetAllCandidates> response) {
               pd.dismiss();
                if(response.code()==200){

                    if(response.body().getContracts().size()!=0){
                        candidatesList.clear();
                        for(int i=0;i<response.body().getContracts().size();i++){
                            AllCandidatesModel allCandidates = new AllCandidatesModel();
                            allCandidates.setName(response.body().getContracts().get(i).getContractProperties().get(4).getValue());
                            allCandidates.setParty(response.body().getContracts().get(i).getContractProperties().get(3).getValue());
                           allCandidates.setId(response.body().getContracts().get(i).getId().toString());
                            candidatesList.add(allCandidates);
                        }
                        allCandidatesRecyclerAdapter.notifyDataSetChanged();
                    }

                }else if(response.code()==401){
                    Intent intent = new Intent(getActivity(),MicrosoftLoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "Cant load the candidates", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAllCandidates> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getActivity(), "Some error occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
