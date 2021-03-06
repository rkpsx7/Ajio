package com.pns.ajio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.pns.ajio.databinding.ActivityNotificationBinding;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.pns.ajio.databinding.ActivityNotificationBinding binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> startActivity(new Intent(NotificationActivity.this, HomeActivity.class)));
    }

    public void onProductClick(View v) {

        startActivity(new Intent(NotificationActivity.this, ProductActivity.class));
    }
}