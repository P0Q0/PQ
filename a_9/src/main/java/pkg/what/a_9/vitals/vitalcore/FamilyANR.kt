package pkg.what.a_9.vitals.vitalcore

import android.content.Context
import pkg.what.a_9.vitals.VitalFamily
import pkg.what.a_9.vitals.VitalType
import java.net.HttpURLConnection
import java.net.URL
import java.util.logging.Logger
import android.util.Log
import java.io.BufferedInputStream
import java.io.File
import java.io.IOException

import android.os.NetworkOnMainThreadException

class FamilyANR(private val ctx: Context) : VitalFamily() {
    override val type: VitalType by lazy { VitalType.CoreGroup(javaClass.name) }
    override var log: Int = Log.d(LOG_CLASS_TAG,LOG_CLASS_MSG)
    override val logger: Logger = Logger.getLogger(FamilyANR::javaClass.name)

    init {
        //anrUiNetworkIo()
        //anrUiDiskIo()
        anrDeadlock()
    }

    /** @throws NetworkOnMainThreadException
     * reason: performs network operation on the main thread */
    private fun anrUiNetworkIo(){
        val url = URL("https://developer.android.com")
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        try {
            val bis = BufferedInputStream(connection.inputStream)
            while (bis.iterator().hasNext()) {
                Log.d(LOG_CLASS_TAG, bis.read().toString())
            }
        } catch (e: NetworkOnMainThreadException){
            e.printStackTrace()
        } finally {
            connection.disconnect()
        }
    }

    /** @throws IOException
     * reason: performs disk operation on the main thread */
    private fun anrUiDiskIo(){
        val path = "NewTextFile.txt"
        val file = File(ctx.filesDir,path)
        try {
            ctx.openFileInput(file.name).use { fis ->
                val reader = fis.bufferedReader()
                for(i in 0 until Int.MAX_VALUE) {
                    reader.lines().map {
                        log = Log.d(LOG_CLASS_TAG, it)
                    }
                }
                reader.close()
            }
            ctx.openFileOutput(file.name,Context.MODE_PRIVATE).use { fos ->
                val writer = fos.bufferedWriter()
                for(i in 0 until Int.MAX_VALUE) {
                    writer.write("operation:writer.write")
                }
                writer.close()
            }
        } catch (e: IOException){
            e.printStackTrace()
        }
    }

    /** @invokes Application Not Responding
     * reason: causes threads to wait on a resource being held up by another thread */
    private fun anrDeadlock(){
        val l1 = Any()
        val l2 = Any()
        val t1 = Thread().apply {
            run {
                synchronized(l1) {
                    msg("...thread#1 will make lock#1 wait...", this)
                    try { Thread.sleep(THREAD_TIME.toLong()) }
                    catch (e: InterruptedException){ e.printStackTrace() }
                    msg("...thread#1 is waiting for lock#2...", this)
                    synchronized (l2) {
                        msg("...thread#1 is holding lock#1 lock#2...", this)
                    }
                }
            }
        }
        val t2 = Thread().apply {
            run {
                synchronized(l1) {
                    msg("...thread#2 will make lock#2 wait...", this)
                    try { Thread.sleep(THREAD_TIME.toLong()) }
                    catch (e: InterruptedException){ e.printStackTrace() }
                    msg("...thread#2 is waiting for lock#1...", this)
                    synchronized (l2) {
                        msg("...thread#2 is holding lock#1 lock#2...", this)
                    }
                }
            }
        }
        t1.join()
        t2.join()
    }

    private fun msg(s: String, t: Thread) =
        this.apply {
            val m = "Thread_Name:${t.name} " +
                    ", Thread_Id:${t.id} " +
                    ", Thread_Alive:${t.isAlive} " +
                    ", Thread_State:${t.state.name} " +
                    ", Thread_Daemon:${t.isDaemon} " +
                    ", Thread_Priority:${t.priority} " +
                    " $s"
            log = Log.d(LOG_CLASS_TAG, m)
        }

    companion object {
        const val LOG_CLASS_TAG = "TAG: FamilyANR"
        const val LOG_CLASS_MSG = "MSG: Initialization"
        const val THREAD_TIME = 10000
    }
}