package com.application.customstopwatch.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.application.customstopwatch.R
import com.application.customstopwatch.databinding.ViewCombineBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val startStopButton: Button
    private val resetButton: Button
    private val timeTextView: TextView
    private var timeListener = mutableSetOf<(TimeState) -> Unit>()
    private val binding = ViewCombineBinding.inflate(LayoutInflater.from(context))
    private var isStart = false
    private val scope = CoroutineScope(Dispatchers.Main)
    private var job: Job = Job()

    private var timerSeconds: Long = 0
        set(value) {
            field = value
            var hours = value / 3600
            hours = if (hours > 12) hours - 12 else hours
            val minutes = (value % 3600) / 60
            val seconds = value % 60
            timeTextView.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            timeListener.forEach { it(TimeState(currentTime(), isStart)) }
        }

    init {
        addView(binding.root)
        startStopButton = binding.buttonStart
        resetButton = binding.buttonReset
        timeTextView = binding.textTime

        startStopButton.setOnClickListener {
            if (!isStart) {
                start()
                startStopButton.text = resources.getText(R.string.stop)
            } else {
                stop()
                startStopButton.text = resources.getText(R.string.start)
            }
        }

        resetButton.setOnClickListener {
            reset()
        }

    }

    fun addUpdateListener(listener: (TimeState) -> Unit) {
        timeListener.add(listener)
        val timeState = TimeState(currentTime(), isStart)
        listener(timeState)
    }

    fun removeUpdateListener(listener: (TimeState) -> Unit) {
        timeListener.remove(listener)
    }

    private fun reset() {
        timerSeconds = 0
    }

    private fun start() {
        isStart = true
        job = scope.launch {
            while (isStart) {
                timerSeconds++
                if (timerSeconds % 43200 == 0L) timerSeconds = 0
                delay(1000)
            }
        }
    }

    private fun stop() {
        isStart = false
        job.cancel()
    }

    private fun currentTime(): Long = timerSeconds
}