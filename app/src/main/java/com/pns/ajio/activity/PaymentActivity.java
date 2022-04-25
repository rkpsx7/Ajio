package com.pns.ajio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pns.ajio.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    private DatabaseReference mDatabaseReference;
    private final String uid = FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("ProductDetails");

        if (getIntent() != null) {
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

        Toast.makeText(this, "Payment successful ", Toast.LENGTH_SHORT).show();

        ArrayList<String> list = getIntent().getStringArrayListExtra("list");
        Map<String, Object> map = new HashMap<>();

        list.add(uid);

        map.put("ordered", list);

        // Updating the ordered and wishListed state of a product

        mDatabaseReference.child(getIntent().getStringExtra("key"))
                .updateChildren(map).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                Toast.makeText(this, "Location detected", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Product will be delivered by tomorrow", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, BagActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {

        Log.d("TAG", "onPaymentError: " + s);
        finish();
    }
}