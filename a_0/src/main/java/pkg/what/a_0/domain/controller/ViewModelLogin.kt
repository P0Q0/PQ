package pkg.what.a_0.domain.controller

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import pkg.what.a_0.data.model.LoginsModel
import pkg.what.pq.BuildConfig.GOOGLE_SERVICES_API_CLIENT_ID

class ViewModelLogin : ViewModel(){

    private val builder:
        GoogleSignInOptions.Builder by lazy {
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(GOOGLE_SERVICES_API_CLIENT_ID) }

    private val model = LoginsModel()

    fun getModel(): LoginsModel {
        return model
    }

    fun getVmLoginBuilder(): GoogleSignInOptions.Builder {
        return this.builder
    }
}