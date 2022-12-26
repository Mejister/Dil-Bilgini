package com.kelimebilmece

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kelimebilmece.R
import com.example.kelimebilmece.databinding.FragmentEntranceScreenBinding
import com.kelimebilmece.entrance.EntranceScreenFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
   private var mMediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      val preferences= getSharedPreferences("GeneralSetting", Context.MODE_PRIVATE)
        preferences.getBoolean("sound",true).let {
            if (it) playSound()
        }
    }



    fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.chillmusic)
            mMediaPlayer?.isLooping = true
            mMediaPlayer?.start()
        } else mMediaPlayer?.start()
    }



    // 3. Stops playback
    fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer?.stop()
            mMediaPlayer?.release()
            mMediaPlayer = null
        }
    }

    // 4. Destroys the MediaPlayer instance when the app is closed
    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer?.release()
            mMediaPlayer = null
        }

    }
}