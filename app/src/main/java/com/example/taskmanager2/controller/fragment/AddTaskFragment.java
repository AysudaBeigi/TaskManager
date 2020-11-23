package com.example.taskmanager2.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;

import com.example.taskmanager2.R;
import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskSate;
import com.example.taskmanager2.repository.TaskDBRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddTaskFragment extends DialogFragment {
    public static final String ARGS_STATE = "argsState";
    public static final String ARGS_USERNAME = "argsUsername";
    public static final int REQUEST_CODE_DATE_PICKER_FRAGMENT = 1;
    public static final String TAG_DATE_PICKER_FRAGMENT = "tagDatePickerFragment";
    public static final int REQUEST_CODE_TIME_PICKER_FRAGMENT = 2;
    public static final String TAG_TIME_PICKER_FRAGMENT = "tagTimePickerFragment";
    public static final String EXTRA_TASK = "extraTask";
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

    /************************ CONSTRUCTOR *******************/
    public AddTaskFragment() {
        // Required empty public constructor
    }

    /************************* NEW INSTANCE ********************/
    public static AddTaskFragment newInstance(String username, TaskSate state) {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_STATE, state);
        args.putString(ARGS_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    /**************************** ON CREATE ********************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUsername = getArguments().getString(ARGS_USERNAME);
        mSate = (TaskSate) getArguments().getSerializable(ARGS_STATE);
        mTaskDBRepository = TaskDBRepository.getInstance();
        mTask = new Task(mUsername, mSate);

    }

    /************************** ON CREATE DIALOG ******************/

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View view = inflater.inflate(R.layout.fragment_add_task, null);

        findViews(view);
        updateView();
        setListener();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);

        return builder.create();


    }

    /********************************* FIND VIEWS ***********************/
    private void findViews(View view) {
        mEditTextDescription = view.findViewById(R.id.edit_text_description);
        mEditTextTitle = view.findViewById(R.id.edit_text_title);
        mButtonTimePicker = view.findViewById(R.id.btn_time_picker);
        mButtonDatePicker = view.findViewById(R.id.btn_date_picker);
        mButtonCancel = view.findViewById(R.id.btn_cancel);
        mButtonSave = view.findViewById(R.id.btn_save);
    }

    /******************************* UPDATE VIEWS **************************/
    private void updateView() {
        mButtonTimePicker.setText(getStringExactTime());
        mButtonDatePicker.setText(getStringExactDate());
        mCheckBoxSate.setText(mSate.toString());
    }


    /************************* GET STRING EXTRACT TIME *******************/
    private String getStringExactTime() {
        return new SimpleDateFormat("HH:mm:ss").format(mTask.getDate());
    }

    /************************* GET STRING EXTRACT DATE ******************/
    private String getStringExactDate() {
        return new SimpleDateFormat("yyy/MM/dd").format(mTask.getDate());
    }

    /*************************** SET LISTENER *************************/
    private void setListener() {
       // if(TaskListFragment.REQUEST_CODE_ADD_TASK_FRAGMENT==getTargetRequestCode())
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTask();
                mTaskDBRepository.insertTask(mTask);
                sendResult();

            }
        });
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskDBRepository.deleteTask(mTask);
                //TODO back to parent

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
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mTask.getDate());
                timePickerFragment.
                        setTargetFragment(
                                AddTaskFragment.this, REQUEST_CODE_TIME_PICKER_FRAGMENT);
                timePickerFragment.show(getFragmentManager(), TAG_TIME_PICKER_FRAGMENT);
            }
        });


    }

    private void sendResult() {
        Fragment fragment = getTargetFragment();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TASK, (Serializable) mTask);
        fragment.onActivityResult(
                getTargetRequestCode(),
                Activity.RESULT_OK, intent);
    }

    /****************************** UPDATE TASK *********************************/
    private void updateTask() {
        mTask.setTitle(mEditTextTitle.getText().toString());
        mTask.setDescription(mEditTextDescription.getText().toString());
    }

    /******************************* ON ACTIVITY RESULT ************************/

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        switch (requestCode) {
            case REQUEST_CODE_DATE_PICKER_FRAGMENT:
                Date userSelectedDate =
                        (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_USER_SELECTED_DATE);
                mTask.setDate(userSelectedDate);
                updateView();
                break;
            case REQUEST_CODE_TIME_PICKER_FRAGMENT:

                Date userSelectedTime =
                        (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_USER_SELECTED_TIME);
                mTask.setDate(userSelectedTime);
                updateView();
                break;

        }

    }
}