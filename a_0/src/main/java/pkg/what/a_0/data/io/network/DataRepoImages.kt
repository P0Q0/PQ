package pkg.what.a_0.data.io.network

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DataRepoImages(private val remoteImages: DataSourceImages) {

    private val ld: MutableLiveData<Bitmap> by lazy { MutableLiveData<Bitmap>() }

    fun getLiveData(): LiveData<Bitmap> { return ld }

    val data: Flow<Any>
        = flow {
            emit(remoteImages.fetchImages(ld))
        }
}