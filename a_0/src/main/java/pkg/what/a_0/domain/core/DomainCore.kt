package pkg.what.a_0.domain.core

import android.app.Application
import android.util.Log
import pkg.what.a_0.domain.core.constant.ConstantsPQ

class DomainCore : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(ConstantsPQ.LOG_INFO_TAG, " $javaClass : onCreate() : Application")
    }
    override fun onLowMemory() {
        super.onLowMemory()
        Log.d(ConstantsPQ.LOG_INFO_TAG, " $javaClass : onLowMemory() : Application")
    }
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.d(ConstantsPQ.LOG_INFO_TAG, " $javaClass : onTrimMemory($level) : Application")
    }
}