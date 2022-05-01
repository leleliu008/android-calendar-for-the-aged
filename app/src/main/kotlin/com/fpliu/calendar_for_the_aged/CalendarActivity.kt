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
import kotlinx.android.synthetic.main.calendar_activity.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class CalendarActivity : BaseActivity() {

    companion object {
        private val TAG = CalendarActivity::class.java.simpleName
    }

    private val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.CHINA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addContentView(R.layout.calendar_activity)
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
                yearTv.text = String.format("%s年", list[0])
                day_solarTv.text = String.format("%s月%s日", list[1], list[2])
                day_lunarTv.text = list[4]
                weekTv.text = list[3]
                tagTv.text = String.format("%s%s", list[5], list[6])
            }, { Logger.e(TAG, "updateDate()", it) })
    }

    private fun updateTime() {
        Observable
            .interval(0, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(disposeOnStop())
            .subscribe({
                timeTv.text = simpleDateFormat.format(System.currentTimeMillis())
            }, { Logger.e(TAG, "updateTime()", it) })
    }

    private fun disposeOnStop() = AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_STOP)
}
