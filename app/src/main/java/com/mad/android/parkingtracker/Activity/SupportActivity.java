package com.mad.android.parkingtracker.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mad.android.parkingtracker.R;

public class SupportActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnCall;
    Button btnSMS;
    Button btnEmail;
    ImageView imgCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imgCall = (ImageView) findViewById(R.id.imgCall);
        imgCall.setOnClickListener(this);

        btnCall = (Button) findViewById(R.id.btnCall);
        btnCall.setOnClickListener(this);

        btnSMS = (Button) findViewById(R.id.btnSMS);
        btnSMS.setOnClickListener(this);

        btnEmail = (Button) findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCall:
                makeCall();
                break;
            case R.id.btnEmail:
                sendEmail();
                break;
            case R.id.btnSMS:
                sendSMS();
                break;

        }
    }

    private void makeCall(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:6476857191"));

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(), "Call permission denied",Toast.LENGTH_LONG).show();
            return;
        }
        startActivity(callIntent);

    }

    private void sendSMS(){
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:6477742880"));
        smsIntent.putExtra("sms body", "Test message");

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(), "SMS permission denied", Toast.LENGTH_LONG).show();
            return;
        }
        startActivity(smsIntent);
    }

    private void sendEmail(){
        Intent emailIntent= new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@parkme.com"});

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Help us");

        emailIntent.putExtra( Intent.EXTRA_TEXT, "Please help us!!");

        emailIntent.setType("message/rfc822");

        startActivity(Intent.createChooser(emailIntent, "support@parkme.ca"));


    }
}