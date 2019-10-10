package com.mad.android.parkingtracker.Activity;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnNewRegister;
    private EditText name, email, password, phoneNumber, carNumber;
    private Spinner city;
    private String strName, strEmail, strPassword, strPhoneNumber, strCarNumber, strCity;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnNewRegister = findViewById(R.id.registerBtnRegister);
        name = findViewById(R.id.registerEtName);
        email = findViewById(R.id.registerEtEmail);
        password = findViewById(R.id.registerEtPassword);
        phoneNumber = findViewById(R.id.registerEtPhone);
        carNumber = findViewById(R.id.registerEtCatPlateNumber);
        city = findViewById(R.id.registerSpinnerCity);

        mAuth = FirebaseAuth.getInstance();

        btnNewRegister.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerBtnRegister:
                extractData();
                if (verifyData()){
                    mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String userId = user.getUid();

                                        DateFormat dateFormatDate = new SimpleDateFormat("dd-MM-yyyy", Locale.CANADA);
                                        DateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss", Locale.CANADA);
                                        Calendar cal = Calendar.getInstance();
                                        String date = dateFormatDate.format(cal.getTime());
                                        String time = dateFormatTime.format(cal.getTime());


                                        // Write a message to the database
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference();
                                        HashMap<String, String> map = new HashMap<>();
                                        map.put("car_number", strCarNumber);
                                        map.put("city", strCity);
                                        map.put("email", strEmail);
                                        map.put("lastLoginDate", date);
                                        map.put("lastLoginTime", time);
                                        map.put("name", strName);
                                        map.put("number", strPhoneNumber);
                                        myRef.child("User").child(userId).setValue(map);
                                        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                        startActivity(intent);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                } else {
                    Toast.makeText(this, "Error in the form", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    void extractData(){
        strName = name.getText().toString();
        strEmail = email.getText().toString();
        strPassword = password.getText().toString();
        strPhoneNumber = phoneNumber.getText().toString();
        strCarNumber = carNumber.getText().toString();
        strCity = city.getSelectedItem().toString();
    }

    boolean verifyData(){
        boolean isDataTrue = true;

        if (strName.isEmpty() || strEmail.isEmpty() || strPassword.isEmpty() || strPhoneNumber.isEmpty() || strCarNumber.isEmpty() || strCity.isEmpty()){
            isDataTrue = false;
        }

        return isDataTrue;
    }

}
