package com.example.taskmanager2.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanager2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignInFragment extends Fragment {

    private TextInputEditText mEditTextUsername;
    private TextInputEditText mEditTextPassword;

    private MaterialButton mButtonSignIn;
    private MaterialButton mButtonSignUp;

    private String mUsername;
    private String mPassword;


    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_in, container, false);
        findViews(view);
        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void findViews(View view) {
        mButtonSignIn =view.findViewById(R.id.btn_sing_in);
        mButtonSignUp =view.findViewById(R.id.btn_sing_up);

        mEditTextUsername =view.findViewById(R.id.edit_text_username);
        mEditTextPassword =view.findViewById(R.id.edit_text_password);
    }
}