package com.mad.android.parkingtracker.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
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

    //No permission required
    private void makeCall(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:6476857191"));
        startActivity(intent);
    }

    //No permission required
    private void sendSMS(){
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:6477742880"));
        sendIntent.putExtra("sms_body", "Hey, we need help");
        startActivity(sendIntent);
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