package com.godapp.godapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.godapp.godapp.model.CellClickListener
import com.godapp.godapp.model.DataModel

class MainActivity : AppCompatActivity(),CellClickListener {


    private lateinit var  photoAdapter: PhotoAdapter
    private var dataList = mutableListOf<DataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)
        createNotificationChannnel();
        recyclerview.layoutManager = GridLayoutManager(applicationContext,2)
        photoAdapter = PhotoAdapter(applicationContext,this)
        recyclerview.adapter = photoAdapter

        //add data
        dataList.add(DataModel("Life", baseContext.getString(R.string.life_header_tamil),R.drawable.bg_1))
        dataList.add(DataModel("Photos",baseContext.getString(R.string.tamil_photos),R.drawable.bg_1))
        dataList.add(DataModel("Music",baseContext.getString(R.string.tamil_music),R.drawable.bg_1))
        dataList.add(DataModel("Alarm",baseContext.getString(R.string.tamil_alarm),R.drawable.bg_1))
        dataList.add(DataModel("Teachings",baseContext.getString(R.string.teaching_header_tamil),R.drawable.bg_1))
        dataList.add(DataModel("Dharma",baseContext.getString(R.string.dharma_header_tamil),R.drawable.bg_1))
//        dataList.add(DataModel("Initiation",baseContext.getString(R.string.initiation_header_tamil),R.drawable.bg_1))
        dataList.add(DataModel("Dharshan",baseContext.getString(R.string.dharshan_header_tamil),R.drawable.bg_1))
        photoAdapter.setDataList(dataList)
    }

    private fun createNotificationChannnel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "godappChannelId",
                "godappChannelId",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onCellClickListener(data:String) {
 //       Toast.makeText(this,data, Toast.LENGTH_SHORT).show()
        when (data) {
            "Life" -> {
                val intent = Intent(this@MainActivity, LifeActivity::class.java)
                startActivity(intent)
            }
            "Photos" -> {
                val intent = Intent(this@MainActivity, GalleryActivity::class.java)
                startActivity(intent)
            }
            "Music" -> {
                val intent = Intent(this@MainActivity, MusicActivity::class.java)
                startActivity(intent)
            }
            "Alarm" -> {
                val intent = Intent(this@MainActivity, AlarmNew::class.java)
                startActivity(intent)
            }
            "Teachings" -> {
                val intent = Intent(this@MainActivity, TeachingActivity::class.java)
                startActivity(intent)
            }
            "Dharma" -> {
                val intent = Intent(this@MainActivity, DharmaActivity::class.java)
                startActivity(intent)
            }
//            "Initiation" -> {
//                val intent = Intent(this@MainActivity, InitiationActivity::class.java)
//                startActivity(intent)
//            }
            "Dharshan" -> {
                val intent = Intent(this@MainActivity, DharshanActivity::class.java)
                startActivity(intent)
            }
        }
    }


}