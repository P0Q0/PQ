package pkg.what.a_0.ui.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.core.os.bundleOf
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import pkg.what.a_0.domain.controller.ViewModelLogin
import pkg.what.a_0.domain.core.constant.FragLcTags.LOG_ATTACH
import pkg.what.a_0.domain.core.constant.FragLcTags.LOG_CREATE
import pkg.what.a_0.domain.core.constant.FragLcTags.LOG_CREATE_VIEW
import pkg.what.a_0.domain.core.constant.FragLcTags.LOG_DESTROY
import pkg.what.a_0.domain.core.constant.FragLcTags.LOG_DESTROY_VIEW
import pkg.what.a_0.domain.core.constant.FragLcTags.LOG_DETACH
import pkg.what.a_0.domain.core.constant.FragLcTags.LOG_PAUSE
import pkg.what.a_0.domain.core.constant.FragLcTags.LOG_RESUME
import pkg.what.a_0.domain.core.constant.FragLcTags.LOG_SAVE_INSTANCE_STATE
import pkg.what.a_0.domain.core.constant.FragLcTags.LOG_START
import pkg.what.a_0.domain.core.constant.FragLcTags.LOG_STOP
import pkg.what.a_0.domain.core.constant.FragLcTags.LOG_VIEW_CREATED
import pkg.what.a_0.domain.core.constant.FragLcTags.LOG_VIEW_STATE_RESTORED
import pkg.what.a_0.domain.core.constant.ProfileTags.TAG_EMAIL
import pkg.what.a_0.domain.core.constant.ProfileTags.TAG_IMGURL
import pkg.what.a_0.domain.core.constant.ProfileTags.TAG_NAME
import pkg.what.a_0.domain.core.constant.ProfileTags.TAG_USER_SIGNED_IN
import pkg.what.a_0.domain.core.constant.SharedPrefTags.SHARED_PREFERENCES_NULL
import pkg.what.a_0.domain.core.constant.SharedPrefTags.STATE_TOKEN_ON_DISK
import pkg.what.a_0.domain.core.constant.SharedPrefTags.STATUS_DESTROY_ON_DISK
import pkg.what.a_0.domain.core.constant.SharedPrefTags.STATUS_TOKEN_ON_DISK
import pkg.what.a_0.domain.pref.PrefPQ
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA0LoginBinding
import java.net.URI

class ViewLogin : Fragment(), View.OnClickListener {

    private lateinit var bind: LayoutA0LoginBinding

    private var navCntrl: NavController? = null

    private lateinit var gso: GoogleSignInOptions

    private var gsc: GoogleSignInClient? = null

    private lateinit var rs: ActivityResultLauncher<Intent>

    private lateinit var vmLogin: ViewModelLogin

    private val pref: PrefPQ by lazy {  PrefPQ(requireContext()) }

    private var stashedGgTokenOnDisk: String? = null

    private fun di() {
        vmLogin = ViewModelLogin(super.requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        di()
        Log.d(LOG_INFO_TAG, LOG_ATTACH)
    }

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this@ViewLogin.childFragmentManager.popBackStack()
                requireActivity().finishAffinity()
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        this.stashedGgTokenOnDisk = pref.read(STATE_TOKEN_ON_DISK)
        if(pref.read(STATUS_TOKEN_ON_DISK) == STATUS_DESTROY_ON_DISK){ this.fireRevoke() }
        Log.d(LOG_INFO_TAG, LOG_CREATE)
    }

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

    override fun onViewStateRestored(state: Bundle?) {
        super.onViewStateRestored(state)
        Log.i(LOG_INFO_TAG, LOG_VIEW_STATE_RESTORED)
    }

    override fun onStart() {
        super.onStart()
        if(stashedGgTokenOnDisk != null
            || stashedGgTokenOnDisk != SHARED_PREFERENCES_NULL){
            fireSilentSignIn()
        } else {
            val account = GoogleSignIn.getLastSignedInAccount(requireContext())
            reflectUi(account)
        }
        Log.i(LOG_INFO_TAG,LOG_START)
    }

    override fun onResume() {
        super.onResume()
        Log.i(LOG_INFO_TAG,LOG_RESUME)
    }

    override fun onPause() {
        super.onPause()
        Log.i(LOG_INFO_TAG,LOG_PAUSE)
    }

    override fun onStop() {
        super.onStop()
        Log.i(LOG_INFO_TAG,LOG_STOP)
    }

    override fun onSaveInstanceState(state: Bundle) {
        Log.i(LOG_INFO_TAG, LOG_SAVE_INSTANCE_STATE)
        super.onSaveInstanceState(state)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG_INFO_TAG,LOG_DESTROY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(LOG_INFO_TAG, LOG_DESTROY_VIEW)
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(LOG_INFO_TAG, LOG_DETACH)
    }

