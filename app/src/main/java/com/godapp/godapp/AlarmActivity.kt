package com.godapp.godapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_alarm.*
import java.util.*

class AlarmActivity : AppCompatActivity() {
    lateinit var btnSetAlarm: Button
    lateinit var timePicker: TimePicker
    private val sharedPrefFile = "KeyShared"
    private lateinit var sharedPreferences: SharedPreferences
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.godapp.godapp.R.layout.activity_alarm)
        title = "KotlinApp"
        timePicker = findViewById(com.godapp.godapp.R.id.timePicker)
      sharedPreferences= this.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
      val sharedNameValue = sharedPreferences.getString("alarm_key","Not Set Yet")
        if(sharedNameValue!=="Not Set Yet") {

            if (sharedNameValue != null) {
                Log.i("sharedMessage", sharedNameValue)
            }

            if (timePicker.hour > Integer.valueOf(sharedNameValue?.substring(0, 2))||timePicker.hour == Integer.valueOf(sharedNameValue?.substring(0, 2))) {
                if(timePicker.minute > Integer.valueOf(sharedNameValue?.substring(3, 4))){
                alarmTime.setText("Alarm is been set for - Not Set Yet")
                }
                    else{
                    alarmTime.setText("Alarm is been set for - " + sharedNameValue)
                    }
            } else {
                alarmTime.setText("Alarm is been set for - " + sharedNameValue)
            }
        }
        else{
            alarmTime.setText("Alarm is been set for - Not Set Yet")
        }
 //       alarmTime.setText("Alarm is been set for - "+sharedNameValue)

        btnSetAlarm = findViewById(com.godapp.godapp.R.id.buttonAlarm)
        btnSetAlarm.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            if (Build.VERSION.SDK_INT >= 23) {
                calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    timePicker.hour,
                    timePicker.minute,
                    0
                )
            } else {
                calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    timePicker.currentHour,
                    timePicker.currentMinute, 0
                )
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setAlarm(calendar.timeInMillis,timePicker.hour.toString(),timePicker.minute.toString())
            }//calendar.timeInMillis
        }
        val actionbar = supportActionBar
        actionbar!!.title = baseContext.getString(com.godapp.godapp.R.string.english_alarm)
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun setAlarm(timeInMillis: Long,hour:String,min:String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, com.godapp.godapp.AlarmActivity.AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show()
        val name:String = hour.toString()+":"+min.toString()
        val id:Int = 1
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putInt("id_key",id)
        editor.putString("alarm_key",name)
        editor.apply()
        val sharedNameValue = sharedPreferences.getString("alarm_key","defaultname")
        alarmTime.setText("Alarm is been set for - "+sharedNameValue)
    }

    class AlarmReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
            val notificationUtils = com.godapp.godapp.NotificationUtils(context)
            val notification = notificationUtils.getNotificationBuilder().build()
            notificationUtils.getManager().notify(150, notification)
        }
    }
}