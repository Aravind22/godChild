package com.godapp.godapp

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import android.os.Handler
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_music.*

class MusicActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable:Runnable
    private var handler: Handler = Handler()
    private var pause:Boolean = false
    private var switchPausePlay = true
    private var switchStop = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.guru_nama)
        // Start the media player
        playBtn.setOnClickListener{
            switchStop = false
            if(switchPausePlay) {
                switchPausePlay = false
                playBtn.setBackgroundResource(R.drawable.ic_baseline_pause_24)
                if (pause) {
                    mediaPlayer.seekTo(mediaPlayer.currentPosition)
//                mediaPlayer.isLooping=true
                    mediaPlayer.start()
                    pause = false
                    Toast.makeText(this, "media playing", Toast.LENGTH_SHORT).show()
                } else {


                    mediaPlayer.isLooping=true
                    mediaPlayer.start()
                    Toast.makeText(this, "media playing", Toast.LENGTH_SHORT).show()

                }
                initializeSeekBar()
                playBtn.isEnabled = true
                pauseBtn.isEnabled = true
                stopBtn.isEnabled = true

                mediaPlayer.setOnCompletionListener {
//                mediaPlayer = MediaPlayer.create(applicationContext,R.raw.guru_nama)
//                mediaPlayer.start()
                    switchPausePlay = true
                    playBtn.isEnabled = true
                    pauseBtn.isEnabled = false
                    stopBtn.isEnabled = false
                }
            }
            else{
                switchPausePlay = true
                playBtn.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24)
                if(mediaPlayer.isPlaying){
                    mediaPlayer.pause()
                    pause = true
                    playBtn.isEnabled = true
                    pauseBtn.isEnabled = false
                    stopBtn.isEnabled = true
                    Toast.makeText(this,"media pause",Toast.LENGTH_SHORT).show()
                }

            }
        }


        // Pause the media player
        pauseBtn.setOnClickListener {
            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
                pause = true
                playBtn.isEnabled = true
                pauseBtn.isEnabled = false
                stopBtn.isEnabled = true
                Toast.makeText(this,"media pause",Toast.LENGTH_SHORT).show()
            }
        }
        // Stop the media player
        stopBtn.setOnClickListener{
            switchPausePlay = true
            switchStop = true
            playBtn.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24)
            if(mediaPlayer.isPlaying || pause.equals(true)){
                pause = false
                seek_bar.setProgress(0)
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                handler.removeCallbacks(runnable)

                playBtn.isEnabled = true
                pauseBtn.isEnabled = false
                stopBtn.isEnabled = false
                tv_pass.text = ""
                tv_due.text = ""
                Toast.makeText(this,"media stop",Toast.LENGTH_SHORT).show()
            }
        }
        // Seek bar change listener
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (b) {
                    mediaPlayer.seekTo(i * 1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = baseContext.getString(R.string.english_music)
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    // Method to initialize seek bar and audio stats
    private fun initializeSeekBar() {
        seek_bar.max = mediaPlayer.seconds

        runnable = Runnable {
            seek_bar.progress = mediaPlayer.currentSeconds

            tv_pass.text = "${mediaPlayer.currentSeconds} sec"
            val diff = mediaPlayer.seconds - mediaPlayer.currentSeconds
            tv_due.text = "$diff sec"

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }


    override fun onDestroy() {
if(switchStop.equals(false)) {
    switchPausePlay = true
    playBtn.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24)
    if (mediaPlayer.isPlaying || pause.equals(true)) {
        pause = false
        seek_bar.setProgress(0)
        mediaPlayer.stop()
        mediaPlayer.reset()
        mediaPlayer.release()
        handler.removeCallbacks(runnable)

        playBtn.isEnabled = true
        pauseBtn.isEnabled = false
        stopBtn.isEnabled = false
        tv_pass.text = ""
        tv_due.text = ""
        Toast.makeText(this, "media stop", Toast.LENGTH_SHORT).show()
    }
}
        super.onDestroy()
    }

}
// Creating an extension property to get the media player time duration in seconds
val MediaPlayer.seconds:Int
    get() {
        return this.duration / 1000
    }
// Creating an extension property to get media player current position in seconds
val MediaPlayer.currentSeconds:Int
    get() {
        return this.currentPosition/1000
    }