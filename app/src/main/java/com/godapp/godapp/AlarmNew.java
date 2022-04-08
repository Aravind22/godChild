package com.godapp.godapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dpro.widgets.WeekdaysPicker;
import com.godapp.godapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AlarmNew extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_new);
        final int[] startAlarmHour = new int[1];
        final int[] startAlarmMinute = new int[1];
        final int[] endAlarmHour = new int[1];
        final int[] endAlarmMinute = new int[1];
        final int[] Selectedinterval = new int[1];
        final PendingIntent[] pendingIntent = new PendingIntent[1];
        final String[] intervals = {"1","2","3","4","6","8","12"};
        final String[] startTimeAM_PM = new String[1];
        final String[] endTimeAM_PM = new String[1];
        List<Integer> selectedDays = new ArrayList<>();
        selectedDays.add(2);
        selectedDays.add(3);
        selectedDays.add(4);
        selectedDays.add(5);
        selectedDays.add(6);


        TextView startTime = (TextView) findViewById(R.id.startTimeEditText);
        TextView endTime = (TextView) findViewById(R.id.endTimeEditText);
        Button setAlarm = (Button) findViewById(R.id.setAlarmBtn);
        Spinner alarmIntervalSpinner = (Spinner)findViewById(R.id.alarmIntervalSpinner);
        TextView currAlarmTxt = (TextView)findViewById(R.id.currentAlarmTxt);
        TextView startTimeIndicator = (TextView)findViewById(R.id.startTimeIndicator);
        TextView endTimeIndicator = (TextView)findViewById(R.id.endTimeIndicator);
        WeekdaysPicker widget = (WeekdaysPicker) findViewById(R.id.weekdaysPicker);
        widget.setSelectedDays(selectedDays);

        SharedPreferences alarmSharedPref = getApplicationContext().getSharedPreferences("myAlarm", MODE_PRIVATE);
        SharedPreferences.Editor editor = alarmSharedPref.edit();

        String currentAlarmHour = alarmSharedPref.getString("currentAlarm", null);
        String startHour = alarmSharedPref.getString("startAlarm", null);
        String currentAlarmMin = alarmSharedPref.getString("currentAlarmMin", null);
        String endHour = alarmSharedPref.getString("endAlarm", null);
        String endMin = alarmSharedPref.getString("endAlarmMin", null);
        String startIndicator = alarmSharedPref.getString("startTimeIndicator", null);
        String endIndicator = alarmSharedPref.getString("endTimeIndicator", null);

        if(startHour != null && currentAlarmMin != null && endHour != null && endMin != null && startIndicator != null && endIndicator != null){
            startTime.setText(String.format("%02d:%02d", (Integer.parseInt(startHour) == 12 || Integer.parseInt(startHour) == 0) ? 12 :
                    Integer.parseInt(startHour) % 12, Integer.parseInt(currentAlarmMin)));
            startTimeIndicator.setText(startIndicator);

            endTime.setText(String.format("%02d:%02d", (Integer.parseInt(endHour) == 12 || Integer.parseInt(endHour) == 0) ? 12 :
                    Integer.parseInt(endHour) % 12, Integer.parseInt(endMin)));
            endTimeIndicator.setText(endIndicator);
        } else {
            startTime.setText("08:00");
            endTime.setText("10:00");
        }


        if(currentAlarmHour!= null && currentAlarmMin != null){
            boolean isPM = (Integer.parseInt(currentAlarmHour) >= 12);
            String indicator = "AM";
            if(isPM){
                indicator = "PM";
            }else {
                indicator = "AM";
            }
            String timeFormatted = String.format("%02d:%02d", (Integer.parseInt(currentAlarmHour) == 12 ||
                    Integer.parseInt(currentAlarmHour) == 0) ? 12 : Integer.parseInt(currentAlarmHour) % 12, Integer.parseInt(currentAlarmMin));
            currAlarmTxt.setText("Alarm set at "+ timeFormatted + " "+indicator);
        }

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(AlarmNew.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        boolean isPM = (hourOfDay >= 12);
                        startTime.setText(String.format("%02d:%02d", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute));
//                        startTime.setText(String.format(format, hourOfDay) + ":" +String.format(format, minute));
                        if(isPM){
                            startTimeIndicator.setText("PM");
                            startTimeAM_PM[0] = "PM";
                        }else {
                            startTimeIndicator.setText("AM");
                            startTimeAM_PM[0] = "AM";
                        }
                        startAlarmHour[0] = hourOfDay;
                        startAlarmMinute[0] = minute;
                    }
                }, hour, minute, false );
                timePickerDialog.setTitle("Select start time");
                timePickerDialog.show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(AlarmNew.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        boolean isPM = (hourOfDay >= 12);
                        endTime.setText(String.format("%02d:%02d", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute));
                        if(isPM){
                            endTimeIndicator.setText("PM");
                            endTimeAM_PM[0] = "PM";
                        }else {
                            endTimeIndicator.setText("AM");
                            endTimeAM_PM[0] = "AM";
                        }
//                        endTime.setText(String.format(format, hourOfDay) + ":" +String.format(format, minute));
                        endAlarmHour[0] = hourOfDay;
                        endAlarmMinute[0] = minute;
                    }
                }, hour, minute, false );
                timePickerDialog.setTitle("Select end time");
                timePickerDialog.show();
            }
        });

        ArrayAdapter intervalAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, intervals);
        intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alarmIntervalSpinner.setAdapter(intervalAdapter);

        alarmIntervalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Selectedinterval[0] = Integer.parseInt(intervals[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Selectedinterval[0] = 1;
            }
        });


        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Date dat = new Date();
                Calendar cal_alarm = Calendar.getInstance();
                Calendar cal_now = Calendar.getInstance();
                cal_now.setTime(dat);
                cal_alarm.setTime(dat);
                cal_alarm.set(Calendar.HOUR_OF_DAY, startAlarmHour[0]);
                cal_alarm.set(Calendar.MINUTE,startAlarmMinute[0]);
                cal_alarm.set(Calendar.SECOND,0);
                if(cal_alarm.before(cal_now)){
                    cal_alarm.add(Calendar.DATE,1);
                }
