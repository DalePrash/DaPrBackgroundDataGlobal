package com.example.daprbackgrounddataglobal.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.daprbackgrounddataglobal.R
import com.example.daprbackgrounddataglobal.backservice.MusicBackgroundService


class MusicActivity : AppCompatActivity() {

    var cnl_id = "cnd_id"
    var cnl_name = "cnl_name"


    val buttonStart : Button by lazy {
        findViewById(R.id.startButton) as Button
    }
    val buttonStop : Button by lazy {
        findViewById(R.id.stopButton) as Button
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        init()
    }

    fun init() {
        buttonStart.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                //notifyUI()
                val myService = Intent(this@MusicActivity, MusicBackgroundService::class.java)

                   startService(myService)
            }
        })
        buttonStop.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val myService = Intent(this@MusicActivity, MusicBackgroundService::class.java)
                stopService(myService)
            }
        })
    }








        fun notifyUI() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                val z =
                    NotificationChannel(cnl_id, cnl_name, NotificationManager.IMPORTANCE_DEFAULT)


                val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

                manager.createNotificationChannel(z)


                val x = NotificationManagerCompat.from(this)

                val y: Notification = NotificationCompat.Builder(this, cnl_id)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Cake").setContentText("Pastry")
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build()


                x.notify(1, y)

            }
        }

    }


