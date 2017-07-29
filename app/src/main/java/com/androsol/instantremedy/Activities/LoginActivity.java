package com.androsol.instantremedy.Activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.androsol.instantremedy.R;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new CountDownTimer(3000, 3000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                LoginActivity.this.setContentView(R.layout.activity_main);
                setContentView(R.layout.activity_login);
                runCode();
            }
        }.start();
        }
    public void runCode(){

    }
    }