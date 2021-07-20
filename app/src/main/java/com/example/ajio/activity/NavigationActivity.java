package com.example.ajio.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ajio.R;
import com.example.ajio.databinding.ActivityNavigationBinding;

public class NavigationActivity extends AppCompatActivity {

    private ActivityNavigationBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }
}