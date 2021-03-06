package com.pns.ajio.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pns.ajio.R;
import com.pns.ajio.adapter.ProductAdapter;
import com.pns.ajio.databinding.ActivityBagBinding;
import com.pns.ajio.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class BagActivity extends AppCompatActivity {

    private static final int SIGN_IN_KEY = 1;
    private FirebaseAuth mAuth;
    private ActivityBagBinding mBinding;
    private List<ProductModel> mList;
    private ProductAdapter mAdapter;
    private final String uid = FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag);

        mBinding = ActivityBagBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initDataAndViews();

        mBinding.imgCross.setOnClickListener(v -> finish());
        mBinding.btnContinueShopping.setOnClickListener(v -> finish());
        mBinding.btnLoginJoin.setOnClickListener(v -> signInWithGoogle());
        mBinding.ivFavourite.setOnClickListener(v -> startActivity(new Intent(BagActivity.this, WishlistActivity.class)));
    }

    private void initDataAndViews() {

        mAuth = FirebaseAuth.getInstance();

        mList = new ArrayList<>();
        mAdapter = new ProductAdapter(mList, this);
        mBinding.recyclerView.setAdapter(mAdapter);

        checkLoginState();
        prepareList();

        mBinding.imgCross.setOnClickListener(v -> finish());
        mBinding.imgContact.setOnClickListener(v -> contact());
        mBinding.imgDev.setOnClickListener(v -> contactDev());
        mBinding.btnContinueShopping.setOnClickListener(v -> finish());
        mBinding.btnLoginJoin.setOnClickListener(v -> signInWithGoogle());
        mBinding.ivFavourite.setOnClickListener(v -> startActivity(new Intent(BagActivity.this, WishlistActivity.class)));
    }

    private void contactDev() {

        FirebaseDatabase.getInstance().getReference("Mail")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    String mail = "mailto:" + snapshot.getValue(String.class);
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse(mail));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Regarding : AJIO");
                    startActivity(Intent.createChooser(intent, "Send Using"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void contact() {

        FirebaseDatabase.getInstance().getReference("Contact")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    String phone = "+91" + snapshot.getValue(String.class);
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(Intent.createChooser(intent, "Call Using"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void prepareList() {

        // Fetching orders from Firebase

        FirebaseDatabase.getInstance().getReference("ProductDetails")
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {

                            mList.clear();

                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                ProductModel model = snapshot1.getValue(ProductModel.class);

                                // Adding data in the list after getting from Firebase

                                if (model != null && model.getOrdered().contains(uid)) {

                                    mList.add(model);

                                    mBinding.recyclerView.setVisibility(View.VISIBLE);
                                    mBinding.rlEmptyBagInfo.setVisibility(View.GONE);
                                    mBinding.tvBagIsEmpty.setVisibility(View.GONE);
                                }
                            }

                            mAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(BagActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signInWithGoogle() {

        // Initializing Google sign In Options

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, gso);

        Intent signInIntent = signInClient.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_IN_KEY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Fetching request code for sign in

        if (requestCode == SIGN_IN_KEY) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);

                assert account != null;

                firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {

                Toast.makeText(this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {

        // Generating credential and token

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {

                    if (task.isSuccessful()) {

                        Toast.makeText(BagActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                        saveLoginState();

                    } else {

                        Toast.makeText(BagActivity.this, task.getException() + "", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveLoginState() {

        SharedPreferences.Editor preferences = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
        preferences.putBoolean("loggedIn", true);
        preferences.apply();

        checkLoginState();
    }

    private void checkLoginState() {

        // Checking login state

        SharedPreferences preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
        boolean loggedInAlready = preferences.getBoolean("loggedIn", false);

        if (loggedInAlready) {

            mBinding.btnLoginJoin.setVisibility(View.GONE);
            mBinding.tvEmptyDescription.setVisibility(View.GONE);

        } else {

            mBinding.btnLoginJoin.setVisibility(View.VISIBLE);
            mBinding.tvEmptyDescription.setVisibility(View.VISIBLE);
        }
    }

    public void onProductClick(View view) {
        startActivity(new Intent(BagActivity.this, HomeActivity.class));
        finish();
    }
}