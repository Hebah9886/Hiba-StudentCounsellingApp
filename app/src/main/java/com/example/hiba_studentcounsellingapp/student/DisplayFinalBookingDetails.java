package com.example.hiba_studentcounsellingapp.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hiba_studentcounsellingapp.R;

public class DisplayFinalBookingDetails extends AppCompatActivity {
    TextView s_name, s_contact, name, quali, expereince, expertise, date, time;
    Button ok;

    String get_s_name, get_s_contact, get_name, get_quali, get_expereince, get_expertise, get_date, get_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_final_booking_details);

        s_name= findViewById(R.id.b_s_name);
        s_contact= findViewById(R.id.b_s_contact);
        name= findViewById(R.id.b_c_name);
        quali= findViewById(R.id.b_quali);
        expereince= findViewById(R.id.b_experience);
        expertise= findViewById(R.id.b_expertise);
        date= findViewById(R.id.b_date);
        time= findViewById(R.id.b_Time);
        ok= findViewById(R.id.ok);




        if (getIntent().hasExtra("name") && getIntent().hasExtra("contact") &&
                getIntent().hasExtra("c_name") && getIntent().hasExtra("qualification")
                && getIntent().hasExtra("experience") && getIntent().hasExtra("expertise")
                && getIntent().hasExtra("date") && getIntent().hasExtra("time")) {

            get_s_name= getIntent().getStringExtra("name");
            get_s_contact= getIntent().getStringExtra("contact");
            get_name= getIntent().getStringExtra("c_name");
            get_quali= getIntent().getStringExtra("qualification");
            get_expereince= getIntent().getStringExtra("experience");
            get_expertise= getIntent().getStringExtra("expertise");
            get_date= getIntent().getStringExtra("date");
            get_time= getIntent().getStringExtra("time");


            s_name.setText("Student Name: "+get_s_name);
            s_contact.setText("Student Contact: "+get_s_contact);
            name.setText("Counsellor Name: "+get_name);
            quali.setText("Qualification: "+get_quali);
            expereince.setText("Experience: "+get_expereince);
            expertise.setText("Expertise: "+get_expertise);
            date.setText("Date: "+get_date);
            time.setText("Time: "+get_time);

        }
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DisplayFinalBookingDetails.this, StudentHomePage.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DisplayFinalBookingDetails.this, StudentHomePage.class));
        finish();
    }
}