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
import android.widget.DatePicker;

import com.example.taskmanager2.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {
    public static final String ARGS_TASK_NOW_DATE = "argsTaskNowDate";
    public static final String EXTRA_USER_SELECTED_DATE = "com.example.taskmanager2.extraUserSelectedDate";
    private Date mTaskNowDate;
    private DatePicker mDatePicker;

    /****************** CONSTRUCTOR *****************/
    public DatePickerFragment() {
        // Required empty public constructor
    }

    /******************* NEW INSTANCE *****************/

    public static DatePickerFragment newInstance(Date date) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_NOW_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    /**************** ON CREATE ******************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskNowDate = (Date) getArguments().getSerializable(ARGS_TASK_NOW_DATE);

    }

    /*************** ON CREATE DIALOG ****************/
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_date_picker, null);

        findViews(view);

        initViews();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Date userSelectedDate = extractUserSelectedDate();
                        sendResult(userSelectedDate);

                    }
                })
                .setNegativeButton(android.R.string.cancel, null);

        return builder.create();
    }

    /***************** SEND RESULT *******************/
    private void sendResult(Date userSelectedDate) {
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_USER_SELECTED_DATE, userSelectedDate);
        Log.d("TAG","date picker send result userSelectedDate "+userSelectedDate.toString());
        fragment.onActivityResult(requestCode, resultCode, intent);
    }

    /***************** EXTRACT USER SELECTED DATE *****************/
    private Date extractUserSelectedDate() {
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int dayOfMonth = mDatePicker.getDayOfMonth();
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, dayOfMonth);
        return gregorianCalendar.getTime();
    }

    /****************** SET NOW DATE FOR DATE PICKER ***********/

    private void initViews() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTaskNowDate);
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year, monthOfYear, dayOfMonth,null);


    }

    /******************* FIND VIEWS *****************/
    private void findViews(View view) {
        mDatePicker = view.findViewById(R.id.date_picker);
    }
}