//                Intent intent = new Intent(AlarmNew.this, AlarmReceiver.class);
//                pendingIntent[0] = PendingIntent.getBroadcast(AlarmNew.this, 0, intent, 0);
//                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis() ,pendingIntent[0]);

                List<String> repeatingDays = widget.getSelectedDaysText();
                for(int i =0;i<repeatingDays.size();i++){
                    Log.i("dayss", String.valueOf(repeatingDays.get(i)));
                    switch (repeatingDays.get(i)){
                        case "Sunday":
                            editor.putString("sunday","0");
                        case "Monday":
                            editor.putString("monday", "0");
                        case "Tuesday":
                            editor.putString("tuesday", "0");
                        case "Wednesday":
                            editor.putString("wednesday", "0");
                        case "Thursday":
                            editor.putString("thursday", "0");
                        case "Friday":
                            editor.putString("friday", "0");
                        case "Saturday":
                            editor.putString("saturday", "0");
                    }
                }


                editor.putString("currentAlarm", String.valueOf(startAlarmHour[0]));
                editor.putString("firstAlarmHour", String.valueOf(startAlarmHour[0]));
                editor.putString("startAlarm", String.valueOf(startAlarmHour[0]));
                editor.putString("endAlarm", String.valueOf(endAlarmHour[0]));
                editor.putString("endAlarmMin", String.valueOf(endAlarmMinute[0]));
                editor.putString("currentAlarmMin", String.valueOf(startAlarmMinute[0]));
                editor.putString("firstAlarmMin", String.valueOf(startAlarmMinute[0]));
                editor.putString("alarmInterval", String.valueOf(Selectedinterval[0]));
                editor.putString("startTimeIndicator", String.valueOf(startTimeAM_PM[0]));
                editor.putString("endTimeIndicator", String.valueOf(endTimeAM_PM[0]));

                editor.apply();

                if(String.valueOf(startAlarmHour[0])!= null && String.valueOf(startAlarmMinute[0]) != null){
                    boolean isPM = (startAlarmHour[0] >= 12);
                    String indicator = "AM";
                    if(isPM){
                        indicator = "PM";
                    }else {
                        indicator = "AM";
                    }
                    String timeFormatted = String.format("%02d:%02d", (startAlarmHour[0] == 12 ||
                            startAlarmHour[0] == 0) ? 12 : startAlarmHour[0] % 12, startAlarmMinute[0]);
                    currAlarmTxt.setText("Alarm set at "+ timeFormatted + " "+indicator);

                    String toastMessage = "Alarm set at " + timeFormatted + " "+indicator;
                    Toast.makeText(AlarmNew.this, toastMessage, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public static class AlarmReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

            NotificationUtils notificationUtils = new NotificationUtils(context);
            Notification notification = notificationUtils.getNotificationBuilder().build();
            notificationUtils.getManager().notify(150, notification);
        }
    }
}