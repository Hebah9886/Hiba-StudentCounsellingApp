package com.example.hiba_studentcounsellingapp.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hiba_studentcounsellingapp.R;
import com.example.hiba_studentcounsellingapp.UserFeedback;
import com.example.hiba_studentcounsellingapp.counsellor.UserCounsellor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddFeedback extends AppCompatActivity {

    TextView rateCount, showRating;
    EditText review;
    Button submit;
    RatingBar ratingBar;
    float rateValue;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String temp;
    String rev, r_count;
    Spinner spinner1;
    String value1;
    String sp1;
    String s1;
    List<String> counsellorsNames = new ArrayList<>();
    DatabaseReference ref;
    String userName= "Unknown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        rateCount= (TextView)findViewById(R.id.rateCount);
        showRating= (TextView) findViewById(R.id.showRating);
        review= (EditText) findViewById(R.id.review);
        submit= (Button) findViewById(R.id.submitBtn);
        ratingBar= (RatingBar) findViewById(R.id.ratingBar);
        spinner1= (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, counsellorsNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        ref= FirebaseDatabase.getInstance().getReference("CounsellorsProfile");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                counsellorsNames.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                //    for (DataSnapshot ss: ds.getChildren()){
                        String text = ds.child("counsellorName").getValue(String.class);
                        counsellorsNames.add(text);

               // }
            }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                value1= adapterView.getItemAtPosition(i).toString();
                s1= value1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rateValue= ratingBar.getRating();
                if (rateValue<=1 && rateValue>0)
                    rateCount.setText("Bad "+rateValue + "/5");
                else if (rateValue<=2 && rateValue>1)
                    rateCount.setText("OK "+rateValue + "/5");
                else if (rateValue<=3 && rateValue>2)
                    rateCount.setText("Good "+rateValue + "/5");
                else if (rateValue<=4 && rateValue>3)
                    rateCount.setText("Very Good "+rateValue + "/5");
                else if (rateValue<=5 && rateValue>4)
                    rateCount.setText("Best "+rateValue + "/5");

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validSpinner()){
                    if (valid()) {
                        temp = rateCount.getText().toString();
                        if (user != null) {
                            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Feedbacks");
                            UserFeedback userFeedback = new UserFeedback(s1,userName, temp, rev);
                            ref2.child(ref.push().getKey()).setValue(userFeedback);
                            Toast.makeText(AddFeedback.this, "Feedback Submitted Successfully", Toast.LENGTH_SHORT).show();
                            showRating.setText("Your Rating: \n" +"User Name: Unknown"+ "\nCounsellor Rating: " +temp + "\nComment: " +review.getText());
                            review.setText("");
                            ratingBar.setRating(0);
                            rateCount.setText("");
                        }
                    }
                }
            }
        });
    }

    private Boolean valid() {
        boolean result = false;

        rev = review.getText().toString();
        r_count= rateCount.getText().toString();

        if (rev.isEmpty() || r_count.isEmpty()) {
            Toast.makeText(this, "Enter all the required details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }

    private Boolean validSpinner(){
        boolean result= false;
        sp1= value1;

        if(sp1.isEmpty()){
            Toast.makeText(this, "Select Counsellor Name first", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }




    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddFeedback.this, StudentHomePage.class));
        finish();
    }
}