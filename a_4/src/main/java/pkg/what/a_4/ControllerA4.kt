package pkg.what.a_4

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import pkg.what.a_4.ModelA4.Companion.MAP_KEY_IMAGE_DATA
import pkg.what.a_4.ModelA4.Companion.MAP_KEY_USER_DATA
import pkg.what.a_4.ViewA4.Companion.LOG_DEBUG_TAG
import java.io.IOException

class ControllerA4 : OkHttpCapableIf {
    override val client: OkHttpClient = OkHttpClient()

    private val parser: Gson by lazy { Gson() }

    fun request(endpoint: String?): Request {
        if(endpoint == null) throw Exception(LOG_REQUEST_FORMAT_FAILED)
        return Request.Builder().url(endpoint).build()
    }

    /**@desc requests the data specified on the endpoint parameter
     * @note this fx is going to die gracefully when the [view] is destroyed due to lifecycleScope */
    fun fireTypicodeCall(request: Request, view: ViewA4, model: ModelA4){
        view.lifecycleScope.launch(Dispatchers.IO){
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(LOG_DEBUG_TAG,LOG_NET_FAILURE +" "+ e.message +" "+ e.stackTrace)
                }
                override fun onResponse(call: Call, response: Response) {
                    Log.d(LOG_DEBUG_TAG,LOG_NET_SUCCESS)
                    if (response.isSuccessful) { debugResponse(response) }
                    else { Log.d(LOG_DEBUG_TAG,LOG_CONNECT_FAILED) }

                    //TODO: fix this, we need to switch to BEGIN_OBJECT but was BEGIN_ARRAY...
                    val data = parser.fromJson(
                        response.body?.charStream()
                        , ModelA4.UserModel::class.java)

                    model.data[MAP_KEY_USER_DATA] = data

                    //notify the view with the received data
                    //change this to a .post or .emit using livedata
                    //for demo purposes do it like this
                    //similar to callback
                    view.update(data)
                }
            })
        }
    }

    /**@desc requests the data specified on the endpoint parameter
     * @note this fx is going to die gracefully when the [view] is destroyed due to lifecycleScope */
    fun fireRobohashCall(request: Request, view: ViewA4, model: ModelA4){
        view.lifecycleScope.launch(Dispatchers.IO){
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(LOG_DEBUG_TAG,LOG_NET_FAILURE +" "+ e.message +" "+ e.stackTrace)
                }
                override fun onResponse(call: Call, response: Response) {
                    Log.d(LOG_DEBUG_TAG,LOG_NET_SUCCESS)
                    if (response.isSuccessful) { debugResponse(response) }
                    else { Log.d(LOG_DEBUG_TAG,LOG_CONNECT_FAILED) }

                    //TODO: fix this, we need to switch to BEGIN_OBJECT but was BEGIN_ARRAY...
                    val data = parser.fromJson(
                        response.body?.charStream()
                        , ModelA4.ImageModel::class.java)

                    model.data[MAP_KEY_IMAGE_DATA] = data

                    //notify the view with the received data
                    //change this to a .post or .emit using livedata
                    //for demo purposes do it like this
                    //similar to callback
                    view.update(data)
                }
            })
        }
    }

    private fun debugResponse(response: Response){
        Log.d(LOG_DEBUG_TAG,LOG_CONNECT_SUCCESS
                + " body: ${response.body}"
                + ", code: ${response.code}"
                + ", msg: ${response.message}")
    }

    /** @desc accessible endpoints in this package within this module */
    /**@note KNOWN_PATHS must resolve to existing directories, SO a static path */
    /**@note RANDOM_PATHS can be anything, SO a dynamic path */
    object ENDPOINTS {
        const val BASE_ROBOHASH_ENDPOINT = "https://robohash.org/"
        const val BASE_TYPICODE_ENDPOINT = "http://jsonplaceholder.typicode.com/"
        const val USERS_PATH = "Users/"
        const val IMAGES_PATH = "Images/"
        const val QWERTY_PATH = "qwerty/"
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object {
        const val LOG_REQUEST_FORMAT_FAILED = "what: is null, who: endpoint, where: ControllerA4.kt"
        const val LOG_CONNECT_FAILED = "Connection Failure"
        const val LOG_CONNECT_SUCCESS = "Connection Success"
        const val LOG_NET_FAILURE = "Failure on the network call ... , reason: "
        const val LOG_NET_SUCCESS = "Success on the network call ... "
    }
}

/**@desc contract for using okhttp, defines a client that operates on [OkHttpClient]
 * @note sealed IF to restrict the use of this particular IF to this module and its packages. */
sealed interface OkHttpCapableIf {
    val client : OkHttpClient
}