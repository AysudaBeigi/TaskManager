package com.example.taskmanager2.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.taskmanager2.R;
import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskState;
import com.example.taskmanager2.repository.TaskDBRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AddTaskFragment extends DialogFragment {

    public static final String ARGS_STATE = "argsState";
    public static final String ARGS_USERNAME = "argsUsername";
    public static final int REQUEST_CODE_DATE_PICKER_FRAGMENT = 1;
    public static final int REQUEST_CODE_TIME_PICKER_FRAGMENT = 2;
    public static final String TAG_DATE_PICKER_FRAGMENT = "tagDatePickerFragment";
    public static final String TAG_TIME_PICKER_FRAGMENT = "tagTimePickerFragment";
    public static final String EXTRA_TASK = "com.example.taskmanager2.extraTask";
    private TextInputEditText mEditTextTitle;
    private TextInputEditText mEditTextDescription;
    private MaterialButton mButtonDatePicker;
    private MaterialButton mButtonTimePicker;
    private MaterialCheckBox mCheckBoxSate;

    private String mUsername;
    private TaskState mSate;

    private Task mTask;
    private TaskDBRepository mTaskDBRepository;

    /************************ CONSTRUCTOR *******************/
    public AddTaskFragment() {
        // Required empty public constructor
    }

    /************************* NEW INSTANCE ********************/
    public static AddTaskFragment newInstance(String username, TaskState state) {
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
        mSate = (TaskState) getArguments().getSerializable(ARGS_STATE);
        mTaskDBRepository = TaskDBRepository.getInstance(getActivity());
        mTask = new Task(mUsername, mSate);
        Log.d("TAG", "AddTaskFragment on create " + mTask.getUsername());

    }

    /************************** ON CREATE DIALOG ******************/

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Log.d("TAG", "AddTaskFragment on createDilaog ");

        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View view = inflater.inflate(R.layout.fragment_add_task, null);

        findViews(view);
        updateView();
        setListener();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.d("TAG", "AddTaskFragment Alert save listener  ");
                        updateTask();
                        mTaskDBRepository.insertTask(mTask);
                        sendResult();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("TAG", "AddTaskFragment Alert cancel listener  ");

                        mTaskDBRepository.deleteTask(mTask);
                    }
                }).setView(view);

        return builder.create();


    }

    /********************************* FIND VIEWS ***********************/
    private void findViews(View view) {
        Log.d("TAG", "AddTaskFragment findViews  ");

        mEditTextDescription = view.findViewById(R.id.edit_text_description);
        mEditTextTitle = view.findViewById(R.id.edit_text_title);
        mButtonTimePicker = view.findViewById(R.id.btn_time_picker);
        mButtonDatePicker = view.findViewById(R.id.btn_date_picker);
        mCheckBoxSate = view.findViewById(R.id.check_box_sate);

    }

    /******************************* UPDATE VIEWS **************************/
    private void updateView() {
        Log.d("TAG", "AddTaskFragment updateView  ");

        mButtonDatePicker.setText(getStringExactDate());
        mButtonTimePicker.setText(getStringExactTime());
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


        mButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "AddTaskFragment DatePicker listener  ");

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
                Log.d("TAG", "AddTaskFragment timePicker listener  ");

                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mTask.getDate());
                timePickerFragment.
                        setTargetFragment(
                                AddTaskFragment.this, REQUEST_CODE_TIME_PICKER_FRAGMENT);
                timePickerFragment.show(getFragmentManager(), TAG_TIME_PICKER_FRAGMENT);
            }
        });


    }

    /****************** SEND RESULT ***********************/
    private void sendResult() {
        Log.d("TAG", "sendResult");

        Fragment fragment = getTargetFragment();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TASK, mTask);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }

    /****************************** UPDATE TASK *********************************/
    private void updateTask() {
        Log.d("TAG", "AddTaskFragment updateTask  ");

        mTask.setTitle(mEditTextTitle.getText().toString());
        mTask.setDescription(mEditTextDescription.getText().toString());
    }

    /******************************* ON ACTIVITY RESULT ************************/

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       /* Log.d("TAG", "AddTaskFragment onActivityResult   " + requestCode);
*/
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
                Log.d("TAG", "AddTaskFragment onActivityResult  time picker requset ");


                Date userSelectedTime =
                        (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_USER_SELECTED_TIME);

                mTask.setTime(userSelectedTime);
                updateView();
                break;

        }

    }
}