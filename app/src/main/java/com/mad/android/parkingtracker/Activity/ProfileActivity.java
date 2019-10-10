package com.mad.android.parkingtracker.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView name, email, contactNumber, carNumber, city;
    private Button edit;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        name = findViewById(R.id.userProfileTvNameData);
        email = findViewById(R.id.userProfileTvEmailData);
        contactNumber = findViewById(R.id.userProfileTvContactNumberData);
        carNumber = findViewById(R.id.userProfileTvCarNumberData);
        city = findViewById(R.id.userProfileTvCityData);
        edit = findViewById(R.id.profileBtnEdit);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userId = user.getUid();
        Log.d("ProfileActivity", "userID: " + userId);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User").child(userId);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                Log.d("ProfileActivity", user1.getEmail());

                name.setText(user1.getName());
                email.setText(user1.getEmail());
                contactNumber.setText(user1.getNumber());
                carNumber.setText(user1.getCar_number());
                city.setText(user1.getCity());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        edit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profileBtnEdit:
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("email", email.getText().toString());
                intent.putExtra("contactNumber", contactNumber.getText().toString());
                intent.putExtra("carNumber", carNumber.getText().toString());
                intent.putExtra("city", city.getText().toString());
                startActivity(intent);
                break;
        }
    }
}
