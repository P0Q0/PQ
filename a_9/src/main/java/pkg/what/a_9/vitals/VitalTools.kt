package pkg.what.a_9.vitals

import android.os.StrictMode

class VitalTools {
    init {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
            .detectDiskWrites()
            .detectDiskReads()
            .detectNetwork()
            .penaltyLog()
            .build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .penaltyDeath()
            .build())
    }
}