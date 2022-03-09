package pkg.what.a_0.data.model

import android.graphics.Bitmap

class ImagesModel {
    private val data: MutableList<Bitmap> = mutableListOf()

    fun getData() : MutableList<Bitmap> { return this.data }
}