package com.mad.android.parkingtracker.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.android.parkingtracker.Model.Receipt;
import com.mad.android.parkingtracker.Adapter.ParkingReportAdapter;
import com.mad.android.parkingtracker.R;

import java.util.ArrayList;

public class ParkingReportActivity extends AppCompatActivity implements ParkingReportAdapter.ListItemClickListener {

    RecyclerView parkingReport;
    private FirebaseAuth mAuth;
    private ArrayList<Receipt> listOfReceipts = new ArrayList<Receipt>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_report);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        parkingReport = findViewById(R.id.parkingReportRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        parkingReport.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecor.setDrawable(this.getResources().getDrawable(R.drawable.line_divider));
        parkingReport.addItemDecoration(itemDecor);

        ParkingReportAdapter adapter = new ParkingReportAdapter(listOfReceipts, this);
        parkingReport.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userId = user.getUid();
        Log.d("ProfileActivity", "userID: " + userId);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("parkingReceipt").child(userId);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Receipt receipt = child.getValue(Receipt.class);
                    listOfReceipts.add(receipt);
                }
                updateDisplay();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void updateDisplay() {
        if (!listOfReceipts.isEmpty()){
            ParkingReportAdapter adapter = new ParkingReportAdapter(listOfReceipts, this);
            adapter.notifyDataSetChanged();
            parkingReport.setAdapter(adapter);

        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Receipt receipt = listOfReceipts.get(clickedItemIndex);
        Intent intent = new Intent(ParkingReportActivity.this, ParkingReportDetailActivity.class);
        intent.putExtra("receipt", receipt);
        startActivity(intent);
    }
}
