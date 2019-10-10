package com.mad.android.parkingtracker.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.android.parkingtracker.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class GoogleSignInExtraActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText phone, carPlate;
    private Spinner city;

    private Button submit;

    private String strPhone, strCarPlate, strCity;

    private FirebaseAuth mAuth;

    private String name = "", email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in_extra);

        phone = findViewById(R.id.registerExtraEtPhone);
        carPlate = findViewById(R.id.registerExtraEtCatPlateNumber);
        city = findViewById(R.id.registerExtraSpinnerCity);
        submit = findViewById(R.id.registerExtraBtnRegister);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");

        submit.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerExtraBtnRegister:
                extractData();
                if (veriftData()){
                    //Data is valid

                    FirebaseUser user = mAuth.getCurrentUser();
                    String userId = user.getUid();

                    DateFormat dateFormatDate = new SimpleDateFormat("dd-MM-yyyy", Locale.CANADA);
                    DateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss", Locale.CANADA);
                    Calendar cal = Calendar.getInstance();
                    String date = dateFormatDate.format(cal.getTime());
                    String time = dateFormatTime.format(cal.getTime());

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("car_number", strCarPlate);
                    map.put("city", strCity);
                    map.put("email", email);
                    map.put("lastLoginDate", date);
                    map.put("lastLoginTime", time);
                    map.put("name", name);
                    map.put("number", strPhone);
                    myRef.child("User").child(userId).setValue(map);
                    Toast.makeText(GoogleSignInExtraActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GoogleSignInExtraActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                }
                break;
        }
    }

    void extractData(){
        strPhone = phone.getText().toString();
        strCarPlate = carPlate.getText().toString();
        strCity = city.getSelectedItem().toString();
    }

    boolean veriftData(){
        boolean isDataTrue = true;

        if (strCity.isEmpty() || strCarPlate.isEmpty() || strPhone.isEmpty()){
            isDataTrue = false;
        }

        return isDataTrue;
    }
}
