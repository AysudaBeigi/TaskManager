package com.example.taskmanager2.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.taskmanager2.R;
import com.example.taskmanager2.controller.activity.AdminActivity;
import com.example.taskmanager2.controller.activity.TaskListPagerActivity;
import com.example.taskmanager2.database.TaskManagerDBSchema;
import com.example.taskmanager2.database.UserCursorWrapper;
import com.example.taskmanager2.model.User;
import com.example.taskmanager2.repository.UserDBRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class SignUpFragment extends Fragment {
    private TextInputEditText mEditTextUsername;
    private TextInputEditText mEditTextPassword;

    private MaterialButton mButtonSignUp;
    private MaterialButton mButtonSignUpAdmin;

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
        // Log.d("TAG", "sign up fragment onCreate ");

        mUserDBRepository = UserDBRepository.getInstance(getActivity());
    }

    /******************* ON CREATE VIEW ********************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Log.d("TAG", "sign up fragment onCreateView ");
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        findViews(view);
        setListener();
        return view;
    }

    /********************** FIND VIEWS *********************/
    private void findViews(View view) {
        mButtonSignUp = view.findViewById(R.id.btn_sign_up);
        mButtonSignUpAdmin = view.findViewById(R.id.btn_sign_up_admin);

        mEditTextUsername = view.findViewById(R.id.edit_text_username);
        mEditTextPassword = view.findViewById(R.id.edit_text_password);
        mLinearLayoutSignup = view.findViewById(R.id.linear_layout_sign_up);
    }

    /************************** SET LISTENER ************************/

    private void setListener() {

        mButtonSignUpAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsername = "admin";
                mEditTextUsername.setText("admin");
            }
        });
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.d("TAG", "sign up fragment signup listener ");
                mUsername = mEditTextUsername.getText().toString();
                mPassword = mEditTextPassword.getText().toString();
                if (isUserPassEntered()) {
                    if (!isTakenUserPass()) {
                        signUpUser(mUsername, mPassword);
                        if (mUsername.equals("admin"))
                            startAdminActivity();
                        else
                            startPagerActivity();
                    } else {
                        generateSnackbar(
                                mLinearLayoutSignup, R.string.snackbar_is_taken_username_or_password);
                    }

                } else
                    generateSnackbar(
                            mLinearLayoutSignup, R.string.snackbar_username_or_password_is_empty);
            }
        });
    }

    private void startAdminActivity() {
        Intent intent = AdminActivity.newIntent(getActivity());
        startActivity(intent);
    }

    private boolean isTakenUserPass() {
        return isUsernameTaken(mUsername) || isPasswordTaken(mPassword);
    }

    private boolean isUserPassEntered() {
        return !mUsername.isEmpty() && !mPassword.isEmpty()
                && mUsername != null && mPassword != null;
    }

    private void startPagerActivity() {
        Intent intent =
                TaskListPagerActivity.newIntent(getActivity(), mUsername);
        startActivity(intent);
    }

    private void signUpUser(String username, String password) {
        User user = new User(username, password);
        mUserDBRepository.insertUser(user);
    }


    /********************* GENERATE SNACK BAR ********************/
    private void generateSnackbar(View view, int stringId) {
        Snackbar snackbar = Snackbar
                .make(view, stringId, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    /************************** IS USERNAME TAKEN ***********************/
    public boolean isUsernameTaken(String username) {
        if (mUserDBRepository.getUser(username) != null)
            return true;
        return false;
    }

    /**************************** IS PASSWORD TAKEN *********************/
    public boolean isPasswordTaken(String password) {
        List<User> users = mUserDBRepository.getUsers();

        List<String> passwords = getAllPasswords(users);
        for (int i = 0; i < passwords.size(); i++) {
            if (passwords.get(i).equals(password))
                return true;
        }
        return false;

    }

    private List<String> getAllPasswords(List<User> users) {
        List<String> passwords = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            passwords.add(users.get(i).getPassword());
        }
        return passwords;
    }


}