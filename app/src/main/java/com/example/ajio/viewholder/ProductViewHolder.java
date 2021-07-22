package com.example.ajio.viewholder;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ajio.R;
import com.example.ajio.model.ProductModel;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public ImageView mImageView , mImgWishList;
    public TextView productName, productSeller, productPrice;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        mImageView = itemView.findViewById(R.id.iv_product_image);
        productSeller = itemView.findViewById(R.id.product_seller);
        productName = itemView.findViewById(R.id.product_name);
        productPrice = itemView.findViewById(R.id.product_price);
        mImgWishList = itemView.findViewById(R.id.img_wishlist);
    }

    @SuppressLint("SetTextI18n")
    public void setData(ProductModel model) {

        Glide.with(mImageView).
                load(model.getUrl()).
                into(mImageView);
        productName.setText(model.getProductName());
        productSeller.setText(model.getSeller());
        productPrice.setText("₹" + model.getPrice());
    }
}