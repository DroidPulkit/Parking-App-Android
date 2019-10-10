package com.mad.android.parkingtracker.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.android.parkingtracker.Model.Receipt;
import com.mad.android.parkingtracker.R;

public class ParkingReceiptActivity extends AppCompatActivity {
    private TextView txtViewEmail, txtViewCarPlate, txtViewCompany, txtViewColor, txtViewHours, txtViewDateTime, txtViewLot, txtViewSpot, txtViewPayment, txtViewAmount;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_receipt);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtViewEmail = findViewById(R.id.txtVEmail);
        txtViewCarPlate = findViewById(R.id.textViewCarPlate);
        txtViewCompany = findViewById(R.id.textViewCompany);
        txtViewColor = findViewById(R.id.textViewColor);
        txtViewHours = findViewById(R.id.textViewHours);
        txtViewDateTime = findViewById(R.id.textViewDate);
        txtViewLot = findViewById(R.id.textViewLot);
        txtViewSpot = findViewById(R.id.textViewSpot);
        txtViewPayment = findViewById(R.id.textViewPaymentMode);
        txtViewAmount = findViewById(R.id.textViewAmount);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userId = user.getUid();
        Log.d("ProfileActivity", "userID: " + userId);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("parkingReceipt").child(userId);

        myRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Receipt receipt = child.getValue(Receipt.class);
                    txtViewEmail.setText(receipt.getEmail());
                    txtViewCarPlate.setText(receipt.getCarNo());
                    txtViewCompany.setText(receipt.getCarCompany());
                    txtViewColor.setText(receipt.getCarColor());
                    txtViewHours.setText(receipt.getNoOfHours());
                    txtViewDateTime.setText(receipt.getDateTime());
                    txtViewLot.setText(receipt.getLotNo());
                    txtViewSpot.setText(receipt.getSpotNo());
                    txtViewPayment.setText(receipt.getPaymentMethod());
                    txtViewAmount.setText(receipt.getPaymentAmount());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
