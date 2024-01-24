package com.example.hiba_studentcounsellingapp.student;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hiba_studentcounsellingapp.R;

public class AddAppointment extends AppCompatActivity {
    Button searchByName, searchByExp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        searchByExp= findViewById(R.id.searchByExp);
        searchByName= findViewById(R.id.searchByName);

        searchByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAppointment.this, SearchCounsellorUsingName.class));
                finish();
            }
        });

        searchByExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAppointment.this, SearchCounsellorUsingExpertise.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddAppointment.this, StudentHomePage.class));
        finish();
    }
}