package com.example.hiba_studentcounsellingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hiba_studentcounsellingapp.counsellor.CounsellorHomePage;
import com.example.hiba_studentcounsellingapp.counsellor.CounsellorLogin;
import com.example.hiba_studentcounsellingapp.student.StudentHomePage;
import com.example.hiba_studentcounsellingapp.student.StudentLogin;
import com.example.hiba_studentcounsellingapp.student.StudentRegistration;

public class UserType extends AppCompatActivity {
    Button student, counsellor;
    public static final String SHARED_PREFS= "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        student= (Button) findViewById(R.id.student);
        counsellor= (Button) findViewById(R.id.counsellor);

        checkBox();

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserType.this, StudentLogin.class));
                finish();
            }
        });

        counsellor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserType.this, CounsellorLogin.class));
                finish();
            }
        });

    }


    private void checkBox() {
        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String check= sharedPreferences.getString("name", "");

        if (check.equals("student")){
            startActivity(new Intent(UserType.this, StudentHomePage.class));
            finish();
        }
        if (check.equals("counsellor")){
            startActivity(new Intent(UserType.this, CounsellorHomePage.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();

    }
}