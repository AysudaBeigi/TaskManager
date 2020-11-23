package com.example.taskmanager2.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanager2.R;

public class EditableDetailFragment extends DialogFragment {


    public EditableDetailFragment() {
        // Required empty public constructor
    }
    public static EditableDetailFragment newInstance() {
        EditableDetailFragment fragment = new EditableDetailFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_editable_detail, container, false);
        return view;
    }
}