package com.example.hiba_studentcounsellingapp.counsellor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.hiba_studentcounsellingapp.R;
import com.example.hiba_studentcounsellingapp.UserFeedback;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ViewFeedback extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ImageAdapterViewFeeback mAdapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private ProgressBar mProgressCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view9);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle9);

        FirebaseRecyclerOptions<UserFeedback> options =
                new FirebaseRecyclerOptions.Builder<UserFeedback>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Feedbacks"), UserFeedback.class)
                        .build();

        mAdapter = new ImageAdapterViewFeeback(options);
        mRecyclerView.setAdapter(mAdapter);
        mProgressCircle.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewFeedback.this, CounsellorHomePage.class));
        finish();
    }
}