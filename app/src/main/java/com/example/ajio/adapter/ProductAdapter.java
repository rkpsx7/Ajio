package com.example.ajio.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajio.interfaces.OnClickListener;
import com.example.ajio.model.ProductModel;
import com.example.ajio.viewholder.ProductViewHolder;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private List<ProductModel> mList;
    private OnClickListener mListener;

    public ProductAdapter(List<ProductModel> list, OnClickListener listener) {
        mList = list;
        mListener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        holder.setData(mList.get(position));
        holder.itemView.setOnClickListener(v-> mListener.onProductClick(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}