package com.codefundoblockchain.voting.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codefundoblockchain.voting.APIModels.GetAllElections;
import com.codefundoblockchain.voting.Adapters.UpcomingElectionsRecyclerAdapter;
import com.codefundoblockchain.voting.Adapters.WhatsNewRecyclerAdapter;
import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.RecyclerModels.ElectionDetailsModel;
import com.codefundoblockchain.voting.RecyclerModels.WhatsNewModel;
import com.codefundoblockchain.voting.Utils.App;
import com.codefundoblockchain.voting.Utils.SessionManager;
import com.codefundoblockchain.voting.retrofit.AzureApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment {


    public RecyclerView whatsNewRecycler,UpcomingElectionsRecycler;
    private List<WhatsNewModel> newsList = new ArrayList<>();
    private WhatsNewRecyclerAdapter newsRecyclerAdapter;
    private List<ElectionDetailsModel> electionsList = new ArrayList<>();
    private UpcomingElectionsRecyclerAdapter electionsRecyclerAdapter;

    private SessionManager sessionManager;

    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_, container, false);


        whatsNewRecycler = view.findViewById(R.id.whatsNewRecycler);
        UpcomingElectionsRecycler = view.findViewById(R.id.upcomingElectionsRecycler);
        sessionManager = new SessionManager(this.getActivity());

        RecyclerView.LayoutManager newsLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);


        RecyclerView.LayoutManager electionsLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        WhatsNewModel news = new WhatsNewModel();
        news.setTitle("Lok Sabha Elections");
        newsList.add(news);
        newsList.add(news);
        newsList.add(news);
        newsList.add(news);
        newsList.add(news);

        ElectionDetailsModel elections = new ElectionDetailsModel();
        elections.setDesc("jsdhjehdehf eufh efh eufuh euf huefh eufg eufg euf ");
        elections.setTitle("Lok Sabha Elections");
        electionsList.add(elections);
        electionsList.add(elections);
        electionsList.add(elections);
        electionsList.add(elections);
        electionsList.add(elections);
        electionsList.add(elections);


        electionsRecyclerAdapter = new UpcomingElectionsRecyclerAdapter(electionsList);
        newsRecyclerAdapter = new WhatsNewRecyclerAdapter(newsList);

        whatsNewRecycler.setLayoutManager(newsLayoutManager);
        whatsNewRecycler.setItemAnimator(new DefaultItemAnimator());
        whatsNewRecycler.setAdapter(newsRecyclerAdapter);


        UpcomingElectionsRecycler.setLayoutManager(electionsLayoutManager);
        UpcomingElectionsRecycler.setItemAnimator(new DefaultItemAnimator());
        UpcomingElectionsRecycler.setAdapter(electionsRecyclerAdapter);



//        retrofit2.Call<GetAllElections> call = AzureApiClient.getClient().getAllElections();
//        call.enqueue(new Callback<GetAllElections>() {
//            @Override
//            public void onResponse(retrofit2.Call<GetAllElections> call, Response<GetAllElections> response) {
//                if(response.code()==200){
//                    Toast.makeText(getActivity(), response.body().getApplications().get(0).getDisplayName(), Toast.LENGTH_SHORT).show();
//                }else if(response.code()==401){
//                    Toast.makeText(getActivity(), "Unauthorized", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getActivity(), "Some Error Occured", Toast.LENGTH_SHORT).show();
//                    Log.e("GetAllElections",Integer.toString(response.code()));
//                }
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<GetAllElections> call, Throwable t) {
//                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
//            }
//        });









        return view;

    }

}
