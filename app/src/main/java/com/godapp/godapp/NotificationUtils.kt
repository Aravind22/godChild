package com.godapp.godapp

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.godapp.godapp.BaseActivity.ringToneVar
import java.util.*
import kotlin.concurrent.timerTask


class  NotificationUtils(base: Context) : ContextWrapper(base) {

    val MYCHANNEL_ID = "App Alert Notification ID"
    val MYCHANNEL_NAME = "App Alert Notification"

    private var manager: NotificationManager? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels()
        }
    }




    // Create channel for Android version 26+
    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannels() {
        val channel = NotificationChannel(MYCHANNEL_ID, MYCHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        channel.enableVibration(true)

        getManager().createNotificationChannel(channel)
    }

    // Get Manager
    fun getManager() : NotificationManager {
        if (manager == null) manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return manager as NotificationManager
    }

    @TargetApi(Build.VERSION_CODES.P)
    fun getNotificationBuilder(): NotificationCompat.Builder {
        val intent = Intent(this, com.godapp.godapp.RingActivity::class.java);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        val intent2 = Intent(this, com.godapp.godapp.NotificationDismissal::class.java);
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val pendingIntent2 = PendingIntent.getBroadcast(this, 0, intent2, 0);
        val notification: Uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.guru_nama)
        ringToneVar = RingtoneManager.getRingtone(applicationContext, notification);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.P){
            Timer().scheduleAtFixedRate(timerTask {
                ringToneVar.play()
            },2,2)

        }else{
            ringToneVar.setLooping(true);
            ringToneVar.play()
        }

        return NotificationCompat.Builder(applicationContext, MYCHANNEL_ID)
            .setContentTitle("Alarm!")
            .setContentText("Godapp Alarm")
            .setSmallIcon(R.drawable.ic_baseline_play_arrow_24)
            .setColor(Color.YELLOW)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_baseline_stop_24, "Dismiss", pendingIntent2)
            .setDeleteIntent(pendingIntent2)
    }

    fun stopAlarm(): Boolean {
        Log.d("ALARM STOP", "Stopping...")
        val pendingIntent: PendingIntent;
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val dat = Date()
        val cal_alarm = Calendar.getInstance()
        val cal_now = Calendar.getInstance()
        cal_now.time = dat
        cal_alarm.time = dat
        cal_alarm[Calendar.SECOND] = 0

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("myAlarm", MODE_PRIVATE);
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val currentAlarm = sharedPreferences.getString("currentAlarm", null);
        val currentAlarmMin = sharedPreferences.getString("currentAlarmMin", null);
        val alarmInterval = sharedPreferences.getString("alarmInterval", null);
        val endAlarm = sharedPreferences.getString("endAlarm", null);
        val endAlarmMin = sharedPreferences.getString("endAlarmMin", null);

        val intent = Intent(this, com.godapp.godapp.AlarmNew.AlarmReceiver::class.java);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmManager.cancel(pendingIntent);

        if(currentAlarm != null && alarmInterval != null && currentAlarmMin != null){
            val setNextAlarm = Integer.parseInt(currentAlarm) + Integer.parseInt(alarmInterval);
            Log.d("CURRENT ALARM", currentAlarm)
            Log.d("NEXT ALARM", setNextAlarm.toString())
            Log.d("END ALARM", endAlarm.toString());
            if(endAlarm != null && setNextAlarm < Integer.parseInt(endAlarm)){

                editor.putString("currentAlarm", setNextAlarm.toString())
//                editor.putString("endAlarm", endAlarm)
//                editor.putString("endAlarmMin", endAlarmMin)
                editor.putString("currentAlarmMin", currentAlarmMin)

                editor.apply();
                cal_alarm[Calendar.HOUR_OF_DAY] = setNextAlarm
                cal_alarm[Calendar.MINUTE] = Integer.parseInt(currentAlarmMin)
                if (cal_alarm.before(cal_now)) {
                    cal_alarm.add(Calendar.DATE, 1)
                }
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    cal_alarm.timeInMillis,
                    pendingIntent
                )
                val toastMessage = "Next Alarm set at " + setNextAlarm.toString() + ":" + currentAlarmMin.toString()
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
            }
            if(endAlarm != null && setNextAlarm == Integer.parseInt(endAlarm) && endAlarmMin != null){
                Log.d("END ALARM MIN", endAlarmMin)
                Log.d("CURRENT ALARM MIN", currentAlarmMin)
                if(Integer.parseInt(endAlarmMin) >= Integer.parseInt(currentAlarmMin)){
                    editor.putString("currentAlarm", setNextAlarm.toString())
//                    editor.putString("endAlarm", endAlarm)
//                    editor.putString("endAlarmMin", endAlarmMin)
                    editor.putString("currentAlarmMin", currentAlarmMin)
                    editor.apply();
                    cal_alarm[Calendar.HOUR_OF_DAY] = setNextAlarm
                    cal_alarm[Calendar.MINUTE] = Integer.parseInt(currentAlarmMin)
                    if (cal_alarm.before(cal_now)) {
                        cal_alarm.add(Calendar.DATE, 1)
                    }
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        cal_alarm.timeInMillis,
                        pendingIntent
                    )
                    val toastMessage = "Next Alarm set at " + setNextAlarm.toString() + ":" + currentAlarmMin.toString()
                    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
        ringToneVar.stop();
        return true;
    }

}