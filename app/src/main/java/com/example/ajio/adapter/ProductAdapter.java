package com.example.ajio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajio.R;
import com.example.ajio.activity.BagActivity;
import com.example.ajio.activity.ProductActivity;
import com.example.ajio.activity.WishlistActivity;
import com.example.ajio.interfaces.OnClickListener;
import com.example.ajio.model.ProductModel;
import com.example.ajio.viewholder.ProductViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private final List<ProductModel> mList;
    private final OnClickListener mListener;
    private final Context mContext;

    public ProductAdapter(List<ProductModel> list, OnClickListener listener, Context context) {
        mList = list;
        mListener = listener;
        mContext = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        if (mContext instanceof WishlistActivity || mContext instanceof BagActivity) {
            holder.mImgWishList.setVisibility(View.GONE);
        } else {
            holder.mImgWishList.setVisibility(View.VISIBLE);
        }

        checkWishList(mList.get(position).getUrl(), holder);

        holder.setData(mList.get(position));
        holder.mImageView.setOnClickListener(v -> mListener.onProductClick(position));
        holder.mImgWishList.setOnClickListener(v -> wishlistItem(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void wishlistItem(int position) {

        ProductModel model = mList.get(position);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Wishlist");

        reference.orderByChild("url").equalTo(model.getUrl()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.exists()) {

                    String key = reference.push().getKey();

                    assert key != null;

                    reference.child(key).setValue(model).addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            Toast.makeText(mContext, "Product wishlisted", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void checkWishList(String imageUrl, ProductViewHolder holder) {

        if (mContext instanceof ProductActivity) {

            FirebaseDatabase.getInstance().getReference("Wishlist").orderByChild("url").equalTo(imageUrl)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {

                                holder.mImgWishList.setImageResource(R.drawable.ic_wishlisted);

                            } else {

                                holder.mImgWishList.setImageResource(R.drawable.ic_favourite);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
    }
}