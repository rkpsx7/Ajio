package com.example.ajio.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ajio.R;
import com.example.ajio.databinding.ActivityNavigationBinding;

public class NavigationActivity extends AppCompatActivity {

    private ActivityNavigationBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.ibBack.setOnClickListener(v -> startActivity(new Intent(NavigationActivity.this, HomeActivity.class)));
        mBinding.ibHome.setOnClickListener(v -> startActivity(new Intent(NavigationActivity.this, HomeActivity.class)));
    }
}