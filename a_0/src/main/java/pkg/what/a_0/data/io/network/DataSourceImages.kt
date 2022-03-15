package pkg.what.a_0.data.io.network

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

class DataSourceImages(private val caller: RoboHashApi
    , private val ioDispatcher: CoroutineDispatcher) : DataSource() {

        suspend fun fetchImagesUsingRetrofit(): Any
            = withContext(ioDispatcher){ caller.getRoboHash("path") }

        suspend fun fetchImages(picasso: Picasso, ld: MutableLiveData<Bitmap>, path: String): Any
            = withContext(ioDispatcher){
                this.launch(Dispatchers.IO) {
                    try {
                        /** @note about workarounds for okhttp picasso and image processing and coroutines
                         * 1) drop robohash SSH handshake, ie take out the s in https, call as http
                         *  for okhttp is not playing nice without an interceptor that
                         *  wil handle server side errors such as 500,504
                         * 2) we are suspended in the IO thread, meaning CPU thread is slow to help
                         *  this is important because the data could potentially not be ready and we
                         *  call ld.post(data) with a null value, that null value in reality is an
                         *  image in memory still getting decoded and *has not* finished yet, but if
                         *  we ld.post which will usually flow.emit which will then ld.observe and
                         *  finally uiThread.run as far as the data flow goes for this application
                         *  and update the ui with a null pointer is unacceptable
                         *  so in essence waste somme time idling, ie delay
                         *  3) garbage collector is super fast from the logs we can see it's chewing
                         *  up the memory this is an issue because it's interfering with the
                         *  image decode process, sample gc log:
                         *  D/LeakCanary: Analysis in progress, 0% done, working on Parsing heap dump
                         *  D/Picasso: Hunter decoded      [ R26 ]+442ms
                         *  D/Picasso: Main   created      [ R27 ] Request{http://robohash.org/d6hW}
                         *  ===> GC: I/pkg.what.pq: Background concurrent copying GC freed 17(535KB)
                         *  AllocSpace objects, 1(516KB) LOS objects, 42% free, 32MB/56MB, paused 451us total 168.803ms */
                        val bitmap = picasso.load("http://robohash.org/$path").get()
                        @Suppress("LocalVariableName") //semantics overrule
                        val NETWORK_REQUEST_IMAGE_WRAP_OBJECT_SYNTHESIZE_THEN_CREATE_AND_DECODE = 3000L
                        delay(timeMillis = NETWORK_REQUEST_IMAGE_WRAP_OBJECT_SYNTHESIZE_THEN_CREATE_AND_DECODE)
                        ld.postValue(bitmap)
                    } catch (e: Exception){
                        println(e.printStackTrace())
                    }
                }
            }
}