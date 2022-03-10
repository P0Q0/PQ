package pkg.what.a_0.data.tool

import android.util.Log
import okhttp3.Response
import pkg.what.a_0.domain.core.constant.ConstantsPQ.LOG_DEBUG_TAG
import pkg.what.a_0.domain.core.constant.ConstantsPQ.LOG_INFO_TAG
import pkg.what.a_0.domain.core.constant.NetConTags.LOG_CONNECT_SUCCESS

class NetworkUtil {

    fun debugNetCode(code : Int){
        when(code) {
            403 -> println("403 forbidden")
            200 -> println("200 ok")
        }
        Log.d(LOG_INFO_TAG,"code:$code")
    }

    fun debugMsg(msg: String) {
        if(msg.isBlank() || msg.isEmpty()){
            Log.d(LOG_DEBUG_TAG,"message is blank or empty")
        }
    }

    private fun debugResponse(response: Response) {
        Log.d(
            LOG_DEBUG_TAG, LOG_CONNECT_SUCCESS
                    + " body: ${response.body}"
                    + ", code: ${response.code}"
                    + ", msg: ${response.message}"
        )
    }
}