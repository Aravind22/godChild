package com.godapp.godapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DharshanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dharshan)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = baseContext.getString(R.string.dharshan_header_english)
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}