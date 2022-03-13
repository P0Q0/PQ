package pkg.what.a_0.domain.controller

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import pkg.what.a_0.data.model.LoginsModel
import pkg.what.a_0.domain.config.ConfigPQ

class ViewModelLogin(context: Context) : ViewModel(){

    private val builder:
        GoogleSignInOptions.Builder by lazy {
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(ConfigPQ.getClientId()) }

    private val model = LoginsModel()

    fun getModel(): LoginsModel {
        return model
    }

    fun getVmLoginBuilder(): GoogleSignInOptions.Builder {
        return this.builder
    }
}