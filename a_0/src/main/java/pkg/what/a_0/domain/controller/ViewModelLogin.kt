package pkg.what.a_0.domain.controller

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import pkg.what.a_0.data.model.LoginsModel

class ViewModelLogin : ViewModel(){

    private val builder: GoogleSignInOptions.Builder
            by lazy { GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail() }

    private val model = LoginsModel()

    init {

    }

    fun getModel(): LoginsModel {
        return model
    }

    fun getVmLoginBuilder(): GoogleSignInOptions.Builder {
        return this.builder
    }
}