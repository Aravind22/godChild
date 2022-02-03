package com.godapp.godapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_initiation.*
import kotlinx.android.synthetic.main.activity_teaching.bodyTamText

class InitiationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initiation)

        bodyTamText.setOnClickListener{
            val intent = Intent(this, MusicActivity::class.java)
            startActivity(intent)
        }

        bodyEngText.setOnClickListener{
            val intent = Intent(this, MusicActivity::class.java)
            startActivity(intent)
        }

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = baseContext.getString(R.string.initiation_header_english)
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}