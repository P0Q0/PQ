package pkg.what.a_0.domain.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pkg.what.a_0.data.model.DataModel

class ViewModelDisplay : ViewModel(){

    private val users: MutableLiveData<List<DataModel.UserModel>> by lazy {
        MutableLiveData<List<DataModel.UserModel>>().also { loadUsers() }
    }

    fun getUsers(): LiveData<List<DataModel.UserModel>> {
        return users
    }

    private fun loadUsers() {
        //TODO: network io
    }
}