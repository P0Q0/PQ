package pkg.what.a_3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA3Binding

@Suppress("unused") /** @note, this is rly bad but mindfully for completeness, suppress unused stuff */

class ViewA3 : AppCompatActivity(), View.OnClickListener {
    private lateinit var bind: LayoutA3Binding

    private val builder:GoogleSignInOptions.Builder
        by lazy { GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail() }

    private lateinit var gso: GoogleSignInOptions

    private var gsc: GoogleSignInClient? = null

    private lateinit var rs: ActivityResultLauncher<Intent>

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutA3Binding.inflate(layoutInflater).also { setContentView(it.root) }
        snack(
            R.string.purpose_a3
            ,"$localClassName , ${resources.getString(R.string.sb_on_click)}")
        register()
        setUi()
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        reflectUi(account)
    }

    private fun setUi(){
        setElements()
        setListeners()
    }

    private fun setElements(){
        /** @note idiomatic configuration of the object and addition effects */
        bind.a3GoogleSignInBtn.apply {
            this.setSize(SignInButton.SIZE_STANDARD)
            this.setColorScheme(SignInButton.COLOR_LIGHT)
        }
    }

    private fun setListeners(){
        /** @note explicit type specification of each button provides clarity */
        this.findViewById<SignInButton>(R.id.a3_google_sign_in_btn).setOnClickListener(this)
        this.findViewById<MaterialButton>(R.id.a3_google_sign_out_btn).setOnClickListener(this)
    }

    private fun fireGoogleSignInConfigurationStage(){
        this.gso = builder.build()
    }

    private fun fireGoogleSignInBuildClientStage(){
        this.gsc = GoogleSignIn.getClient(this, gso)
    }

    private fun reflectUi(account: GoogleSignInAccount?){
        if(account != null){
            bind.a3GoogleSignInBtn.visibility = View.GONE
            bind.a3GoogleSignOutBtn.visibility = View.VISIBLE
            debugSnack(account)
        } else {
            bind.a3GoogleSignInBtn.visibility = View.VISIBLE
            bind.a3GoogleSignOutBtn.visibility = View.GONE
            debugSnack(account)
        }
    }

    override fun onClick(v: View?) {
        if(v != null) {
            when (v.id) {
                R.id.a3_google_sign_in_btn -> { fireSignIn() }
                R.id.a3_google_sign_out_btn -> { fireSignOut() }
            }
        } else {
            Toast.makeText(this
                ,"$javaClass is null, reason: can not set click listener on a view that is $v"
                ,Toast.LENGTH_SHORT).show()
        }
    }

    private fun fireSignIn(){
        fireGoogleSignInConfigurationStage()
        fireGoogleSignInBuildClientStage()
        if(this.gsc != null){
            val intent: Intent = gsc?.signInIntent as Intent
            rs.launch(intent)
        } else {
            debugSnack(null)
        }
    }

    private fun fireSignOut(){
        gsc?.signOut()?.addOnCompleteListener(this, object : OnCompleteListener<Void?> {
            override fun onComplete(p0: Task<Void?>) {
                reflectUi(null)
            }
        })
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            reflectUi(account)
        } catch (e: ApiException) {
            Log.d(LOG_INFO_TAG, "signInResult:failed code=" + e.statusCode)
            reflectUi(null)
        }
    }

    private fun register(){
        this.rs = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { lambda ->
            if (lambda.resultCode ==  RESULT_CODE_SIGN_IN) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(lambda.data)
                handleSignInResult(task)
            }
            if (lambda.resultCode ==  RESULT_CODE_SIGN_OUT) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(lambda.data)
                handleSignInResult(task)
            }
        }
    }

    /**@note decorate account as null in the event it is null the fx will handle it */
    private fun debugSnack(@Nullable account: GoogleSignInAccount?){
        if(account != null){
            Snackbar.make(
                findViewById(R.id.layout_a3)
                ,"ACCOUNT_NAME: ${account.account?.name}" +
                        ", ACCOUNT_TYPE: ${account.account?.type}" +
                        ", ACCOUNT_describeContents(): ${account.account?.describeContents()}" +
                        ", ACCOUNT_ACT: ${account.account}" +
                        ", ACCOUNT_ID: ${account.id}" +
                        ", ACCOUNT_ID_TOKEN: ${account.idToken}" +
                        ", ACCOUNT_EMAIL: ${account.email}" +
                        ", ACCOUNT_DISPLAY_NAME: ${account.displayName}" +
                        ", ACCOUNT_REQ_SCOPE: ${account.requestedScopes}" +
                        ", ACCOUNT_GRANTED_SCOPE: ${account.grantedScopes}" +
                        ", ACCOUNT_EXPIRED: ${account.isExpired}" +
                        ", ACCOUNT_SERV_AUTH_CODE: ${account.serverAuthCode}" +
                        ", OBJECT: $account"
                ,Snackbar.LENGTH_SHORT)
                .setTextColor(getColor(R.color.colorLight))
                .setAction(getString(R.string.sb_dismiss))
                { Log.d(LOG_DEBUG_TAG,"$localClassName , ${resources.getString(R.string.sb_on_click)}") }
                .show()
        } else {
            Toast.makeText(this
                ,"$javaClass is null, reason: can not access an null GoogleSignInAccount"
                ,Toast.LENGTH_SHORT).show()
        }
    }

    /** @desc file specific for short snackbar */
    private fun snack(ui_msg: Int, log_msg: String) =
        Snackbar.make(
            findViewById(R.id.layout_a3)
            ,getString(ui_msg)
            , Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_INFO_TAG,log_msg) }
            .show()

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "VIEW_A3_DEBUG_TAG"
        const val LOG_INFO_TAG ="VIEW_A3_INFO_TAG"
        const val SIGN_IN_TAG = "SignInActivity"
        const val RESULT_CODE_SIGN_IN = 9001
        const val RESULT_CODE_SIGN_OUT = 9002
    }
}