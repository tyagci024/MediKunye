package com.example.gitdeneme

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat

import java.util.Calendar

class AlarmReceiver() : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmReceiver", "onReceive called")

        val reminderTitle = intent?.getStringExtra("REMINDER_TITLE")
        val alarmId = intent?.getIntExtra("ALARM_ID", 0)
        val endDate = intent?.getLongExtra("END_DATE", 0L)
        val reminderDesc = intent?.getStringExtra("DESC")
        val reminderType = intent?.getStringExtra("REMINDER_TYPE")

        val currentDate = Calendar.getInstance().timeInMillis

        if (reminderType == "DRUG") {
            if (currentDate >= endDate!!) {
                cancelAlarm(context!!, alarmId!!)
            }

            val notification = createNotification(context!!, reminderTitle!!, reminderDesc!!)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(alarmId!!, notification)

            Log.d("AlarmReceiver", "Bildirim oluşturuldu: ID - $alarmId, Başlık - $reminderTitle, Açıklama - $reminderDesc")
        } else if (reminderType == "CHECK_UP") {
            val checkUpTitle = intent?.getStringExtra("REMINDER_TITLE")
            val hospital = intent?.getStringExtra("DESC")

            val checkUpNotification = createCheckUpNotification(context!!, checkUpTitle!!,hospital!!)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(alarmId!!, checkUpNotification)
            Log.d("AlarmReceiver", "onReceive called")
        }
    }

    private fun cancelAlarm(context: Context, alarmId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
    }



    private fun createNotification(context: Context, reminderTitle: String, reminderDesc: String): Notification {
        val channelId = "YOUR_CHANNEL_ID"
        val contentTitle = "$reminderTitle içme zamanı"
        val contentText = reminderDesc
        val icon = R.drawable.icon // Bildirimde gösterilecek ikon

        return NotificationCompat.Builder(context, channelId).apply {
            setContentTitle(contentTitle)
            setContentText(contentText)
            setSmallIcon(icon)
            setAutoCancel(true)
        }.build()
    }

    private fun createCheckUpNotification(context: Context, checkUpTitle: String, hospital:String): Notification {
        val channelId = "YOUR_CHANNEL_ID"
        val contentTitle = "Check Up Reminder"
        val contentText = "3 saat sonra Dr. $checkUpTitle ile $hospital'da randevunuz vardır"
        val icon = R.drawable.icon // Bildirimde gösterilecek ikon
        return NotificationCompat.Builder(context, channelId).apply {
            setContentTitle(contentTitle)
            setContentText(contentText)
            setSmallIcon(icon)
            setAutoCancel(true)
        }.build()
    }
}