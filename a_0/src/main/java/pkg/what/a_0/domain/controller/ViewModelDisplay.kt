package pkg.what.a_0.domain.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pkg.what.a_0.data.io.network.DataRepoImages
import pkg.what.a_0.data.io.network.DataRepoUsers
import pkg.what.a_0.data.model.DataModel
import pkg.what.a_0.data.model.ImagesModel
import pkg.what.a_0.data.model.UsersModel

class ViewModelDisplay(
    private val imgRepo: DataRepoImages,
    private val userRepo: DataRepoUsers) : ViewModel(){

    private val users: MutableLiveData<List<DataModel.UserModel>>
        by lazy { MutableLiveData<List<DataModel.UserModel>>() }
    fun getUsers(): LiveData<List<DataModel.UserModel>> {
        return users
    }
    val modelOfUsers = UsersModel()

    private val images: MutableLiveData<Any>
        by lazy { MutableLiveData<Any>() }
    fun getImages(): LiveData<Any> {
        return images
    }
    val modelOfImages = ImagesModel()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            imgRepo.data.collect {
                images.postValue(imgRepo.getLiveData().value)
            }
        }
        viewModelScope.launch {
            userRepo.data.collect{
                users.value = it
                users.postValue(it)
            }
        }
    }
}