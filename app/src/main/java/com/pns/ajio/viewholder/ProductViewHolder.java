package com.pns.ajio.viewholder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pns.ajio.R;
import com.pns.ajio.activity.BagActivity;
import com.pns.ajio.model.ProductModel;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public ImageView mImgProduct, mImgWishList;
    public TextView mTvProductName, mTvProductSeller, mTvProductPrice, mTvDeliveryInfo;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        mImgProduct = itemView.findViewById(R.id.iv_product_image);
        mTvProductSeller = itemView.findViewById(R.id.tv_product_seller);
        mTvProductName = itemView.findViewById(R.id.tv_product_name);
        mTvProductPrice = itemView.findViewById(R.id.tv_product_price);
        mImgWishList = itemView.findViewById(R.id.img_wishlist_2);
        mTvDeliveryInfo = itemView.findViewById(R.id.tv_delivery);

    }

    // Setting the data in item layout

    @SuppressLint("SetTextI18n")
    public void setData(ProductModel model, Context context) {

        if (context instanceof BagActivity) {
            mTvProductPrice.setVisibility(View.GONE);
            mTvDeliveryInfo.setVisibility(View.VISIBLE);

        } else {
            mTvProductPrice.setVisibility(View.VISIBLE);
            mTvDeliveryInfo.setVisibility(View.GONE);
        }

        Glide.with(mImgProduct).
                load(model.getUrl()).
                into(mImgProduct);
        mTvProductName.setText(model.getProductName());
        mTvProductSeller.setText(model.getSeller());
        mTvProductPrice.setText("₹" + model.getPrice());
    }
}