package com.example.ajio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ajio.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    private DatabaseReference mDatabaseReference;
    private String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("ProductDetails");

        if (getIntent() != null) {
            key = getIntent().getStringExtra("key");
            makePayment();
        }
    }

    private void makePayment() {

        Checkout checkout = new Checkout();

        checkout.setKeyID("rzp_test_KGbp2Y9RY4nziY");

        checkout.setImage(R.drawable.ajio);

        try {
            JSONObject options = new JSONObject();

            options.put("name", "AJIO");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");
            options.put("prefill.email", "vipindev@gmail.com");
            options.put("prefill.contact", "8890790340");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 6);
            options.put("retry", retryObj);

            checkout.open(this, options);

        } catch (Exception e) {

            Toast.makeText(this, "Payment failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

        Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();

        Map<String, Object> map = new HashMap<>();
        map.put("ordered", true);
        map.put("wishlisted", false);

        // Updating the ordered and wishListed state of a product

        mDatabaseReference.child(key).updateChildren(map).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                startActivity(new Intent(this, BagActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
    }
}