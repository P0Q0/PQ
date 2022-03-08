package pkg.what.a_0.domain.controller

import androidx.lifecycle.ViewModel
import pkg.what.a_0.data.model.LoginModel

class ViewModelLogin : ViewModel(){
    private val model = LoginModel()

    fun getModel(): LoginModel {
        return model
    }
}