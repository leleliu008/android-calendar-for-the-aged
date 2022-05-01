package com.fpliu.calendar_for_the_aged

import android.text.TextUtils
import com.fpliu.newton.log.Logger

object ErrorHandler {

    private val TAG = ErrorHandler::class.java.simpleName

    fun onError(e: Throwable, extraInfo: String? = null) {
        Logger.e(TAG, "onError() extraInfo = $extraInfo", e)
        StringBuilder().apply {
            append(Logger.getExceptionTrace(e))
            append('\n')
//            append("公网IP：${IPManager.ipInfo} record when ${IPManager.time}")
            append('\n')
//            append("网络配置：${Environment.getInstance().networkInfo}")
            if (!TextUtils.isEmpty(extraInfo)) {
                append('\n')
                append(extraInfo)
            }
        }.let {
//            MobclickAgent.reportError(appContext, it.toString())
        }
    }
}