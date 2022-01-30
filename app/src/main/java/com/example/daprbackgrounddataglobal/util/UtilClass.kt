package com.example.daprbackgrounddataglobal.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.daprbackgrounddataglobal.R

class UtilClass : AppCompatActivity() {

    companion object{
        public  fun notifyUI(cont: Context) {
            val cnl_id = "cnd_id"
            val cnl_name = "cnl_name"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val z =
                    NotificationChannel(cnl_id, cnl_name, NotificationManager.IMPORTANCE_DEFAULT)
                val manager = cont.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(z)
                val x = NotificationManagerCompat.from(cont)
                val y: Notification = NotificationCompat.Builder(cont, cnl_id)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("MUSIC").setContentText("Theme instrumental Started Playing")
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build()
                x.notify(1, y)
            }
        }
    }

}