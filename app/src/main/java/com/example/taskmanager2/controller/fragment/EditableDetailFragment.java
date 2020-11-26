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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.taskmanager2.R;
import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskState;
import com.example.taskmanager2.repository.TaskDBRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditableDetailFragment extends DialogFragment {

    public static final int REQUEST_CODE_DATE_PICKER_FRAGMENT = 1;
    public static final String TAG_DATE_PICKER_FRAGMENT = "tagDatePickerFragment";
    public static final int REQUEST_CODE_TIME_PICKER_FRAGMENT = 2;
    public static final String TAG_TIME_PICKER_FRAGMENT = "tagTimePickerFragment";
    public static final String EXTRA_TASK = "com.example.taskmanager2.extraTask";
    public static final String ARGS_TASK = "argsTask";
    private TextInputEditText mEditTextTitle;
    private TextInputEditText mEditTextDescription;
    private MaterialButton mButtonDatePicker;
    private MaterialButton mButtonTimePicker;
    private MaterialButton mButtonDeleteTask;
    private MaterialCheckBox mCheckBoxSate;

    private RadioGroup mRadioGroupEditState;
    private RadioButton mRadioButtonTodo;
    private RadioButton mRadioButtonDoing;
    private RadioButton mRadioButtonDone;


    private Task mTask;
    private TaskDBRepository mTaskDBRepository;


    /************************ CONSTRUCTOR *******************/
    public EditableDetailFragment() {
        // Required empty public constructor
    }

    /************************* NEW INSTANCE ********************/
    public static EditableDetailFragment newInstance(Task task) {
        EditableDetailFragment fragment = new EditableDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    /**************************** ON CREATE ********************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTask = (Task) getArguments().getSerializable(ARGS_TASK);
        mTaskDBRepository = TaskDBRepository.getInstance();
    }

    /************************** ON CREATE DIALOG ******************/

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View view = inflater.inflate(R.layout.fragment_editable_detail, null);

        findViews(view);
        updateView();
        setListener();
        AlertDialog.Builder builder = buildAlertDialog(view);

        return builder.create();


    }

    private AlertDialog.Builder buildAlertDialog(View view) {
        return new AlertDialog.Builder(getActivity())

                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("TAG", "EditalbeDetailF Save listener");
                        updateTask();
                        mTaskDBRepository.updateTask(mTask);
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_TASK, mTask);
                        sendResult(intent);

                    }
                }).setView(view);
    }

    /********************************* FIND VIEWS ***********************/
    private void findViews(View view) {
        mEditTextDescription = view.findViewById(R.id.edit_text_description);
        mEditTextTitle = view.findViewById(R.id.edit_text_title);
        mButtonTimePicker = view.findViewById(R.id.btn_time_picker);
        mButtonDatePicker = view.findViewById(R.id.btn_date_picker);
        mButtonDeleteTask = view.findViewById(R.id.btn_delete_task);
        mCheckBoxSate = view.findViewById(R.id.check_box_sate);

        mRadioGroupEditState = view.findViewById(R.id.radio_group_edit_state);
        mRadioButtonTodo = view.findViewById(R.id.btn_todo);
        mRadioButtonDoing = view.findViewById(R.id.btn_doing);
        mRadioButtonDone = view.findViewById(R.id.btn_done);


    }

    /******************************* UPDATE VIEWS **************************/
    private void updateView() {
        mEditTextTitle.setText(mTask.getTitle());
        mEditTextDescription.setText(mTask.getDescription());
        mButtonDatePicker.setText(getStringExactDate());
        mButtonTimePicker.setText(getStringExactTime());
        mCheckBoxSate.setText(mTask.getSate().toString());
        mCheckBoxSate.setEnabled(false);
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
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mTask.getDate());
                datePickerFragment.
                        setTargetFragment(
                                EditableDetailFragment.this, REQUEST_CODE_DATE_PICKER_FRAGMENT);
                datePickerFragment.show(getFragmentManager(), TAG_DATE_PICKER_FRAGMENT);
            }
        });
        mButtonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mTask.getDate());
                timePickerFragment.
                        setTargetFragment(
                                EditableDetailFragment.this, REQUEST_CODE_TIME_PICKER_FRAGMENT);
                timePickerFragment.show(getFragmentManager(), TAG_TIME_PICKER_FRAGMENT);
            }
        });
        mRadioGroupEditState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == mRadioButtonTodo.getId()) {
                    mRadioButtonTodo.setChecked(true);
                    mTask.setSate(TaskState.TODO);
                    // mTaskDBRepository.updateTask(mTask);

                } else if (checkedId == mRadioButtonDoing.getId()) {
                    mRadioButtonDoing.setChecked(true);
                    mTask.setSate(TaskState.DOING);
                    // mTaskDBRepository.updateTask(mTask);

                } else if (checkedId == mRadioButtonDone.getId()) {
                    mRadioButtonDone.setChecked(true);
                    mTask.setSate(TaskState.DONE);
                    // mTaskDBRepository.updateTask(mTask);

                }

            }
        });
        mButtonDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "EditableDetailF Delete listener");
                mTaskDBRepository.deleteTask(mTask);
                Intent intent = new Intent();
                sendResult(intent);

            }
        });


    }

    /****************** SEND RESULT ***********************/
    private void sendResult(Intent intent) {
        Fragment fragment = getTargetFragment();
        int resultCode = Activity.RESULT_OK;
        int requestCode = getTargetRequestCode();
        fragment.onActivityResult(requestCode, resultCode, intent);
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
                mTask.setTime(userSelectedTime);
                updateView();
                break;

        }

    }
}