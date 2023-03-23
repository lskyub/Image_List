package com.sklim.service

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import com.sklim.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltWorker
class PopupWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    private val windowManager by lazy {
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    private val inflater by lazy {
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    private lateinit var popup: View

    private val params = WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        },
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
        PixelFormat.TRANSLUCENT
    ).apply {
        gravity = Gravity.CENTER
    }

//    override fun startWork(): ListenableFuture<Result> {
//        Looper.prepare()
//        return CallbackToFutureAdapter.getFuture { completer ->
//            CoroutineScope(Default).launch {
//                TDialog(applicationContext).show()
//            }
//        }
//    }

    override suspend fun doWork(): Result {
        CoroutineScope(Dispatchers.Main).launch {
            //화면이 떠있는 상태에서만 동작
//            if (::popup.isInitialized) {
//            } else {
//                popup = inflater.inflate(R.layout.popup, null)
//            }
//            windowManager.addView(popup, params)
//            Toast.makeText(context, "테스트", Toast.LENGTH_SHORT).show()
        }
        return Result.success()
    }
//    override fun doWork(): Result {
//        Looper.prepare()
//        if (::popup.isInitialized) {
//        } else {
//            popup = inflater.inflate(R.layout.popup, null)
//        }
//        windowManager.addView(popup, params)
//        Toast.makeText(context, "테스트", Toast.LENGTH_SHORT).show()
//        return Result.success()
//    }
}