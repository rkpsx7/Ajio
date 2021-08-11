package com.example.ajio.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajio.R;
import com.example.ajio.activity.AccountActivity;
import com.example.ajio.activity.BagActivity;
import com.example.ajio.activity.PaymentActivity;
import com.example.ajio.activity.ProductActivity;
import com.example.ajio.activity.WishlistActivity;
import com.example.ajio.model.ProductModel;
import com.example.ajio.viewholder.ProductViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private final List<ProductModel> mList;
    private final Context mContext;
    private int position;

    public ProductAdapter(List<ProductModel> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
        Inflating the item layout and passing to view holder
        */
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        // Showing wishlist icon only if we were in product activity

        if (mContext instanceof WishlistActivity || mContext instanceof BagActivity) {
            holder.mImgWishList.setVisibility(View.GONE);
        } else {
            holder.mImgWishList.setVisibility(View.VISIBLE);
        }

        checkWishListedItem(position, holder);

        SharedPreferences preferences = mContext.getSharedPreferences("PREFS", MODE_PRIVATE);
        boolean loggedInAlready = preferences.getBoolean("loggedIn", false);

        holder.setData(mList.get(position), mContext);
        holder.mImgWishList.setOnClickListener(v -> {

            if (loggedInAlready) {
                wishlistItem(position);
            } else {
                Toast.makeText(mContext, "Sign in first to wishlist this product", Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, AccountActivity.class));
                ((Activity)mContext).finish();
            }

        });
        holder.mImgProduct.setOnClickListener(v -> {

            if (loggedInAlready) {
                this.position = position;

                if (mContext instanceof BagActivity) {
                    Toast.makeText(mContext, "You have already purchased this product", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(mContext, PaymentActivity.class);
                    intent.putExtra("key", mList.get(position).getKey());
                    mContext.startActivity(intent);
                }
            } else {
                Toast.makeText(mContext, "Sign in first to purchase this product", Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, AccountActivity.class));
                ((Activity)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void wishlistItem(int position) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ProductDetails");

        Map<String, Object> map = new HashMap<>();

        // Updating state of wishlist of a product after clicking on it

        if (mList.get(position).isWishlisted()) {

            map.put("wishlisted", false);

            reference.child(mList.get(position).getKey()).updateChildren(map).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {

                    Toast.makeText(mContext, "Removed from wishlist", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                }
            });

        } else {

            map.put("wishlisted", true);

            reference.child(mList.get(position).getKey()).updateChildren(map).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {

                    Toast.makeText(mContext, "Product WishListed", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void checkWishListedItem(int position, ProductViewHolder holder) {

        if (mContext instanceof ProductActivity) {

            // Changing the color of the heart icon depending on it is wishListed or not

            if (mList.get(position).isWishlisted()) {
                holder.mImgWishList.setImageResource(R.drawable.ic_wishlisted);
            } else {
                holder.mImgWishList.setImageResource(R.drawable.ic_favourite);
            }
        }
    }
}