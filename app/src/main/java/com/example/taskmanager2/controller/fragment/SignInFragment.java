package com.example.taskmanager2.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.taskmanager2.R;
import com.example.taskmanager2.controller.activity.SignUpActivity;
import com.example.taskmanager2.controller.activity.TaskListPagerActivity;
import com.example.taskmanager2.repository.UserDBRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class SignInFragment extends Fragment {

    public static final int REQUEST_CODE_SIGN_UP = 1;
    public static final String TAG = "signUp";
    private TextInputEditText mEditTextUsername;
    private TextInputEditText mEditTextPassword;

    private MaterialButton mButtonSignIn;
    private MaterialButton mButtonSignUp;

    private String mUsername;
    private String mPassword;

    private UserDBRepository mUserDBRepository;

    private LinearLayout mLinearLayoutSignIn;

    /************** CONSTRUCTOR *************************/

    public SignInFragment() {
        // Required empty public constructor
    }

    /********************** NEW INSTANCE ************************/
    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**************** ON CREATE ****************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TAG", "this is SignIn on create   ");

        mUserDBRepository = UserDBRepository.getInstance();

    }

    /************************ ON CREATE VIEW *********************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("TAG", "this is SignIn onCreateView   ");

        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        findViews(view);
        setListener();

        return view;
    }

    /******************* FIND VIEWS ***************************/
    private void findViews(View view) {
        mButtonSignIn = view.findViewById(R.id.btn_sing_in);
        mButtonSignUp = view.findViewById(R.id.btn_sign_up);

        mEditTextUsername = view.findViewById(R.id.edit_text_username);
        mEditTextPassword = view.findViewById(R.id.edit_text_password);
        mLinearLayoutSignIn = view.findViewById(R.id.linear_layout_sing_in);
    }

    /******************** SET LISTENER **************************/
    private void setListener() {
        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "this is SignIn listener login  ");

                mUsername = mEditTextUsername.getText().toString();
                mPassword = mEditTextPassword.getText().toString();
                if (mUsername != "" && mPassword != "")
                    if (mUserDBRepository.isUsernameTaken(mUsername)
                            && mUserDBRepository.isPasswordTaken(mPassword)) {
                        Log.d("TAG", "user exits   ");

                        if (isMachUsernameAndPassword()) {
                            Log.d("TAG", "username and password are correct");

                            generateSnackbar(mLinearLayoutSignIn, R.string.snackbar_user_find);
                            Intent intent = TaskListPagerActivity.newIntent(getActivity(), mUsername);
                            startActivity(intent);
                        } else {
                            Log.d("TAG", "username is correct but password is incorrect");
                            generateSnackbar(mLinearLayoutSignIn,
                                    R.string.snackbar_invalid_username_or_password);
                        }

                    } else if (!mUserDBRepository.isUsernameTaken(mUsername)
                            && !mUserDBRepository.isPasswordTaken(mPassword)) {

                        Log.d("TAG", "you have not account ");
                        generateSnackbar(mLinearLayoutSignIn, R.string.snackbar_not_exits_user);
                    }

            }
        });
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("TAG", "up listener in sign in fragment ");
                Intent intent = SignUpActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
    }

    /*******************  ON ACTIVITY RESULT *****************/
  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_CANCELED || data == null)
            return;

        if (requestCode == REQUEST_CODE_SIGN_UP) {

        }
    }
*/

    /******************** GENERATE SNACK BAR *********************/
    private void generateSnackbar(View view, int stringId) {
        Snackbar snackbar = Snackbar
                .make(view,
                        stringId, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    /*********************** IS MACH USERNAME AND PASSWORD ***************/
    public boolean isMachUsernameAndPassword() {

        if (mUserDBRepository.getUser(mUsername).getPassword().equals(mPassword))
            return true;
        return false;

    }


}