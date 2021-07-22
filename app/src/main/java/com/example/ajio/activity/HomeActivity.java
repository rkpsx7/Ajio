package com.example.ajio.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ajio.R;
import com.example.ajio.databinding.ActivityHomeBinding;
import com.example.ajio.fragment.BottomSheetFragment;
import com.example.ajio.fragment.HomeFragment;
import com.example.ajio.fragment.StoresFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    ActivityHomeBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.bottomMenu.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

        mBinding.imgHamburger.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, NavigationActivity.class)));
        mBinding.imgNotification.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, NotificationActivity.class)));
        mBinding.imgDrop.setOnClickListener(v -> openBottomSheetDialog());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bottom, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();
                break;

            case R.id.menu_stores:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new StoresFragment())
                        .commit();
                break;

            case R.id.menu_account:
                startActivity(new Intent(HomeActivity.this, AccountActivity.class));
                break;

            case R.id.menu_wishlist:

                SharedPreferences preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
                boolean loggedInAlready = preferences.getBoolean("loggedIn", false);

                if (loggedInAlready) {
                    startActivity(new Intent(HomeActivity.this, WishlistActivity.class));
                } else {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }

                break;

            case R.id.menu_bag:
                startActivity(new Intent(HomeActivity.this, BagActivity.class));
                break;

            default:
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    private void openBottomSheetDialog() {

        BottomSheetFragment fragment = new BottomSheetFragment();
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }

    public void onProductClick(View v) {

        startActivity(new Intent(HomeActivity.this, ProductActivity.class));
    }
}