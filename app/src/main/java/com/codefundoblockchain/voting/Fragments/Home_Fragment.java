package com.codefundoblockchain.voting.Fragments;


import android.app.ProgressDialog;
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
import com.codefundoblockchain.voting.Activity.HomeActivity;
import com.codefundoblockchain.voting.Activity.MicrosoftLoginActivity;
import com.codefundoblockchain.voting.Adapters.UpcomingElectionsRecyclerAdapter;
import com.codefundoblockchain.voting.Adapters.WhatsNewRecyclerAdapter;
import com.codefundoblockchain.voting.R;
import com.codefundoblockchain.voting.RecyclerModels.ElectionDetailsModel;
import com.codefundoblockchain.voting.RecyclerModels.WhatsNewModel;
import com.codefundoblockchain.voting.Utils.App;
import com.codefundoblockchain.voting.Utils.SessionManager;
import com.codefundoblockchain.voting.retrofit.AzureApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private DatabaseReference mdatabase;

    private SessionManager sessionManager;
    private ProgressDialog pd;

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


        mdatabase = FirebaseDatabase.getInstance().getReference().child("news");
        RecyclerView.LayoutManager electionsLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

//        WhatsNewModel news = new WhatsNewModel();
//        news.setTitle("Lok Sabha Elections");
//        newsList.add(news);
//        newsList.add(news);
//        newsList.add(news);
//        newsList.add(news);
//        newsList.add(news);

//        ElectionDetailsModel elections = new ElectionDetailsModel();
//        elections.setDesc("jsdhjehdehf eufh efh eufuh euf huefh eufg eufg euf ");
//        elections.setTitle("Lok Sabha Elections");
//        electionsList.add(elections);
//        electionsList.add(elections);
//        electionsList.add(elections);
//        electionsList.add(elections);
//        electionsList.add(elections);
//        electionsList.add(elections);


        electionsRecyclerAdapter = new UpcomingElectionsRecyclerAdapter(electionsList,getActivity());
        newsRecyclerAdapter = new WhatsNewRecyclerAdapter(newsList);

        whatsNewRecycler.setLayoutManager(newsLayoutManager);
        whatsNewRecycler.setItemAnimator(new DefaultItemAnimator());
        whatsNewRecycler.setAdapter(newsRecyclerAdapter);


        UpcomingElectionsRecycler.setLayoutManager(electionsLayoutManager);
        UpcomingElectionsRecycler.setItemAnimator(new DefaultItemAnimator());
        UpcomingElectionsRecycler.setAdapter(electionsRecyclerAdapter);

        pd = new ProgressDialog(getActivity());
        pd.setMessage("loading");
        pd.setCancelable(false);


        getAllElections();

        getAllWhatsNew();












        return view;

    }

    private void getAllWhatsNew() {
        pd.show();

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pd.dismiss();
                newsList.clear();
                for(DataSnapshot AllNews : dataSnapshot.getChildren()){
                    WhatsNewModel news = new WhatsNewModel();
                    news.setTitle(AllNews.child("title").getValue().toString());
                    newsList.add(news);
                }
                newsRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pd.dismiss();
            }
        });

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
