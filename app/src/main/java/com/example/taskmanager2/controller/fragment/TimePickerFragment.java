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

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.example.taskmanager2.R;

import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment {
    public static final String ARGS_TASK_NOW_DATE = "argsTaskNowTime";
    public static final String EXTRA_USER_SELECTED_TIME = "com.example.taskmanager2.extraUserSelectedTime";

    private TimePicker mTimePicker;
    private Date mTaskNowDate;

    /************************* CONSTRUCTOR **********************/

    public TimePickerFragment() {
        // Required empty public constructor
    }

    /*************************** NEW INSTANCE *********************/
    public static TimePickerFragment newInstance(Date date) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_NOW_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    /************************* ON CREATE *************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskNowDate = (Date) getArguments().getSerializable(ARGS_TASK_NOW_DATE);

    }

    /**************************** ON CREATE DIALOG ******************/

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View view = inflater.inflate(R.layout.fragment_time_picker, null, false);
        findViews(view);
        initViews();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date userSelectedTime = extractUserSelectedTime();
                        sendResult(userSelectedTime);

                    }
                })
                .setNegativeButton(android.R.string.cancel, null);
        return builder.create();


    }
    /******************************** FIND VIEWS **********************/
    private void findViews(View view) {
        mTimePicker = view.findViewById(R.id.btn_time_picker);
    }
    /******************************** INIT VIEWS *******************************/
    private void initViews() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTaskNowDate);
        mTimePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        mTimePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }


    /**************************** EXTRACT USER SELECTED TIME *****************/
    public Date extractUserSelectedTime() {
        int hour = mTimePicker.getCurrentHour();
        int minute = mTimePicker.getCurrentMinute();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        Date userSelectedTime = calendar.getTime();
        return userSelectedTime;

    }

    /******************************* SEND RESULT ***************************/
    private void sendResult(Date userSelectedTime) {
        Fragment fragment = getTargetFragment();
        Intent intent = new Intent();
        intent.putExtra("ExtraUserSelectedTime", userSelectedTime);
        fragment.onActivityResult(
                getTargetRequestCode()
                , Activity.RESULT_OK, intent);
    }

}
