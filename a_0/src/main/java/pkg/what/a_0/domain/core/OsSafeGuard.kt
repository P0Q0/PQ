package pkg.what.a_0.domain.core

import kotlin.system.exitProcess

/** @desc anything in this should rarely be touched, if so must be done with caution, and on a thread,
 * note that this is not aware of any lifecycle, it's strictly in the realm of processes  */
class OsSafeGuard {
    fun pidKill(){
        val pid = android.os.Process.myPid()
        android.os.Process.killProcess(pid)
    }
    fun processKill(){
        exitProcess(0)
    }
    fun jvmKill(){
        Runtime.getRuntime().exit(0)
    }
    fun gracefulGcKill(){
        System.runFinalization()
    }
}