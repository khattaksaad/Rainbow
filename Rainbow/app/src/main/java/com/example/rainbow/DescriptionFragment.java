package com.example.rainbow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class DescriptionFragment extends Fragment {
    public DescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_description, container, false);

        // You can set up any controls here like view.findViewById() if needed

        return view; // ✅ return the inflated view
    }
}
