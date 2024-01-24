package com.example.hiba_studentcounsellingapp.counsellor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hiba_studentcounsellingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterCounsellor extends AppCompatActivity {
    EditText name, experience, qualification, expertise;
    Button upload;
    private DatabaseReference ref, ref1;
    private String u_experience, u_name, u_qualification, u_expertise;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_counsellor);

        upload=(Button) findViewById(R.id.upload);

        experience= (EditText) findViewById(R.id.coun_experience);
        name= (EditText) findViewById(R.id.coun_name);
        qualification= (EditText) findViewById(R.id.coun_qualification);
        expertise= (EditText) findViewById(R.id.coun_expertise);

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    ref= FirebaseDatabase.getInstance().getReference("Counsellors").child(user.getUid()).child("All");
                    ref1= FirebaseDatabase.getInstance().getReference("CounsellorsProfile");
                    UserCounsellor userCounsellor= new UserCounsellor(u_name, u_qualification, u_experience, u_expertise);
                    ref.setValue(userCounsellor);
                    ref1.child(ref.push().getKey()).setValue(userCounsellor);

                    Toast.makeText(RegisterCounsellor.this, "Counsellor Information Saved", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterCounsellor.this, CounsellorHomePage.class));
                    finish();
                }
            }
        });

    }

    private Boolean validate(){
        boolean result= false;

        u_experience= experience.getText().toString();
        u_expertise= expertise.getText().toString();
        u_name= name.getText().toString();
        u_qualification= qualification.getText().toString();

        if(u_expertise.isEmpty() || u_experience.isEmpty() || u_name.isEmpty() || u_qualification.isEmpty() ){
            Toast.makeText(RegisterCounsellor.this, "First add all details of Service", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterCounsellor.this, CounsellorHomePage.class));
        finish();
    }
}