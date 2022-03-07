package pkg.what.a_9.vitals

import java.util.logging.Logger

interface Vital {

    /** @desc object used to log messages to a specific entity
     * , this is an android native api which is built directly for io: logcat
     * @reference android.util.Log */
    val log: Int

    /** @desc object used to log messages to a specific entity
     * , this is a java native which supports io: consoles, files, os logs, and more
     * @reference java.util.logging.Logger */
    val logger: Logger
}