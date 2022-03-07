package pkg.what.a_9.vitals.vitalcore

import android.util.Log
import pkg.what.a_9.vitals.VitalFamily
import pkg.what.a_9.vitals.VitalType
import java.util.logging.Logger
import kotlin.properties.Delegates

class FamilyCrash : VitalFamily() {
    override val type: VitalType by lazy { VitalType.CoreGroup(javaClass.name) }
    override var log: Int = Log.d(LOG_CLASS_TAG, LOG_CLASS_MSG)
    override val logger: Logger = Logger.getLogger(FamilyANR::javaClass.name)

    private val npe: Any? = null

    private lateinit var uninitialized: String

    private var state: String by Delegates.notNull()

    init {
        crashNPE()
        //crashUninitialized()
        //crashState()
    }

    /** @throws NullPointerException
     * reason: cast null property as another type */
    private fun crashNPE(){
        msg(npe as String)
    }

    /** @throws UninitializedPropertyAccessException
     * reason: access an uninitialized property */
    private fun crashUninitialized(){
        msg(uninitialized)
    }

    /** @throws IllegalStateException
     * reason: performs r/w operations on delegate without initialization */
    private fun crashState(){
        msg(state)
    }

    private fun msg(s: String) =
        this.apply {
            log = Log.d(LOG_CLASS_TAG, s)
        }

    companion object {
        const val LOG_CLASS_TAG = "TAG: FamilyANR"
        const val LOG_CLASS_MSG = "MSG: Initialization"
    }
}