package com.pns.ajio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pns.ajio.adapter.ProductAdapter;
import com.pns.ajio.databinding.ActivityProductBinding;
import com.pns.ajio.model.ProductModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding mBinding;
    private ProductAdapter mAdapter;
    private List<ProductModel> mList;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Checkout.preload(getApplicationContext());

        initializeData();
        addData();
    }

    private void initializeData() {

        mList = new ArrayList<>();
        mAdapter = new ProductAdapter(mList, this);
        mBinding.recyclerView.setAdapter(mAdapter);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("ProductDetails");

        mBinding.imgBag.setOnClickListener(v -> startActivity(new Intent(ProductActivity.this, BagActivity.class)));
        mBinding.imgFavourite.setOnClickListener(v -> {
            startActivity(new Intent(ProductActivity.this, WishlistActivity.class));
            finish();
        });
        mBinding.imgSearch.setOnClickListener(v -> startActivity(new Intent(ProductActivity.this, HomeActivity.class)));
        mBinding.imgHamburger.setOnClickListener(v -> startActivity(new Intent(ProductActivity.this, NavigationActivity.class)));
    }

    private void addData() {

        // Retrieving product details from firebase

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    mList.clear();

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        ProductModel model = snapshot1.getValue(ProductModel.class);

                        mList.add(model);
                    }

                    adjustView();
                    mAdapter.notifyDataSetChanged();

                } else {

                    adjustView();

                    Toast.makeText(ProductActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                adjustView();

                Toast.makeText(ProductActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adjustView() {

        mBinding.shimmerLayout.setVisibility(View.GONE);
        mBinding.tvPrice.setVisibility(View.VISIBLE);
        mBinding.tvProducts.setVisibility(View.VISIBLE);
        mBinding.recyclerView.setVisibility(View.VISIBLE);
        mBinding.bottomLayout.setVisibility(View.VISIBLE);
    }

}