package com.fpliu.calendar_for_the_aged

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import com.fpliu.newton.log.Logger
import com.fpliu.newton.ui.base.BaseActivity
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import com.fpliu.calendar_for_the_aged.databinding.CalendarActivityBinding

class CalendarActivity : BaseActivity() {

    companion object {
        private val TAG = CalendarActivity::class.java.simpleName
    }

    private val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.CHINA)

    private lateinit var binding: CalendarActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CalendarActivityBinding.inflate(layoutInflater)
        addContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        updateDate()
        updateTime()
    }

    private fun updateDate() {
        Observable
            .interval(0, 1, TimeUnit.MINUTES)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(disposeOnStop())
            .subscribe({
                val date = Calendar.getDate()
                val list = date.split("|")
                binding.yearTv.text = String.format("%s年", list[0])
                binding.daySolarTv.text = String.format("%s月%s日", list[1], list[2])
                binding.dayLunarTv.text = list[4]
                binding.weekTv.text = list[3]
                binding.tagTv.text = String.format("%s%s", list[5], list[6])
            }, { Logger.e(TAG, "updateDate()", it) })
    }

    private fun updateTime() {
        Observable
            .interval(0, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(disposeOnStop())
            .subscribe({
                binding.timeTv.text = simpleDateFormat.format(System.currentTimeMillis())
            }, { Logger.e(TAG, "updateTime()", it) })
    }

    private fun disposeOnStop() = AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_STOP)
}
