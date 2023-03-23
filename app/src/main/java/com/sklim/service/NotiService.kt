package com.sklim.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.sklim.App
import com.sklim.R
import com.sklim.service.listener.StepListener
import java.util.UUID
import java.util.concurrent.TimeUnit

class NotiService : Service(), StepListener {

    private val channelID = "com.sklim.service"

    private val channelName = "서비스"

    private var notiBuild: NotificationCompat.Builder? = null

    private var isScreenOnOff = true

    private lateinit var worker: OneTimeWorkRequest

    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private var serviceReveiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.i("sgim", "screenOnOff ${p1?.action}")
            when (p1?.action) {
                Intent.ACTION_SCREEN_ON -> {
                    isScreenOnOff = true
                }
                Intent.ACTION_SCREEN_OFF -> {
                    isScreenOnOff = false
                }
                Intent.ACTION_USER_PRESENT -> {
                    if (!::worker.isInitialized) {
                        worker = OneTimeWorkRequest.Builder(PopupWorker::class.java).build()
                    }
                    WorkManager.getInstance(this@NotiService).enqueueUniqueWork(
                        "popup",
                        ExistingWorkPolicy.REPLACE,
                        worker
                    )
                }
                "com.sklim.service.UPDATE" -> {

                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        App.service = this

        startForeground(100, notiBuild.get().build())

        registerSensor()

        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        filter.addAction(Intent.ACTION_USER_PRESENT)
        filter.addAction("com.sklim.service.UPDATE")
        registerReceiver(serviceReveiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterSensor()

//        if (::worker.isInitialized) {
//            WorkManager.getInstance(this).cancelWorkById(worker.id)
//        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun step(step: Int, offset: Int) {
        Log.i("sgim", "$step")
    }

    private fun NotificationCompat.Builder?.get(): NotificationCompat.Builder {
        return (this ?: if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan =
                NotificationChannel(
                    channelID,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    lightColor = Color.BLUE
                    lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                    setShowBadge(false)
                    enableLights(false)
                    enableVibration(false)
                    setSound(null, null)
                    try {
                        vibrationPattern = null
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(chan)
            NotificationCompat.Builder(this@NotiService, channelID)
        } else {
            NotificationCompat.Builder(this@NotiService)
        }).apply {
            setSmallIcon(R.mipmap.ic_launcher)
            val appName = resources.getString(R.string.app_name)
            setContentTitle(appName)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                setContentText("$appName 서비스")
            }
            setOnlyAlertOnce(true)
            setOngoing(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setCategory(Notification.CATEGORY_SERVICE)
                setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            }
            setShowWhen(false)
            setVibrate(null)
            setSound(null)
        }
    }

    private fun registerSensor() {
        try {
            sensorManager.let {
                try {
                    val sensor = it.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
                    it.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
                    return@let
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                try {
                    StepListener.apply {
                        val h = 480
                        mYOffset = h * 0.5f
                        mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)))
                        mScale[1] = -(h * 0.5f * (1.0f / SensorManager.MAGNETIC_FIELD_EARTH_MAX))
                    }
                    val sensor = it.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
                    it.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
                    return@let
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
        }
    }

    private fun unregisterSensor() {
        try {
            sensorManager.unregisterListener(this)
        } catch (e: Exception) {
        }
    }
}
