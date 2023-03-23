package com.sklim.service.listener

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import java.text.SimpleDateFormat
import java.util.*

interface StepListener : SensorEventListener {
    companion object {
        private var mLimit = 10f
        private var mLastValues = FloatArray(3 * 2)
        var mScale = FloatArray(2)
        var mYOffset = 0f

        private var mLastDirections = FloatArray(3 * 2)
        private var mLastExtremes = arrayOf(FloatArray(3 * 2), FloatArray(3 * 2))
        private var mLastDiff = FloatArray(3 * 2)
        private var mLastMatch = -1

        var step = 0
        var offset = 0
        var stepDate = ""
    }

    fun step(step: Int, offset: Int)

    private fun calculate(value: Int) {
        val currentDate = SimpleDateFormat("").format(Date())
        if (stepDate != currentDate) {
            step = 0
            stepDate = currentDate
        }
        val lastStep: Int = value - offset
        if (lastStep > 300) {
            step += 1
        } else if (lastStep >= 0) {
            step += lastStep
        }
        offset = value

        step(step, offset)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return
        synchronized(this) {
            when (event.sensor.type) {
                Sensor.TYPE_STEP_COUNTER -> {
                    calculate(event.values[0].toInt())
                }
                Sensor.TYPE_ACCELEROMETER -> {
                    var vSum = 0f
                    for (i in 0..2) {
                        val v = mYOffset + event.values[i] * mScale[1]
                        vSum += v
                    }
                    val k = 0
                    val v = vSum / 3
                    val direction =
                        (if (v > mLastValues[k]) 1 else if (v < mLastValues[k]) -1 else 0).toFloat()
                    if (direction == -mLastDirections[k]) {
                        // Direction changed

                        // Direction changed
                        val extType = if (direction > 0) 0 else 1 // minumum or maximum?

                        mLastExtremes[extType][k] = mLastValues[k]
                        val diff =
                            Math.abs(mLastExtremes[extType][k] - mLastExtremes[1 - extType][k])
                        if (diff > mLimit) {
                            val isAlmostAsLargeAsPrevious = diff > mLastDiff[k] * 2 / 3
                            val isPreviousLargeEnough = mLastDiff[k] > diff / 3
                            val isNotContra = mLastMatch != 1 - extType
                            if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra) {
                                mLastMatch = extType
                                calculate(step++)
                            } else {
                                mLastMatch = -1
                            }
                        }
                        mLastDiff[k] = diff
                    }
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
