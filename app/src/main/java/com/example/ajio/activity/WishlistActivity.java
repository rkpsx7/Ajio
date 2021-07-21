package com.example.ajio.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ajio.databinding.ActivityWishlistBinding;

public class WishlistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityWishlistBinding binding = ActivityWishlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnContinueShopping.setOnClickListener(v -> finish());
    }
}