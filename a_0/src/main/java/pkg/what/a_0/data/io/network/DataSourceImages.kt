package pkg.what.a_0.data.io.network

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pkg.what.a_0.R
import pkg.what.a_0.util.StrTool

class DataSourceImages(private val caller: RoboHashApi
    , private val ioDispatcher: CoroutineDispatcher) : DataSource() {

        suspend fun fetchImagesUsingRetrofit(): Any
            = withContext(ioDispatcher){ caller.getRoboHash("path") }

        suspend fun fetchImages(picasso: Picasso, ld: MutableLiveData<Bitmap>, path: String): Any
            = withContext(ioDispatcher){
                this.launch(Dispatchers.IO) {
                    val bitmap = picasso.load("https://www.robohash.org/$path").get()
                    ld.postValue(bitmap)
                }
            }
}