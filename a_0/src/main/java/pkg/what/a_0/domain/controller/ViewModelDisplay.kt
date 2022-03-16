package pkg.what.a_0.domain.controller

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pkg.what.a_0.R
import pkg.what.a_0.data.io.network.DataRepoImages
import pkg.what.a_0.data.io.network.DataRepoUsers
import pkg.what.a_0.data.model.DataModel
import pkg.what.a_0.data.model.ImagesModel
import pkg.what.a_0.data.model.UsersModel

class ViewModelDisplay(
    context: Context,
    private val imgRepo: DataRepoImages,
    private val userRepo: DataRepoUsers) : ViewModel(){

    private val users: MutableLiveData<List<DataModel.UserModel>>
        by lazy { MutableLiveData<List<DataModel.UserModel>>() }
    fun getUsers(): LiveData<List<DataModel.UserModel>> {
        return users
    }
    val modelOfUsers = UsersModel()

    private val images: MutableLiveData<List<Bitmap?>>
        by lazy { MutableLiveData<List<Bitmap?>>() }
    fun getImages(): LiveData<List<Bitmap?>> {
        return images
    }
    val modelOfImages = ImagesModel()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            userRepo.data.collect{
                it.forEach { uL -> modelOfUsers.getData().add(uL) }
                users.value = it
                users.postValue(it)
            }

        // TODO:ViewModelDisplay, add error handling for when LiveData<Bitmap> is null
        //  this will happen when the image decoding is not ready or there is io issues
        @Suppress("LocalVariableName") //semantics overrule
        val N = modelOfUsers.getData().size
            for(i in 0..N){
                imgRepo.data.collect { that ->
                    cast<LiveData<Bitmap>>(that){
                        if (this.value != null) {
                            modelOfImages.getData().add(this.value as Bitmap)
                        } else {
                            modelOfImages.getData().add(getCachedBitmap())
                        }
                    }
                }
            }
            images.value = modelOfImages.getData()
            images.postValue(modelOfImages.getData())
        }
    }
    private inline fun <reified T> cast(instance: Any?, body: T.() -> Unit){
        if(instance is T) body(instance)
    }

    private var cachedBitmap: Bitmap? = null
    fun setCachedBitmap(bitmap: Bitmap){ this.cachedBitmap = bitmap }
    private fun getCachedBitmap(): Bitmap? { return this.cachedBitmap }
}