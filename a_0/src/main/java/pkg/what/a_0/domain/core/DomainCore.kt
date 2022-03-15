package pkg.what.a_0.domain.core

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import android.util.Log
import pkg.what.a_0.domain.core.constant.ConstantsPQ


class DomainCore : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(ConstantsPQ.LOG_INFO_TAG, " $javaClass : onCreate() : Application")

        /** @reference
         * https://wh0.github.io/2020/08/12/closeguard.html
         */
        //try {
        //    Class.forName("dalvik.system.CloseGuard")
        //        .getMethod("setEnabled", Boolean::class.javaPrimitiveType)
        //        .invoke(null, true)
        //} catch (e: ReflectiveOperationException) {
        //    throw RuntimeException(e)
        //}

        /** @reference
         * https://developer.android.com/reference/android/os/StrictMode.html
         */
        //StrictMode.setThreadPolicy(
        //    ThreadPolicy.Builder()
        //        .detectDiskReads()
        //        .detectDiskWrites()
        //        .detectNetwork()
        //        .penaltyLog()
        //        .build()
        //)
        //StrictMode.setVmPolicy(
        //    VmPolicy.Builder()
        //        .detectLeakedClosableObjects()
        //        .penaltyLog()
        //        .build()
        //)
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