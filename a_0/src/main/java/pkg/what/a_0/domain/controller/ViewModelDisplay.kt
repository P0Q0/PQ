package pkg.what.a_0.domain.controller

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pkg.what.a_0.data.io.network.DataRepoUsers
import pkg.what.a_0.data.model.DataModel
import pkg.what.a_0.data.model.UsersModel

class ViewModelDisplay(
    //private val imgRepo: DataRepoImages,
    private val userRepo: DataRepoUsers) : ViewModel(){

    private val users: MutableLiveData<List<DataModel.UserModel>>
        by lazy { MutableLiveData<List<DataModel.UserModel>>() }

    fun getUsers(): LiveData<List<DataModel.UserModel>> {
        return users
    }

    val model = UsersModel()

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
        viewModelScope.launch {
            userRepo.data.collect{
                users.value = it
                users.postValue(it)
            }
        }
    }
}