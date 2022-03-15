package pkg.what.a_0.ui.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pkg.what.a_0.data.model.DataModel
import pkg.what.a_0.domain.controller.ViewModelDisplay
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
import pkg.what.a_0.domain.core.constant.SharedPrefTags
import pkg.what.a_0.domain.core.di.DomainDi
import pkg.what.a_0.domain.pref.PrefPQ
import pkg.what.a_0.ui.CardAdapter
import pkg.what.pq.databinding.LayoutA0DisplayBinding

class ViewDisplay : Fragment() , LogOutIf {

    private lateinit var bind: LayoutA0DisplayBinding

    private var navCntrl: NavController? = null

    private lateinit var vmDisplay: ViewModelDisplay

    private val pref: PrefPQ by lazy {  PrefPQ(requireContext()) }

    private var stashedDisk: String? = null

    private fun di() {
        val domain = DomainDi(super.requireContext().applicationContext)
        vmDisplay = ViewModelDisplay(
            super.requireContext(),
            domain.getRemoteDataRepoImagesFromRoboHash(),
            domain.getRemoteDataRepoUsersFromTypiCode())
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
                pref.write(SharedPrefTags.STATUS_TOKEN_ON_DISK, SharedPrefTags.STATUS_DESTROY_ON_DISK)
                fireLogOut()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        Log.d(LOG_INFO_TAG, LOG_CREATE)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View? {
        this.bind = LayoutA0DisplayBinding.inflate(layoutInflater, parent, false)
        Log.d(LOG_INFO_TAG, LOG_CREATE_VIEW)
        return bind.root
    }

    override fun onViewCreated(view: View, state: Bundle?) {
        super.onViewCreated(view, state)
        Log.d(LOG_INFO_TAG, LOG_VIEW_CREATED)
        this.navCntrl = Navigation.findNavController(view)

        /** adds the current user signed in, only after navCntrl initializes */
        val user = DataModel.UserModel(
            name = arguments?.getString(TAG_USER_SIGNED_IN)
            , email = arguments?.getString(TAG_EMAIL)
            , username = arguments?.getString(TAG_NAME)
            , website = arguments?.getString(TAG_IMGURL))
        vmDisplay.modelOfUsers.getData().add(user)

        listenOnUiObservers()
        setupUi()
        setListeners()
    }

    override fun onViewStateRestored(state: Bundle?) {
        super.onViewStateRestored(state)
        Log.i(LOG_INFO_TAG, LOG_VIEW_STATE_RESTORED)
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOG_INFO_TAG, LOG_START)
    }

    override fun onResume() {
        super.onResume()
        Log.i(LOG_INFO_TAG, LOG_RESUME)
    }

    override fun onPause() {
        super.onPause()
        Log.i(LOG_INFO_TAG, LOG_PAUSE)
    }

    override fun onStop() {
        super.onStop()
        Log.i(LOG_INFO_TAG, LOG_STOP)
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

    override fun fireLogOut() {
        this.stashedDisk = SharedPrefTags.SHARED_PREFERENCES_NULL
        pref.write(SharedPrefTags.STATE_TOKEN_ON_DISK, stashedDisk)
        this@ViewDisplay.childFragmentManager.popBackStack()
        navCntrl?.navigate(pkg.what.pq.R.id.nav_fragment_a0_login)
    }

    private fun listenOnUiObservers(){
        vmDisplay.getUsers().observe(viewLifecycleOwner) {
            requireActivity().lifecycleScope.launch(Dispatchers.Main){
                bind.a0DisplayRv.adapter?.notifyItemRangeChanged(0,it.size,null)
            }
        }
        vmDisplay.getImages().observe(viewLifecycleOwner) {
            requireActivity().lifecycleScope.launch(Dispatchers.Main){
                bind.a0DisplayRv.adapter?.notifyItemRangeChanged(0,it.size,null)
            }
        }
    }

    private fun setupUi(){
        bind.a0DisplayRv.adapter =
            CardAdapter(this,vmDisplay.modelOfUsers.getData(),vmDisplay.modelOfImages.getData())
    }

    private fun setListeners(){
        bind.a0DisplaySignOutBtn.setOnClickListener { this.fireLogOut() }
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "A0_VIEW_DISPLAY_DEBUG_TAG"
        const val LOG_INFO_TAG = "A0_VIEW_DISPLAY_INFO_TAG"
    }
}