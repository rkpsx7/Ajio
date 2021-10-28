package com.pns.ajio.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pns.ajio.R;
import com.pns.ajio.databinding.ActivityAccountBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class AccountActivity extends AppCompatActivity {

    private static final int SIGN_IN_KEY = 1;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ActivityAccountBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        checkLoginState(mUser);

        mBinding.btnLogin.setOnClickListener(v -> signInWithGoogle());
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

                        Toast.makeText(AccountActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        mUser = mAuth.getCurrentUser();

                        assert mUser != null;

                        // Saving state of user login

                        SharedPreferences.Editor preferences = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                        preferences.putBoolean("loggedIn", true);
                        preferences.apply();

                        finish();

                    } else {

                        Toast.makeText(AccountActivity.this, task.getException() + "", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void checkLoginState(FirebaseUser user) {

        if (user == null) return;

        SharedPreferences preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
        boolean loggedInAlready = preferences.getBoolean("loggedIn", false);

        if (loggedInAlready) {

            mBinding.btnLogin.setVisibility(View.GONE);
            mBinding.imgIcon.setImageDrawable(getResources().getDrawable(R.drawable.ajio));
            mBinding.tvEdit.setVisibility(View.VISIBLE);
            mBinding.tvName.setVisibility(View.VISIBLE);
            mBinding.tvEmail.setVisibility(View.VISIBLE);
            mBinding.tvPhone.setVisibility(View.VISIBLE);

            mBinding.tvName.setText(user.getDisplayName());
            mBinding.tvEmail.setText(user.getEmail());

            if (Objects.requireNonNull(user.getPhoneNumber()).length() < 10) {
                mBinding.tvPhone.setText("8890790340");
            } else {
                mBinding.tvPhone.setText(user.getPhoneNumber());
            }

        } else {

            mBinding.btnLogin.setVisibility(View.VISIBLE);
            mBinding.imgIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_account));
            mBinding.tvEdit.setVisibility(View.GONE);
            mBinding.tvName.setVisibility(View.GONE);
            mBinding.tvEmail.setVisibility(View.GONE);
            mBinding.tvPhone.setVisibility(View.GONE);
        }
    }
}