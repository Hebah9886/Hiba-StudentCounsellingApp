package com.example.hiba_studentcounsellingapp.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hiba_studentcounsellingapp.R;
import com.example.hiba_studentcounsellingapp.UserBooking;
import com.example.hiba_studentcounsellingapp.UserInformation;
import com.example.hiba_studentcounsellingapp.counsellor.DisplayCounsellorInfo;
import com.example.hiba_studentcounsellingapp.counsellor.UserCounsellor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class BookAppointment extends AppCompatActivity {

    TextView name, qualification, experience, expertise;
    Button mDatebtn, mTimebtn;
    Button confirm_ap;

    DatabaseReference ref,ref1;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String getKey;
    String date ,time;
    String timeTonotify;
    String s_name, s_contact;
    String c_name, c_qualification, c_experience, c_expertise;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        name= findViewById(R.id.d_cn_name);
        qualification= findViewById(R.id.d_cn_qualification);
        experience= findViewById(R.id.d_cn_experience);
        expertise= findViewById(R.id.d_cn_expertise);
        confirm_ap= findViewById(R.id.confirm_ap);
        mDatebtn = (Button) findViewById(R.id.btnDate);                                             //assigned all the material reference to get and set data
        mTimebtn = (Button) findViewById(R.id.btnTime);

        firebaseAuth= FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (getIntent().hasExtra("key")) {
            getKey = getIntent().getStringExtra("key");
        }

        ref= FirebaseDatabase.getInstance().getReference("CounsellorsProfile").child(getKey);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserCounsellor userCounsellor= snapshot.getValue(UserCounsellor.class);
                name.setText("Counsellor Name: "+userCounsellor.getCounsellorName());
                qualification.setText("Qualification: "+userCounsellor.getCounsellorQualification());
                experience.setText("Years of Experience: "+userCounsellor.getCounsellorExperience());
                expertise.setText("Expertise: "+userCounsellor.getCounsellorExpertise());
                c_name=userCounsellor.getCounsellorName();
                c_qualification= userCounsellor.getCounsellorQualification();
                c_experience=userCounsellor.getCounsellorExperience();
                c_expertise= userCounsellor.getCounsellorExpertise();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Profile");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                s_name= userInformation.getUserName();
                s_contact= userInformation.getUserPhone();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BookAppointment.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        mTimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();                                                                       //when we click on the choose time button it calls the select time method
            }
        });
        mDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });

        confirm_ap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    ref1= FirebaseDatabase.getInstance().getReference("Bookings").child(user.getUid()).child("own");
                    UserBooking userBooking = new UserBooking(s_name, s_contact, c_name, c_qualification, c_experience, c_expertise, time, date);
                    ref1.child(ref.push().getKey()).setValue(userBooking);
                    Toast.makeText(BookAppointment.this, "Booking completed Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookAppointment.this, DisplayFinalBookingDetails.class);
                    intent.putExtra("name", s_name);
                    intent.putExtra("contact", s_contact);
                    intent.putExtra("c_name", c_name);
                    intent.putExtra("qualification", c_qualification);
                    intent.putExtra("experience", c_experience);
                    intent.putExtra("expertise", c_expertise);
                    intent.putExtra("date", date);
                    intent.putExtra("time", time);
                    startActivity(intent);
                    finish();
                }

            }
        });


    }

    private Boolean validate(){
        boolean result= false;

        date = mDatebtn.getText().toString().trim();                                 //access the date from the choose date button
        time = mTimebtn.getText().toString().trim();
        if (time.equals("Select time") || date.equals("Select date")) {                                               //shows toast if date and time are not selected
            Toast.makeText(getApplicationContext(), "Please select date and time", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    private void selectTime() {                                                                     //this method performs the time picker task
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeTonotify = i + ":" + i1;                                                        //temp variable to store the time to set alarm
                mTimebtn.setText(FormatTime(i, i1));                                                //sets the button text as selected time
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }
    private void selectDate() {                                                                     //this method performs the date picker task
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mDatebtn.setText(day + "-" + (month + 1) + "-" + year);                             //sets the selected date as test for button
            }
        }, year, month, day);
        datePickerDialog.show();
    }
    public String FormatTime(int hour, int minute) {                                                //this method converts the time into 12hr format and assigns am or pm
        String time;
        time = "";
        String formattedMinute;
        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }
        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }
        return time;
    }

    //date time code end

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BookAppointment.this, AddAppointment.class));
        finish();
    }
}