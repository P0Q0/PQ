package pkg.what.a_0.domain.controller

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pkg.what.a_0.data.io.network.DataRepoImages
import pkg.what.a_0.data.io.network.DataRepoUsers
import pkg.what.a_0.data.model.DataModel

class ViewModelDisplay(
    //private val imgRepo: DataRepoImages,
    private val userRepo: DataRepoUsers) : ViewModel(){

    private val users: MutableLiveData<List<DataModel.UserModel>>
        by lazy { MutableLiveData<List<DataModel.UserModel>>() }

    fun getUsers(): LiveData<List<DataModel.UserModel>> {
        return users
    }

    init {
        loadUsers()
    }

    private fun loadUsers() {
        //TODO: network io load users
//        viewModelScope.launch {
//            imgRepo.data.collect {
//                //TODO: ViewModelDisplay, asynchronously update UI
//                Log.d("ViewModelDisplay, flow is emitting IMG data, loadUsers", it.toString())
//            }
//        }
        //TODO: network io load images
        viewModelScope.launch {
            userRepo.data.collect{
                //it.forEach { user -> }
                //users.value = it
                Log.d("ViewModelDisplay, flow is emitting SRC data, loadUsers", it.toString())
            }
        }
    }
}