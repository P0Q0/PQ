package pkg.what.a_0.data.io.network

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataSourceImages(private val caller: RoboHashApi
    , private val ioDispatcher: CoroutineDispatcher) : DataSource() {

        /** @Note: DaraSourceImages, fetchImage with Picasso-n/Glide-s **/
        suspend fun fetchImagesUsingRetrofit(): Any
            = withContext(ioDispatcher){ caller.getRoboHash("qwerty") }

        suspend fun fetchImages(ld: MutableLiveData<Bitmap>): Any
            = withContext(ioDispatcher){
                this.launch(Dispatchers.IO) {
                    /** @Note endpoint and path can be passed into the source, path can be randomized */
                    val bitmap = Picasso.get().load("https://robohash.org/qwerty").get()
                    ld.postValue(bitmap)
                }
            }
}