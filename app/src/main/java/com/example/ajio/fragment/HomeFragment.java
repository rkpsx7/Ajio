package com.example.ajio.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.ajio.R;
import com.example.ajio.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    public HomeFragment() {}

    FragmentHomeBinding mBinding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = FragmentHomeBinding.inflate(inflater, container, false);

        Glide.with(mBinding.imgSale)
                .load("https://assets.ajio.com/medias/sys_master/images/images/h4d/hba/33870600339486/15072021-d-unisex-topbanner-50to80-iconicfashionsale.jpg")
                .into(mBinding.imgSale);

        Glide.with(mBinding.imgMobikwik)
                .load("https://assets.ajio.com/medias/sys_master/images/images/h83/h73/33815931617310/07072021-D-unisex-alliances-mobikwik.jpg")
                .into(mBinding.imgMobikwik);

        return mBinding.getRoot();
    }
}