    private fun fireGoogleSignInConfigurationStage(){
        this.gso = vmLogin.getVmLoginBuilder().build()
    }

    private fun fireGoogleSignInBuildClientStage(){
        this.gsc = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun reflectUi(account: GoogleSignInAccount?){
        if(account != null){
            stashedGgTokenOnDisk = account.idToken
            bind.a0GoogleSignInBtn.visibility = View.GONE
            bind.a0GoogleSignOutBtn.visibility = View.VISIBLE
            account.idToken?.let { vmLogin.getModel().getCache().add(it) }
            navCntrl?.navigate(R.id.nav_fragment_a0_display,
                bundleOf(TAG_USER_SIGNED_IN to account.idToken
                    , TAG_EMAIL to account.email
                    , TAG_NAME to account.displayName
                    , TAG_IMGURL to URI(account.photoUrl?.toString()).toString() ))
        } else {
            stashedGgTokenOnDisk = account?.email ?: SHARED_PREFERENCES_NULL
            bind.a0GoogleSignInBtn.visibility = View.VISIBLE
            bind.a0GoogleSignOutBtn.visibility = View.GONE
        }
        pref.write(STATE_TOKEN_ON_DISK, stashedGgTokenOnDisk)
    }

    private fun register(){
        this.rs = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { lambda ->
                when(lambda.resultCode){
                    Activity.RESULT_OK -> {
                        Log.d(LOG_DEBUG_TAG, "$javaClass, : ${lambda.resultCode}")
                        val task = GoogleSignIn.getSignedInAccountFromIntent(lambda.data)
                        handleSignInResult(task)
                    }
                    CommonStatusCodes.SUCCESS_CACHE -> {
                        /** @note i've taken note of this issue, it's a cache thing with the emulator and rsa fingerprint */
                    }
                    else -> { Log.d(LOG_DEBUG_TAG,"$javaClass : ${lambda.resultCode}") }
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
                R.id.a0_google_sign_out_btn -> { fireSignOut() }
            }
        } else {
            Log.d(LOG_DEBUG_TAG, "$javaClass is null, reason: can not set click listener on a view that is $v")
        }
    }

    private fun fireSignIn(){
        fireGoogleSignInConfigurationStage()
        fireGoogleSignInBuildClientStage()
        if(this.gsc != null){
            val intent: Intent = gsc?.signInIntent as Intent
            val bundle = Bundle().apply { putInt("RESULT_CODE_SIGN_IN",RESULT_CODE_SIGN_IN) }
            intent.putExtras(bundle)
            rs.launch(intent)
        } else {
            debugSnack(null)
        }
    }

    private fun fireSignOut(){
        gsc?.signOut()?.addOnCompleteListener(requireActivity(), object : OnCompleteListener<Void> {
            override fun onComplete(p0: Task<Void>) {
                reflectUi(null)
            }
        })
        //?.addOnSuccessListener(requireActivity(), object : OnSuccessListener<Void>{
        //    override fun onSuccess(p0: Void?) {
        //        rs.launch(Intent().putExtra("RESULT_CODE_SIGN_OUT", RESULT_CODE_SIGN_OUT))
        //    }
        //})?.addOnFailureListener(requireActivity(), object : OnFailureListener{
        //    override fun onFailure(p0: Exception) {
        //        p0.message?.let { Log.d(LOG_DEBUG_TAG, it) }
        //        p0.printStackTrace()
        //    }
        //})
    }

    private fun fireRevoke(){
        gsc?.revokeAccess()?.addOnCompleteListener(requireActivity(), object : OnCompleteListener<Void> {
            override fun onComplete(p0: Task<Void>) {
                reflectUi(null)
            }
        })
        //?.addOnSuccessListener(requireActivity(), object : OnSuccessListener<Void>{
        //    override fun onSuccess(p0: Void?) {
        //        rs.launch(Intent().putExtra("RESULT_CODE_REVOKE", RESULT_CODE_REVOKE))
        //    }
        //})?.addOnFailureListener(requireActivity(), object : OnFailureListener{
        //    override fun onFailure(p0: Exception) {
        //        p0.message?.let { Log.d(LOG_DEBUG_TAG, it) }
        //        p0.printStackTrace()
        //    }
        //})
    }

    private fun fireSilentSignIn(){
        gsc?.silentSignIn()?.addOnCompleteListener(requireActivity(), object : OnCompleteListener<GoogleSignInAccount>{
            override fun onComplete(p0: Task<GoogleSignInAccount>) {
                reflectUi(p0.result)
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
        const val RESULT_CODE_SIGN_IN = 9001
        const val RESULT_CODE_SIGN_OUT = 9002
        const val RESULT_CODE_REVOKE = 9003
        const val UI_SNACK_HEIGHT = 500
        const val UI_SNACK_TEXT_LINES = 15
    }
}