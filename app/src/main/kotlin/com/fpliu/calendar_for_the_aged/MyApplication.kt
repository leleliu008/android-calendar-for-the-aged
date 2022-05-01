package com.fpliu.calendar_for_the_aged

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextPaint
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.fpliu.newton.crash.CrashHandler
import com.fpliu.newton.font.config.api.applyFontFromConfig
import com.fpliu.newton.log.Logger
import com.fpliu.newton.ui.base.BaseActivity
import com.fpliu.newton.ui.statusbar.StatusBarUtil
import com.fpliu.newton.util.*
import com.jakewharton.rxbinding3.view.clicks
import com.uber.autodispose.autoDisposable
import io.reactivex.plugins.RxJavaPlugins

/**
 * 进程的入口
 * @author 792793182@qq.com 2018-03-24.
 */
class MyApplication : Application(), Application.ActivityLifecycleCallbacks {

    companion object {
        private val TAG = MyApplication::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(this)

        Logger.init(this, "DT_", true)

        CrashHandler.init(this, null)

        if (!BuildConfig.DEBUG) {
            //RxJava的全局异常处理，防止崩溃
            RxJavaPlugins.setErrorHandler { ErrorHandler.onError(it) }
        }

        //应用字体
        try {
            applyFontFromConfig("assets:Alibaba_PuHuiTi_Light.otf")
        } catch (e: Exception) {
            ErrorHandler.onError(e)
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Logger.i(TAG, "onActivityCreated() activity = $activity, savedInstanceState = $savedInstanceState")

        val headHeight = dp2px(48)

        if (activity is BaseActivity) {
            val baseView = activity.contentView.apply {
                setBackgroundColor(Color.WHITE)
            }

            baseView.headBarLayout.apply {
                layoutParams.height = headHeight

                background = object : ColorDrawable(Color.WHITE) {
                    val paint = Paint().apply {
                        isAntiAlias = true
                        isDither = true
                        color = getColorInt(R.color.c_ebebeb)
                    }

                    override fun draw(canvas: Canvas) {
                        super.draw(canvas)
                        canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(), paint)
                    }
                }

                findViewById<TextView>(R.id.base_view_head_title).apply {
                    setTextColorRes(R.color.c_101010)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, getDimension(R.dimen.sp750_30))
                    (paint as TextPaint).isFakeBoldText = true
                }

                ImageView(activity).apply {
                    setImageResource(R.mipmap.ic_back_black)
                    setPadding(30, 0, 70, 0)
                    clicks().autoDisposable(activity.disposeOnDestroy()).subscribe { activity.onLeftBtnClick() }
                }.let { setLeftView(it) }
            }

            baseView.statusBarPlaceHolder.apply {
                setBackgroundColor(Color.WHITE)
                visibility = View.VISIBLE
            }

            activity.toastLayout.apply {
                setBackgroundColorRes(R.color.c_00ddbb)
                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, headHeight).apply {
                    topMargin = getStatusBarHeight()
                }
            }
        }

        StatusBarUtil.setRootViewFitsSystemWindows(activity, true)
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(activity)
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(activity, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(activity, 0x55000000)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        Logger.i(TAG, "onActivityStarted() activity = $activity")
    }

    override fun onActivityResumed(activity: Activity) {
        Logger.i(TAG, "onActivityResumed() activity = $activity")
    }

    override fun onActivityPaused(activity: Activity) {
        Logger.i(TAG, "onActivityPaused() activity = $activity")
    }

    override fun onActivityStopped(activity: Activity) {
        Logger.i(TAG, "onActivityStopped() activity = $activity")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Logger.i(TAG, "onActivitySaveInstanceState() activity = $activity, outState = $outState")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Logger.i(TAG, "onActivityDestroyed() activity = $activity")
    }

    override fun onLowMemory() {
        Logger.i(TAG, "onLowMemory()")
        super.onLowMemory()
//        Glide.get(this).clearMemory()
    }

    override fun onTrimMemory(level: Int) {
        Logger.i(TAG, "onTrimMemory() level = $level")
        super.onTrimMemory(level)
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) { //进入后台的时候会是这个level
//            Glide.get(this).clearMemory()
        }
//        Glide.get(this).trimMemory(level)
    }
}