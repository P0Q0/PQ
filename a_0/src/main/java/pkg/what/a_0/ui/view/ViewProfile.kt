package pkg.what.a_0.ui.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import pkg.what.a_0.domain.controller.ViewModelProfile
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
import pkg.what.a_0.domain.core.constant.ProfileTags
import pkg.what.pq.databinding.LayoutA0ProfileBinding

class ViewProfile : Fragment() {

    /**@note this is a non-Gg app, non-Gg apps should not be updating Gg profile information */
    //TODO: CardAdapter, for demonstration, add ui edit field, to update remote data, but a brief note

    private lateinit var bind: LayoutA0ProfileBinding

    private var navCntrl: NavController? = null

    private lateinit var vmProfile: ViewModelProfile

    private fun di() {
        vmProfile = ViewModelProfile(super.requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        di()
        Log.d(LOG_INFO_TAG, LOG_ATTACH)
    }

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        Log.d(LOG_INFO_TAG, LOG_CREATE)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View? {
        this.bind = LayoutA0ProfileBinding.inflate(layoutInflater, parent, false)
        Log.d(LOG_INFO_TAG, LOG_CREATE_VIEW)
        setUi()
        return bind.root
    }

    override fun onViewCreated(view: View, state: Bundle?) {
        super.onViewCreated(view, state)
        Log.d(LOG_INFO_TAG, LOG_VIEW_CREATED)
        this.navCntrl = Navigation.findNavController(view)
    }

    private fun setUi(){
        bind.profileImageUi.text =
            arguments?.get(ProfileTags.TAG_IMGURL).toString()
        bind.profileNameUi.text =
            getString(pkg.what.pq.R.string.profile_name).plus(arguments?.get(ProfileTags.TAG_NAME).toString())
        bind.profileEmailUi.text =
            getString(pkg.what.pq.R.string.profile_email).plus(arguments?.get(ProfileTags.TAG_EMAIL).toString())
        bind.profileIdUi.text =
            getString(pkg.what.pq.R.string.profile_id).plus(arguments?.get(ProfileTags.TAG_ID).toString())
        bind.profilePhoneUi.text =
            getString(pkg.what.pq.R.string.profile_phone).plus(arguments?.get(ProfileTags.TAG_PHONE).toString())
        bind.profileEmployerUi.text =
            getString(pkg.what.pq.R.string.profile_employer).plus(arguments?.get(ProfileTags.TAG_EMPLOYER).toString())
        bind.profileCityUi.text =
            getString(pkg.what.pq.R.string.profile_city).plus(arguments?.get(ProfileTags.TAG_CITY).toString())
        bind.profileZipUi.text =
            getString(pkg.what.pq.R.string.profile_zip).plus(arguments?.get(ProfileTags.TAG_ZIP).toString())

        Log.d(LOG_DEBUG_TAG, "$javaClass : ${arguments?.get(ProfileTags.TAG_WEBTKN).toString()}")
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
        Log.i(LOG_INFO_TAG, LOG_DESTROY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(LOG_INFO_TAG, LOG_DESTROY_VIEW)
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(LOG_INFO_TAG, LOG_DETACH)
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object {
        const val LOG_DEBUG_TAG = "A0_VIEW_PROFILE_DEBUG_TAG"
        const val LOG_INFO_TAG = "A0_VIEW_PROFILE_INFO_TAG"
    }
}