package pkg.what.a_0.domain.core

class OsSafeGuard {
    /** @desc this should rarely be touched, if so must be done with caution, and on a thread */
    fun killer(){
        val pid = android.os.Process.myPid()
        android.os.Process.killProcess(pid)
    }
}