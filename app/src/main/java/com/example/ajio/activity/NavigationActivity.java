package com.example.ajio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ajio.databinding.ActivityNavigationBinding;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.ajio.databinding.ActivityNavigationBinding binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ibBack.setOnClickListener(v -> startActivity(new Intent(NavigationActivity.this, HomeActivity.class)));
        binding.ibHome.setOnClickListener(v -> startActivity(new Intent(NavigationActivity.this, HomeActivity.class)));
        binding.imgAjioLuxe.setOnClickListener(v -> startActivity(new Intent(NavigationActivity.this, HomeActivity.class)));
        binding.ibSearch.setOnClickListener(v -> startActivity(new Intent(NavigationActivity.this, HomeActivity.class)));
    }

    public void onProductClick(View v) {

        startActivity(new Intent(NavigationActivity.this, ProductActivity.class));
    }
}