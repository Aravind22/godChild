package com.godapp.godapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_teaching.*
import android.provider.AlarmClock




class TeachingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teaching)

//        bodySubEngText.setOnClickListener{
////            val intent = Intent(this, AlarmActivity::class.java)
////            startActivity(intent)
//            val i = Intent(AlarmClock.ACTION_SET_ALARM)
//            startActivity(i)
//        }

//        bodyTamSubText.setOnClickListener{
////            val intent = Intent(this, AlarmActivity::class.java)
////            startActivity(intent)
//            val i = Intent(AlarmClock.ACTION_SET_ALARM)
//            startActivity(i)
//        }
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = baseContext.getString(R.string.teaching_header_english)
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}