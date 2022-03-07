package pkg.what.a_9.vitals.vitalother

import android.util.Log
import pkg.what.a_9.vitals.VitalFamily
import pkg.what.a_9.vitals.VitalType
import java.util.logging.Logger

class FamilyRender : VitalFamily() {
    override val type: VitalType by lazy { VitalType.CoreGroup(javaClass.name) }
    override val log: Int = Log.d(LOG_CLASS_TAG, LOG_CLASS_MSG)
    override val logger: Logger = Logger.getLogger(FamilyRender::javaClass.name)

    init {
        renderLayoutPerformance()
    }

    //TODO: renderLayoutPerformance
    private fun renderLayoutPerformance(){
        //start new activity
        //nest linear layouts
    }

    companion object {
        const val LOG_CLASS_TAG = "TAG: FamilyRender"
        const val LOG_CLASS_MSG = "MSG: Initialization"
    }
}