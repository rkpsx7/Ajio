package com.pns.ajio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pns.ajio.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {

    private ActivitySplashScreenBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        checkStatus();
    }

    private void checkStatus() {

        FirebaseDatabase.getInstance().getReference("Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    try {
                        if (snapshot.getValue(Boolean.class)) {
                            openHomeActivity();
                        } else {
                            mBinding.ins.setVisibility(View.VISIBLE);
                        }
                    } catch (NullPointerException e){
                        openHomeActivity();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openHomeActivity() {

        // Switching to Home screen after 2 seconds

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
            finish();
        }, 2000);
    }
}