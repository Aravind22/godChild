package com.godapp.godapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.godapp.godapp.R;

public class RingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);

        Button dismissBtn = (Button) findViewById(R.id.dismissAlarm);

        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationUtils notificationUtils = new NotificationUtils(RingActivity.this);
                boolean flag = notificationUtils.stopAlarm();
                if(flag){
                    finishAndRemoveTask();
                }
            }
        });
    }
}