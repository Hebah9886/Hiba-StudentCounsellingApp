package com.example.hiba_studentcounsellingapp.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class StudentHomePage extends AppCompatActivity {
    Button add_appointment, view_appointment, view_profile, delete_account, add_feedback, logout;
    TextView userNameGet;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        //to access header to display user name
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.name);
        //dislay name end of header

        add_appointment = (Button) findViewById(R.id.add_appointment);
        view_appointment = (Button) findViewById(R.id.view_appointment);
        view_profile = (Button) findViewById(R.id.view_profile);
        delete_account = (Button) findViewById(R.id.delete_account);
        add_feedback = (Button) findViewById(R.id.add_feedback);
        logout = (Button) findViewById(R.id.logout);
        userNameGet = (TextView) findViewById(R.id.user_nameGet);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Profile");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                userNameGet.setText("Welcome: "+userInformation.getUserName());
                navUsername.setText(userInformation.getUserName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(StudentHomePage.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.home_menu) {
                    startActivity(new Intent(StudentHomePage.this, StudentHomePage.class));
                    finish();

                } else if (id == R.id.add_by_name_menu) {
                    startActivity(new Intent(StudentHomePage.this, SearchCounsellorUsingName.class));
                    finish();

                } else if (id == R.id.add_by_exp_menu) {
                    startActivity(new Intent(StudentHomePage.this, SearchCounsellorUsingExpertise.class));
                    finish();

                } else if (id == R.id.add_ap_menu) {
                    startActivity(new Intent(StudentHomePage.this, AddAppointment.class));
                    finish();

                } else if (id == R.id.add_feedback_menu) {
                    startActivity(new Intent(StudentHomePage.this, AddFeedback.class));
                    finish();

                } else if (id == R.id.update_ap_menu) {
                    startActivity(new Intent(StudentHomePage.this, StudentViewAppointment.class));
                    finish();

                } else if (id == R.id.delete_ap_menu) {
                    startActivity(new Intent(StudentHomePage.this, StudentViewAppointment.class));
                    finish();

                } else if (id == R.id.delete_menu) {
                    startActivity(new Intent(StudentHomePage.this, DeleteAccountStduent.class));
                    finish();

                } else if (id == R.id.view_menu) {
                    startActivity(new Intent(StudentHomePage.this, ViewProfileStudent.class));
                    finish();

                } else if (id == R.id.edit_menu) {
                    startActivity(new Intent(StudentHomePage.this, UpdateProfileStudent.class));
                    finish();

                } else if (id == R.id.logout_menu) {
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    firebaseAuth.signOut();
                    finish();
                    Toast.makeText(StudentHomePage.this, "Account Logout", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StudentHomePage.this, UserType.class));
                }
                return false;
            }
        });


        add_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentHomePage.this, AddAppointment.class));
                finish();
            }
        });

        view_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentHomePage.this, StudentViewAppointment.class));
                finish();
            }
        });

        view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentHomePage.this, ViewProfileStudent.class));
                finish();
            }
        });

        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentHomePage.this, DeleteAccountStduent.class));
                finish();
            }
        });

        add_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentHomePage.this, AddFeedback.class));
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                firebaseAuth.signOut();
                finish();
                Toast.makeText(StudentHomePage.this, "Account Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(StudentHomePage.this, UserType.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        backButtonHandler();
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                StudentHomePage.this);
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