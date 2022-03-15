package pkg.what.a_0.data.io.network

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pkg.what.a_0.util.StrTool

class DataSourceImages(private val caller: RoboHashApi
    , private val ioDispatcher: CoroutineDispatcher) : DataSource() {

        /** @Note: DaraSourceImages, fetchImage with Picasso-n/Glide-s **/
        suspend fun fetchImagesUsingRetrofit(): Any
            = withContext(ioDispatcher){ caller.getRoboHash("path") }

        suspend fun fetchImages(ld: MutableLiveData<Bitmap>): Any
            = withContext(ioDispatcher){
                this.launch(Dispatchers.IO) {
                    val bitmap = Picasso.get().load("https://robohash.org/${StrTool.strGenerateRandom()}").get()
                    ld.postValue(bitmap)
                }
            }
}