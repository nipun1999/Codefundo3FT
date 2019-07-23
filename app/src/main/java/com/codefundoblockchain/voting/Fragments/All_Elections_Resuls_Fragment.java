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

import com.codefundoblockchain.voting.APIModels.GetAllElections;
import com.codefundoblockchain.voting.Activity.MicrosoftLoginActivity;
import com.codefundoblockchain.voting.Adapters.GetAllCandidatesResultsAdapter;
import com.codefundoblockchain.voting.Adapters.UpcomingElectionsRecyclerAdapter;
import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.RecyclerModels.AllCandidatesResultsModel;
import com.codefundoblockchain.voting.RecyclerModels.ElectionDetailsModel;
import com.codefundoblockchain.voting.retrofit.AzureApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class All_Elections_Resuls_Fragment extends Fragment {

    private RecyclerView UpcomingElectionsRecycler;

    private UpcomingElectionsRecyclerAdapter electionsRecyclerAdapter;
    private ProgressDialog pd;
    private List<ElectionDetailsModel> electionsList = new ArrayList<>();

    public All_Elections_Resuls_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all__elections__resuls_, container, false);
        UpcomingElectionsRecycler = view.findViewById(R.id.upcomingElectionsResultsRecycler);
        RecyclerView.LayoutManager electionsLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        electionsRecyclerAdapter = new UpcomingElectionsRecyclerAdapter(electionsList,getActivity(),"results");

        UpcomingElectionsRecycler.setLayoutManager(electionsLayoutManager);
        UpcomingElectionsRecycler.setItemAnimator(new DefaultItemAnimator());
        UpcomingElectionsRecycler.setAdapter(electionsRecyclerAdapter);

        pd = new ProgressDialog(getActivity());
        pd.setMessage("loading");
        pd.setCancelable(false);



        getAllElections();

        return view;
    }



    private void getAllElections() {
        electionsList.clear();
        pd.show();
        retrofit2.Call<GetAllElections> call = AzureApiClient.getClient().getAllElections();
        call.enqueue(new Callback<GetAllElections>() {
            @Override
            public void onResponse(retrofit2.Call<GetAllElections> call, Response<GetAllElections> response) {
                pd.dismiss();
                if(response.code()==200){


                    if(response.body().getApplications().size()!=0){
                        for(int i=0;i<response.body().getApplications().size();i++){
                            ElectionDetailsModel elections = new ElectionDetailsModel();
                            String name = response.body().getApplications().get(i).getDisplayName();
                            String desc = response.body().getApplications().get(i).getDescription();
                            String appId = Integer.toString(response.body().getApplications().get(i).getId());
                            elections.setTitle(name);
                            elections.setDesc(desc);
                            elections.setAppId(appId);
                            electionsList.add(elections);
                        }

                        electionsRecyclerAdapter.notifyDataSetChanged();
                    }
//                    Toast.makeText(getActivity(), response.body().getApplications().get(0).getDisplayName(), Toast.LENGTH_SHORT).show();
                }else if(response.code()==401){
                    Toast.makeText(getActivity(), "Unauthorized", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(),MicrosoftLoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "Some Error Occured", Toast.LENGTH_SHORT).show();
                    Log.e("GetAllElections",Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(retrofit2.Call<GetAllElections> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
