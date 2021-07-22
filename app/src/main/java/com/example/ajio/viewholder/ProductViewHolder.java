package com.example.ajio.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ajio.R;
import com.example.ajio.model.ProductModel;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public ImageView mImageView;
    public TextView productName, productDisc, productPrice;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        mImageView = itemView.findViewById(R.id.bottom_menu);
        productName = itemView.findViewById(R.id.bottom_menu);
        productDisc = itemView.findViewById(R.id.bottom_menu);
        productPrice = itemView.findViewById(R.id.bottom_menu);
    }

    public void setData(ProductModel model) {

//        Glide.with(mImageView).
//                load(model.getUrl()).
//                into(mImageView);
//        productName.setText(model.getProductName());
//        productDisc.setText(model.getProductDisc());
//        productPrice.setText(String.valueOf(model.getPrice()));
    }
}