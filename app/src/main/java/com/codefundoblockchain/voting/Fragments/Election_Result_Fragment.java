package com.codefundoblockchain.voting.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codefundoblockchain.voting.APIModels.GetAllCandidates;
import com.codefundoblockchain.voting.Activity.MicrosoftLoginActivity;
import com.codefundoblockchain.voting.Adapters.GetAllCandidatesResultsAdapter;
import com.codefundoblockchain.voting.Adapters.UpcomingElectionsRecyclerAdapter;
import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.RecyclerModels.AllCandidatesModel;
import com.codefundoblockchain.voting.RecyclerModels.AllCandidatesResultsModel;
import com.codefundoblockchain.voting.retrofit.AzureApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Election_Result_Fragment extends Fragment {


    private List<AllCandidatesResultsModel> electionsList = new ArrayList<>();
    private GetAllCandidatesResultsAdapter electionsRecyclerAdapter;
    private ProgressDialog pd;
    private String id;

    public Election_Result_Fragment() {
        // Required empty public constructor
    }

    private RecyclerView AllElectionsResults;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_election__result_, container, false);
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            id = bundle.getString("id");
        }

        AllElectionsResults = view.findViewById(R.id.ElectionsResultsRecycler);

        electionsRecyclerAdapter = new GetAllCandidatesResultsAdapter(electionsList,getActivity());
        RecyclerView.LayoutManager electionsLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        AllElectionsResults.setLayoutManager(electionsLayoutManager);
        AllElectionsResults.setItemAnimator(new DefaultItemAnimator());
        AllElectionsResults.setAdapter(electionsRecyclerAdapter);

        pd = new ProgressDialog(getActivity());
        pd.setMessage("loading");
        pd.setCancelable(false);




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
                        electionsList.clear();
                        for(int i=0;i<response.body().getContracts().size();i++){
                            AllCandidatesResultsModel allCandidates = new AllCandidatesResultsModel();
                            allCandidates.setName(response.body().getContracts().get(i).getContractProperties().get(4).getValue());
                            allCandidates.setParty(response.body().getContracts().get(i).getContractProperties().get(3).getValue());
                            allCandidates.setVotes(response.body().getContracts().get(i).getContractProperties().get(2).getValue());
                            electionsList.add(allCandidates);
                        }
                        electionsRecyclerAdapter.notifyDataSetChanged();
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
