package com.pns.ajio.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pns.ajio.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    public BottomSheetFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
    }
}