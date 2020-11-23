package com.example.taskmanager2.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.taskmanager2.R;
import com.example.taskmanager2.controller.activity.LauncherActivity;
import com.example.taskmanager2.controller.activity.SignUpActivity;
import com.example.taskmanager2.controller.activity.TaskListPagerActivity;
import com.example.taskmanager2.model.User;
import com.example.taskmanager2.repository.UserDBRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


public class SignUpFragment extends Fragment {
    private TextInputEditText mEditTextUsername;
    private TextInputEditText mEditTextPassword;

    private MaterialButton mButtonSignUp;

    private UserDBRepository mUserDBRepository;

    private LinearLayout mLinearLayoutSignup;

    private String mUsername;
    private String mPassword;

    /*********************** CONSTRUCTOR **********************/
    public SignUpFragment() {
        // Required empty public constructor
    }

    /************************* NEW INSTANCE ********************/

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /************************ ON CREATE ******************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserDBRepository = UserDBRepository.getInstance();
    }

    /******************* ON CREATE VIEW ********************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        findViews(view);
        setListener();
        return view;
    }

    /********************** FIND VIEWS *********************/
    private void findViews(View view) {
        mButtonSignUp = view.findViewById(R.id.btn_sign_up);

        mEditTextUsername = view.findViewById(R.id.edit_text_username);
        mEditTextPassword = view.findViewById(R.id.edit_text_password);
        mLinearLayoutSignup = view.findViewById(R.id.linear_layout_sign_up);
    }

    /************************** SET LISTENER ************************/

    private void setListener() {
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsername = mEditTextUsername.getText().toString();
                mPassword = mEditTextPassword.getText().toString();
                if (mUsername != "" && mPassword != "")
                    if (mUserDBRepository.isUsernameTaken(mUsername)) {
                        generateSnackbar(mLinearLayoutSignup, R.string.snackbar_is_taken_username);
                    } else {
                        if (mUserDBRepository.isPasswordTaken(mPassword))
                            generateSnackbar(mLinearLayoutSignup, R.string.snackbar_is_taken_password);
                        else {
                            User user = new User(mUsername, mPassword);
                            mUserDBRepository.insertUser(user);
                     /*  Intent intent= LauncherActivity.newIntent(getActivity());
                        startActivity(intent);*/
                            Intent intent = new Intent();
                            getTargetFragment().onActivityResult(SignInFragment.REQUEST_CODE_SIGN_UP,
                                    getActivity().RESULT_OK, intent);

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.beginTransaction().remove(SignUpFragment.this)
                                    .add(R.id.fragment_container, SignInFragment.newInstance())
                                    .remove(SignUpFragment.this)
                                    .commit();


                            Intent pagerIntent = TaskListPagerActivity.newIntent(getActivity(), mUsername);
                            startActivity(intent);
                        }
                    }
            }
        });
    }


    /********************* GENERATE SNACK BAR ********************/
    private void generateSnackbar(View view, int stringId) {
        Snackbar snackbar = Snackbar
                .make(view,
                        stringId, Snackbar.LENGTH_LONG);
        snackbar.show();
    }


}