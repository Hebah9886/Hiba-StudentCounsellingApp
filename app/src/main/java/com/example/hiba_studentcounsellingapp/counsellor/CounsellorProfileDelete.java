package com.example.hiba_studentcounsellingapp.counsellor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class CounsellorProfileDelete extends AppCompatActivity {
    TextView name, qualification, experience, expertise;
    Button update, delete;

    DatabaseReference ref, ref1, ref2;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String name1, qualification1, experience1, expertise1;
    String name2, qualification2, experience2, expertise2;
    String key;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_profile_delete);

        name= findViewById(R.id.cn_name_p1);
        qualification= findViewById(R.id.cn_qualification_p1);
        experience= findViewById(R.id.cn_experience_p1);
        expertise= findViewById(R.id.cn_expertise_p1);
        delete= findViewById(R.id.btn_c_del_p1);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        ref= FirebaseDatabase.getInstance().getReference("Counsellors").child(user.getUid()).child("All");
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


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref1= FirebaseDatabase.getInstance().getReference("CounsellorsProfile");
                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                        UserCounsellor userCounsellor = ds.getValue(UserCounsellor.class);
                        name1 = userCounsellor.getCounsellorName();
                        qualification1 = userCounsellor.getCounsellorQualification();
                        experience1 = userCounsellor.getCounsellorExperience();
                        expertise1 = userCounsellor.getCounsellorExpertise();


                        name2 = name.getText().toString();
                        qualification2 = qualification.getText().toString();
                        expertise2 = expertise.getText().toString();
                        experience2 = experience.getText().toString();

                        if (name1.equals(name2) && qualification1.equals(qualification2) && experience1.equals(experience2) && expertise1.equals(expertise2)) {
                            key = ds.getKey();
                            ref2 = FirebaseDatabase.getInstance().getReference("CounsellorsProfile").child(key);
                            deleteCounsellorProfile();
                        }
                    }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


    }

    public void deleteCounsellorProfile() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                CounsellorProfileDelete.this);
        // Setting Dialog Title
        alertDialog.setTitle("Delete Counsellor Profile");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to Delete Counsellor Profile?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.logo);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ref.getRef().removeValue();
                        ref2.getRef().removeValue();
                        startActivity(new Intent(CounsellorProfileDelete.this, CounsellorHomePage.class));
                        Toast.makeText(CounsellorProfileDelete.this, "Profile is Deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(CounsellorProfileDelete.this, CounsellorProfile.class));
        finish();
    }
}