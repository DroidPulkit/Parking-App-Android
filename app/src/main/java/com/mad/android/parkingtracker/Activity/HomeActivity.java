package com.mad.android.parkingtracker.Activity;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.android.parkingtracker.Model.User;
import com.mad.android.parkingtracker.R;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;

    private TextView drawerUsername, drawerAccount;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentIntent = new Intent(HomeActivity.this, ParkingActivity.class);
                startActivity(paymentIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        drawerUsername = headerView.findViewById(R.id.drawer_name);
        drawerAccount = headerView.findViewById(R.id.drawer_email);

        navigationView.setNavigationItemSelectedListener(this);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        final String userId = user.getUid();

        DatabaseReference myRef = database.getReference("User").child(userId);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                drawerUsername.setText(user1.getName());
                drawerAccount.setText(user1.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_payment_receipt) {
            Intent paymentIntent = new Intent(HomeActivity.this, ParkingActivity.class);
            startActivity(paymentIntent);

        } else if (id == R.id.nav_location) {
            Intent locationIntent = new Intent(HomeActivity.this, LocationActivity.class);
            startActivity(locationIntent);

        } else if (id == R.id.nav_parking_manual) {
            Intent manualIntent = new Intent(HomeActivity.this, ManualActivity.class);
            startActivity(manualIntent);

        } else if (id == R.id.nav_report) {

            Intent paymentReportIntent = new Intent(HomeActivity.this, ParkingReportActivity.class);
            startActivity(paymentReportIntent);

        } else if (id == R.id.nav_profile) {

            Intent userProfileIntent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(userProfileIntent);

        } else if (id == R.id.nav_support_contact) {
            Intent supportIntent = new Intent(HomeActivity.this, SupportActivity.class);
            startActivity(supportIntent);
        } else if (id == R.id.nav_logout) {
            mAuth.signOut();
            Intent intent = new Intent(HomeActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
