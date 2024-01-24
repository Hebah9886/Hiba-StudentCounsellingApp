package com.example.hiba_studentcounsellingapp.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.hiba_studentcounsellingapp.R;
import com.example.hiba_studentcounsellingapp.counsellor.DisplayCounsellorInfo;
import com.example.hiba_studentcounsellingapp.counsellor.UserCounsellor;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class SearchCounsellorUsingExpertise extends AppCompatActivity {
    RecyclerView recview;
    ImageAdapterSearchByExpertise adapter;
    SearchView searchView;
    private ProgressBar mProgressCircle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_counsellor_using_expertise);

        recview = (RecyclerView) findViewById(R.id.recycler_view2);
        recview.setLayoutManager(new LinearLayoutManager(this));

        searchView = (SearchView) findViewById(R.id.searchView2);
        searchView.clearFocus();

        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle2);

        FirebaseRecyclerOptions<UserCounsellor> options =
                new FirebaseRecyclerOptions.Builder<UserCounsellor>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("CounsellorsProfile"), UserCounsellor.class)
                        .build();

        adapter = new ImageAdapterSearchByExpertise(options);
        recview.setAdapter(adapter);
        mProgressCircle.setVisibility(View.INVISIBLE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                processSearch(query);
                return false;
            }
        });

        adapter.setOnItemClickListener(new ImageAdapterSearchByExpertise.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String key= adapter.getRef(position).getKey();
                Intent intent = new Intent(SearchCounsellorUsingExpertise.this, BookAppointment.class);
                intent.putExtra("key", key);
                startActivity(intent);
                finish();

            }
        });

    }


    private void processSearch(String query) {
        FirebaseRecyclerOptions<UserCounsellor> options =
                new FirebaseRecyclerOptions.Builder<UserCounsellor>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("CounsellorsProfile").orderByChild("counsellorExpertise").startAt(query).endAt(query+"\uf8ff"), UserCounsellor.class)
                        .build();

        adapter=new ImageAdapterSearchByExpertise(options);
        adapter.startListening();
        recview.setAdapter(adapter);


        adapter.setOnItemClickListener(new ImageAdapterSearchByExpertise.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String key= adapter.getRef(position).getKey();
                Intent intent = new Intent(SearchCounsellorUsingExpertise.this, DisplayCounsellorInfo.class);
                intent.putExtra("key", key);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SearchCounsellorUsingExpertise.this, AddAppointment.class));
        finish();
    }
}