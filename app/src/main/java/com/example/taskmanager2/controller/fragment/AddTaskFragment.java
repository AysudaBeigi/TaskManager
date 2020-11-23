package com.example.taskmanager2.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;

import com.example.taskmanager2.R;
import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskSate;
import com.example.taskmanager2.repository.TaskDBRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;


public class AddTaskFragment extends DialogFragment {
    public static final String ARGS_STATE = "argsState";
    public static final String ARGS_USERNAME = "argsUsername";
    public static final int REQUEST_CODE_DATE_PICKER_FRAGMENT = 1;
    public static final String TAG_DATE_PICKER_FRAGMENT = "tagDatePickerFragment";
    public static final int REQUEST_CODE_TIME_PICKER_FRAGMENT = 2;
    public static final String TAG_TIME_PICKER_FRAGMENT = "tagTimePickerFragment";
    private TextInputEditText mEditTextTitle;
    private TextInputEditText mEditTextDescription;
    private MaterialButton mButtonDatePicker;
    private MaterialButton mButtonTimePicker;
    private MaterialButton mButtonCancel;
    private MaterialButton mButtonSave;
    private MaterialCheckBox mCheckBoxSate;

    private String mUsername;
    private TaskSate mSate;

    private Task mTask;
    private TaskDBRepository mTaskDBRepository;


    public AddTaskFragment() {
        // Required empty public constructor
    }

    public static AddTaskFragment newInstance(String username, TaskSate state) {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_STATE, state);
        args.putString(ARGS_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUsername = getArguments().getString(ARGS_USERNAME);
        mSate = (TaskSate) getArguments().getSerializable(ARGS_STATE);
        mTaskDBRepository = TaskDBRepository.getInstance();
        mTask = new Task(mUsername, mSate);

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View view = inflater.inflate(R.layout.fragment_add_task, null);

        findViews(view);
        initViews();
        setListener();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);

        return builder.create();


    }

    private void initViews() {
        //TODO extract time from date
        mButtonTimePicker.setText(mTask.getDate().toString());
        mButtonTimePicker.setText(mTask.getDate().toString());
        mCheckBoxSate.setText(mSate.toString());
    }

    private void setListener() {
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Task task = new Task(mUsername, mSate);
                // task.setDate();


            }
        });

        mButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mTask.getDate());
                datePickerFragment.
                        setTargetFragment(
                                AddTaskFragment.this, REQUEST_CODE_DATE_PICKER_FRAGMENT);
                datePickerFragment.show(getFragmentManager(), TAG_DATE_PICKER_FRAGMENT);
            }
        });
        mButtonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance();
                timePickerFragment.
                        setTargetFragment(
                                AddTaskFragment.this, REQUEST_CODE_TIME_PICKER_FRAGMENT);
                timePickerFragment.show(getFragmentManager(), TAG_TIME_PICKER_FRAGMENT);
            }
        });


    }

    private void findViews(View view) {
        mEditTextDescription = view.findViewById(R.id.edit_text_description);
        mEditTextTitle = view.findViewById(R.id.edit_text_title);
        mButtonTimePicker = view.findViewById(R.id.btn_time_picker);
        mButtonDatePicker = view.findViewById(R.id.btn_date_picker);
        mButtonCancel = view.findViewById(R.id.btn_cancel);
        mButtonSave = view.findViewById(R.id.btn_save);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        switch (requestCode) {
            case REQUEST_CODE_DATE_PICKER_FRAGMENT:
                Date userSelectedDate =
                        (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_USER_SELECTED_DATE);
                mTaskDBRepository.insertTask(mTask);
                break;
            case REQUEST_CODE_TIME_PICKER_FRAGMENT:

                break;


        }

    }
}