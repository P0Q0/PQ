package pkg.what.a_0.domain.controller

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
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

    private val images: MutableLiveData<List<Bitmap>>
        by lazy { MutableLiveData<List<Bitmap>>() }
    fun getImages(): LiveData<List<Bitmap>> {
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

        @Suppress("LocalVariableName")
        val N = modelOfUsers.getData().size
            for(i in 0..N){
                imgRepo.data.collect { that ->
                    cast<LiveData<Bitmap>>(that){
                        modelOfImages.getData().add( i , this.value as Bitmap )
                    }
                }
            }
            images.value = modelOfImages.getData()
            images.postValue(modelOfImages.getData())
        }
    }
    inline fun <reified T> cast(instance: Any?, body: T.() -> Unit){
        if(instance is T) body(instance)
    }
}