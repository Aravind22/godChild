package com.godapp.godapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LifeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = baseContext.getString(R.string.life_header_english)
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)//#2B547E(text color code)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}