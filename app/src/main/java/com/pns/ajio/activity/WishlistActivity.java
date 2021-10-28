package com.pns.ajio.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pns.ajio.adapter.ProductAdapter;
import com.pns.ajio.databinding.ActivityWishlistBinding;
import com.pns.ajio.model.ProductModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends AppCompatActivity {

    private List<ProductModel> mList;
    private ProductAdapter mAdapter;
    private ActivityWishlistBinding mBinding;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityWishlistBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.btnContinueShopping.setOnClickListener(v -> finish());

        initDataAndView();
        fetchData();
    }

    private void initDataAndView() {

        mList = new ArrayList<>();
        mAdapter = new ProductAdapter(mList, this);
        mBinding.recyclerView.setAdapter(mAdapter);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("ProductDetails");
    }

    private void fetchData() {

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    mList.clear();

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        ProductModel model = snapshot1.getValue(ProductModel.class);

                        // Adding only those products which has been ordered

                        if (model != null && model.isWishlisted()) {

                            mList.add(model);

                            mBinding.layoutRelative.setVisibility(View.GONE);
                            mBinding.recyclerView.setVisibility(View.VISIBLE);
                        }
                    }

                    mAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(WishlistActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}