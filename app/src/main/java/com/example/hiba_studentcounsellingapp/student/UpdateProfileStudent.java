package com.example.hiba_studentcounsellingapp.student;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hiba_studentcounsellingapp.R;
import com.example.hiba_studentcounsellingapp.UserInformation;
import com.example.hiba_studentcounsellingapp.counsellor.CounsellorHomePage;
import com.example.hiba_studentcounsellingapp.counsellor.UpdateProfileCounsellor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfileStudent extends AppCompatActivity {

    private EditText u_gender, u_fname, u_phone, u_add;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private Button update;
    NetworkInfo nInfo;

    String us_gender, us_fname, us_phone, us_add;
    private FirebaseDatabase firebaseDatabase;
    String shop_n;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_student);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        update=(Button) findViewById(R.id.vp_update_save3);
        u_gender= (EditText) findViewById(R.id.uv_gender3);
        u_fname= (EditText) findViewById(R.id.uv_fname3);
        u_phone= (EditText) findViewById(R.id.uv_phone3);
        u_add= (EditText) findViewById(R.id.uv_add3);

        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        nInfo = cManager.getActiveNetworkInfo();

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        //get firebase user
        user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Profile");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    UserInformation userInformation= dataSnapshot.getValue(UserInformation.class);
                    u_gender.setText(userInformation.getUserGender());
                    u_fname.setText(userInformation.getUserName());
                    u_phone.setText(userInformation.getUserPhone());
                    u_add.setText(userInformation.getUserAddress());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UpdateProfileStudent.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null){
                    if (validate()) {
                        if (nInfo != null && nInfo.isConnected()) {
                            UserInformation userInformation = new UserInformation(us_fname, us_phone, us_add,us_gender);
                            ref.setValue(userInformation);
                            Toast.makeText(UpdateProfileStudent.this, "Profile is Updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateProfileStudent.this, StudentHomePage.class));
                            finish();

                        }
                    } else {
                        Toast.makeText(UpdateProfileStudent.this, "Network is not available", Toast.LENGTH_LONG).show();
                    }
                }//user null
            }
        });
    }

    private Boolean validate(){
        boolean result= false;

        us_gender = u_gender.getText().toString();
        us_fname = u_fname.getText().toString();
        us_phone=u_phone.getText().toString();
        us_add= u_add.getText().toString();
        if(us_gender.isEmpty() ||  us_fname.isEmpty() || us_phone.isEmpty() || us_add.isEmpty()){
            Toast.makeText(this, "Fill every required information", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UpdateProfileStudent.this, StudentHomePage.class));
        finish();
    }
}