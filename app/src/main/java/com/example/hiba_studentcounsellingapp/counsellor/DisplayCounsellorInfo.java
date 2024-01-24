package com.example.hiba_studentcounsellingapp.counsellor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hiba_studentcounsellingapp.R;
import com.example.hiba_studentcounsellingapp.student.AddAppointment;
import com.example.hiba_studentcounsellingapp.student.BookAppointment;
import com.example.hiba_studentcounsellingapp.student.SearchCounsellorUsingName;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayCounsellorInfo extends AppCompatActivity {
    TextView name, qualification, experience, expertise;
    Button book_ap;

    DatabaseReference ref;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String getKey;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_counsellor_info);

        name= findViewById(R.id.cn_name);
        qualification= findViewById(R.id.cn_qualification);
        experience= findViewById(R.id.cn_experience);
        expertise= findViewById(R.id.cn_expertise);
        book_ap= findViewById(R.id.book_ap);


        if (getIntent().hasExtra("key")) {
            getKey = getIntent().getStringExtra("key");
        }

        ref= FirebaseDatabase.getInstance().getReference("CounsellorsProfile").child(getKey);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserCounsellor userCounsellor= snapshot.getValue(UserCounsellor.class);
                name.setText(userCounsellor.getCounsellorName());
                qualification.setText(userCounsellor.getCounsellorQualification());
                experience.setText(userCounsellor.getCounsellorExperience());
                expertise.setText(userCounsellor.getCounsellorExpertise());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        book_ap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayCounsellorInfo.this, BookAppointment.class);
                intent.putExtra("key", getKey);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DisplayCounsellorInfo.this, AddAppointment.class));
        finish();
    }
}