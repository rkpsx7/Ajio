package com.example.ajio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ajio.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        openHomeActivity();
    }

    private void openHomeActivity() {

        // Switching to Home screen after 2 seconds

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
            finish();
        }, 2000);
    }
}