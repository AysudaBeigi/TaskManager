package com.example.taskmanager2.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.example.taskmanager2.R;

import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment {
    public static final String ARGS_TASK_NOW_TIME = "argsTaskNowTime";
    public static final String EXTRA_USER_SELECTED_TIME = "com.example.taskmanager2.extraUserSelectedTime";

    private TimePicker mTimePicker;
    private Date mTaskNowtime;

    /************************* CONSTRUCTOR **********************/

    public TimePickerFragment() {
        // Required empty public constructor
    }

    /*************************** NEW INSTANCE *********************/
    public static TimePickerFragment newInstance(Date date) {
        Log.d("TAG", "TimePickerfragment new Instance");

        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_NOW_TIME, date);
        fragment.setArguments(args);
        return fragment;
    }

    /************************* ON CREATE *************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "TimePickerFragment onCreate");
        super.onCreate(savedInstanceState);
        mTaskNowtime = (Date) getArguments().getSerializable(ARGS_TASK_NOW_TIME);

    }

    /**************************** ON CREATE DIALOG ******************/

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.d("TAG", "TimePickerFragment onCreate Dilaog  ");

        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View view = inflater.inflate(R.layout.fragment_time_picker, null, false);
        findViews(view);
        initViews();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("TAG", "TimePickerFragment ok listener  ");

                        Date userSelectedTime = extractUserSelectedTime();
                        sendResult(userSelectedTime);

                    }
                })
                .setNegativeButton(android.R.string.cancel, null);
        return builder.create();


    }
    /******************************** FIND VIEWS **********************/
    private void findViews(View view) {
        Log.d("TAG", "TimePickerFragment findViews   ");
        mTimePicker = view.findViewById(R.id.time_picker);
    }
    /******************************** INIT VIEWS *******************************/
    @RequiresApi(api = Build.VERSION_CODES.M)

    private void initViews() {

           /* Calendar calendar = Calendar.getInstance();
            calendar.setTime(mTaskNowDate);

            if (Build.VERSION.SDK_INT < 23) {
                mTimePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
                mTimePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
            } else {
                mTimePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
                mTimePicker.setMinute(calendar.get(Calendar.MINUTE));
            }
*/
        Log.d("TAG", "TimePickerFragment initViews   "+ mTaskNowtime.toString());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTaskNowtime);
        mTimePicker.setHour(calendar.get(Calendar.HOUR));
        mTimePicker.setMinute(calendar.get(Calendar.MINUTE));

    }


    /**************************** EXTRACT USER SELECTED TIME *****************/
    @RequiresApi(api = Build.VERSION_CODES.M)

    public Date extractUserSelectedTime() {
        int hour = mTimePicker.getHour();
        int minute = mTimePicker.getMinute();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        Date userSelectedTime = calendar.getTime();
        Log.d("TAG", "TimePickerFragment extract selected    "+userSelectedTime.toString());

        return userSelectedTime;


    }

    /******************************* SEND RESULT ***************************/
    private void sendResult(Date userSelectedTime) {
        Fragment fragment = getTargetFragment();

        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_USER_SELECTED_TIME, userSelectedTime);

        fragment.onActivityResult(requestCode, resultCode, intent);

    }

}
