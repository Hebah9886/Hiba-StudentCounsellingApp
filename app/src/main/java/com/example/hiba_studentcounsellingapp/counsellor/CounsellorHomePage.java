package com.example.hiba_studentcounsellingapp.counsellor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hiba_studentcounsellingapp.R;
import com.example.hiba_studentcounsellingapp.UserInformation;
import com.example.hiba_studentcounsellingapp.UserType;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CounsellorHomePage extends AppCompatActivity {
    TextView user_nameGet_c;
    Button add_counsellor, view_appointment, counsellor_profile, view_profile, delete_account, logout, view_feedback;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;

    public static final String SHARED_PREFS= "sharedPrefs";
    FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_home_page);

        drawerLayout= findViewById(R.id.drawerlayout1);
        navigationView= findViewById(R.id.navigationview1);
        toolbar= findViewById(R.id.toolbar1);

        setSupportActionBar(toolbar);
        firebaseAuth= FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        user_nameGet_c= findViewById(R.id.user_nameGet_c);
        view_appointment= findViewById(R.id.view_appointment_counsellor);
        counsellor_profile= findViewById(R.id.view_counsellor_profile);
        view_feedback= findViewById(R.id.view_feedback_c);
        add_counsellor= findViewById(R.id.add_counsellor);
        delete_account= findViewById(R.id.delete_account_c);
        view_profile= findViewById(R.id.view_profile_c);
        logout= findViewById(R.id.logout_c);


        //to access header to display user name
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.m_name);
        //dislay name end of header


        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Profile");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                navUsername.setText(userInformation.getUserName());
                user_nameGet_c.setText("Welcome: "+userInformation.getUserName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CounsellorHomePage.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open, R.string.navigation_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();

                if (id == R.id.home_menu1) {
                    startActivity(new Intent(CounsellorHomePage.this, CounsellorHomePage.class));
                    finish();

                } else if (id == R.id.m_view_ap_menu) {
                    startActivity(new Intent(CounsellorHomePage.this, ViewAppointmentCounsellor.class));
                    finish();

                } else if (id == R.id.m_view_feed_menu) {
                    startActivity(new Intent(CounsellorHomePage.this, ViewFeedback.class));
                    finish();

                } else if (id == R.id.add_coun_menu) {
                    DatabaseReference ref1= FirebaseDatabase.getInstance().getReference("Counsellors").child(user.getUid()).child("All");
                    ref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                Toast.makeText(CounsellorHomePage.this, "You already have a Counsellor Account", Toast.LENGTH_SHORT).show();
                            }
                            if (!snapshot.exists()){
                                startActivity(new Intent(CounsellorHomePage.this, RegisterCounsellor.class));
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else if (id == R.id.delete_coun_menu) {
                    startActivity(new Intent(CounsellorHomePage.this, CounsellorProfileDelete.class));
                    finish();

                } else if (id == R.id.m_delete_menu) {
                    startActivity(new Intent(CounsellorHomePage.this, DeleteAccountCounsellor.class));
                    finish();

                } else if (id == R.id.m_view_menu) {
                    startActivity(new Intent(CounsellorHomePage.this, ViewProfileCounsellor.class));
                    finish();

                } else if (id == R.id.m_edit_menu) {
                    startActivity(new Intent(CounsellorHomePage.this, UpdateProfileCounsellor.class));
                    finish();

                }  else if (id == R.id.m_logout_menu) {
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    firebaseAuth.signOut();
                    finish();
                    Toast.makeText(CounsellorHomePage.this, "Account Logout", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CounsellorHomePage.this, UserType.class));
                }
                return false;
            }
        });


        view_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CounsellorHomePage.this, ViewAppointmentCounsellor.class));
                finish();
            }
        });

        counsellor_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CounsellorHomePage.this, CounsellorProfile.class));
                finish();
            }
        });

        view_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CounsellorHomePage.this, ViewFeedback.class));
                finish();
            }
        });

        add_counsellor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref1= FirebaseDatabase.getInstance().getReference("Counsellors").child(user.getUid()).child("All");
                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Toast.makeText(CounsellorHomePage.this, "You already have a Counsellor Account", Toast.LENGTH_SHORT).show();
                        }
                        if (!snapshot.exists()){
                            startActivity(new Intent(CounsellorHomePage.this, RegisterCounsellor.class));
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CounsellorHomePage.this, DeleteAccountCounsellor.class));
                finish();
            }
        });

        view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CounsellorHomePage.this, ViewProfileCounsellor.class));
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.clear();
                editor.commit();
                firebaseAuth.signOut();
                finish();
                Toast.makeText(CounsellorHomePage.this, "Account Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CounsellorHomePage.this, UserType.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        backButtonHandler();
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                CounsellorHomePage.this);
        // Setting Dialog Title
        alertDialog.setTitle("Leave application?");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to leave the application?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.logo);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Exit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
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
}