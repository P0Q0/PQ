package pkg.what.a_0.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
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
import pkg.what.a_0.domain.controller.ViewModelLogin
import pkg.what.a_0.domain.core.constants.FragLcTags.LOG_CREATE_VIEW
import pkg.what.a_0.domain.core.constants.FragLcTags.LOG_START
import pkg.what.a_0.domain.core.constants.FragLcTags.LOG_VIEW_CREATED
import pkg.what.pq.databinding.LayoutA0LoginBinding
import pkg.what.pq.R

class ViewLogin : Fragment(), View.OnClickListener {

    private lateinit var bind: LayoutA0LoginBinding

    private var navCntrl: NavController? = null

    private lateinit var gso: GoogleSignInOptions

    private var gsc: GoogleSignInClient? = null

    private lateinit var rs: ActivityResultLauncher<Intent>

    private val vmLogin: ViewModelLogin by viewModels()

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View? {
        this.bind = LayoutA0LoginBinding.inflate(layoutInflater, parent, false)
        Log.d(LOG_INFO_TAG, LOG_CREATE_VIEW)
        return bind.root
    }

    override fun onViewCreated(view: View, state: Bundle?) {
        super.onViewCreated(view, state)
        Log.d(LOG_INFO_TAG, LOG_VIEW_CREATED)
        this.navCntrl = Navigation.findNavController(view)

        register()
        setUi()
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOG_INFO_TAG, LOG_START)
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        reflectUi(account)
    }

    private fun navigate() =
        this.requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.layout_a0_login, ViewDisplay()::class.java, Bundle())
            .commit()

    private fun fireGoogleSignInConfigurationStage(){
        this.gso = vmLogin.getVmLoginBuilder().build()
    }

    private fun fireGoogleSignInBuildClientStage(){
        this.gsc = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun reflectUi(account: GoogleSignInAccount?){
        if(account != null){
            //TODO: ViewLogin, stash login account in shared preferences
            Toast.makeText(requireContext(),SIGN_IN_TAG,Toast.LENGTH_SHORT).show()
            bind.a0GoogleSignInBtn.visibility = View.GONE
            bind.a0GoogleSignOutBtn.visibility = View.VISIBLE
            debugSnack(account)
            navCntrl?.navigate(R.id.nav_fragment_a0_display)
        } else {
            Toast.makeText(requireContext(), SIGN_OUT_TAG,Toast.LENGTH_SHORT).show()
            bind.a0GoogleSignInBtn.visibility = View.VISIBLE
            bind.a0GoogleSignOutBtn.visibility = View.GONE
            debugSnack(account)
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

    private fun setUi(){
        setElements()
        setListeners()
    }

    private fun setElements(){
        /** @note idiomatic configuration of the object and addition effects */
        bind.a0GoogleSignInBtn.apply {
            this.setSize(SignInButton.SIZE_STANDARD)
            this.setColorScheme(SignInButton.COLOR_LIGHT)
        }
    }

    private fun setListeners(){
        /** @note explicit type specification of each button provides clarity */
        this.requireView().findViewById<SignInButton>(R.id.a0_google_sign_in_btn).setOnClickListener(this)
        this.requireView().findViewById<MaterialButton>(R.id.a0_google_sign_out_btn).setOnClickListener(this)
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

    override fun onClick(v: View?) {
        if(v != null) {
            when (v.id) {
                R.id.a0_google_sign_in_btn -> { fireSignIn() }
                R.id.a0_google_sign_out_btn -> { fireRevoke() }
            }
        } else {
            Toast.makeText(requireContext()
                ,"$javaClass is null, reason: can not set click listener on a view that is $v"
                , Toast.LENGTH_SHORT).show()
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
        fireGoogleSignInConfigurationStage()
        fireGoogleSignInBuildClientStage()
        gsc?.signOut()?.addOnCompleteListener(requireActivity(), object : OnCompleteListener<Void> {
            override fun onComplete(p0: Task<Void>) {
                reflectUi(null)
            }
        })
    }

    private fun fireRevoke(){
        fireGoogleSignInConfigurationStage()
        fireGoogleSignInBuildClientStage()
        gsc?.revokeAccess()?.addOnCompleteListener(requireActivity(), object : OnCompleteListener<Void> {
            override fun onComplete(p0: Task<Void>) {
                reflectUi(null)
            }
        })
    }

    /**@note decorate account as null in the event it is null the fx will handle it */
    private fun debugSnack(@Nullable account: GoogleSignInAccount?){
        if(account != null){
            val snack = Snackbar.make(
                requireActivity().findViewById(R.id.layout_a0_login)
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
                , Snackbar.LENGTH_SHORT)
                .setTextColor(requireActivity().getColor(R.color.colorDark))
                .setAction(getString(R.string.sb_dismiss))
                    { Log.d(LOG_DEBUG_TAG,"${requireActivity().localClassName} , " +
                            " ${resources.getString(R.string.sb_on_click)}") }
                snack.view.updateLayoutParams<FrameLayout.LayoutParams>
                    { this.height = UI_SNACK_HEIGHT }
                val snackText = snack.view.findViewById<TextView>(
                        com.google.android.material.R.id.snackbar_text)
                    snackText.maxLines = UI_SNACK_TEXT_LINES
                snack.show()
        } else {
            Toast.makeText(requireContext()
                ,"$javaClass is null, reason: can not access an null GoogleSignInAccount"
                ,Toast.LENGTH_SHORT).show()
        }
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "A0_VIEW_LOGIN_DEBUG_TAG"
        const val LOG_INFO_TAG = "A0_VIEW_LOGIN_INFO_TAG"
        const val SIGN_IN_TAG = "SignInActivity"
        const val SIGN_OUT_TAG = "SignOutActivity"
        const val RESULT_CODE_SIGN_IN = 9001
        const val RESULT_CODE_SIGN_OUT = 9002
        const val UI_SNACK_HEIGHT = 500
        const val UI_SNACK_TEXT_LINES = 15
    }
}