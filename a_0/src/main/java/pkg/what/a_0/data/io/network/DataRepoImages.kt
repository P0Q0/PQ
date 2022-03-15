package pkg.what.a_0.data.io.network

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pkg.what.a_0.util.StrTool

class DataRepoImages(private val picasso: Picasso, private val remoteImages: DataSourceImages) {

    private val ld: MutableLiveData<Bitmap> by lazy { MutableLiveData<Bitmap>() }

    private fun getLiveData(): LiveData<Bitmap> { return ld }

    val data: Flow<Any>
        = flow {
            val path: String = StrTool.strGenerateRandom()
            remoteImages.fetchImages(picasso,ld,path)
            emit(getLiveData())
        }
}