package pkg.what.a_0.domain.core

import kotlin.system.exitProcess

/** @desc anything in this should rarely be touched
 * , if so must be done with caution and possibly on a different thread
 * , note that this is not aware of any lifecycle, so leaks can happen
 * , therefore be mindful it is in the realm of processes  */
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
    fun forcefulKill(){
        val pid = Runtime.getRuntime().exec("pidof pkg.what.pq")
        println(pid)
        val killer = Runtime.getRuntime().exec("kill $pid")
        println(killer)
        val app = Runtime.getRuntime().exec("am force-stop pkg.what.pq")
        println(app)
    }
}