package pkg.what.a_0.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import pkg.what.a_0.domain.controller.ViewModelLogin
import pkg.what.a_0.domain.core.constants.FragLcTags.LOG_CREATE_VIEW
import pkg.what.a_0.domain.core.constants.FragLcTags.LOG_VIEW_CREATED
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA0LoginBinding

class ViewLogin : Fragment() {

    private lateinit var bind: LayoutA0LoginBinding

    private var navCntrl: NavController? = null

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
    }

    private fun navigate() =
        this.requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.layout_a0_login, ViewDisplay()::class.java, Bundle())
            .commit()

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "A0_VIEW_LOGIN_DEBUG_TAG"
        const val LOG_INFO_TAG = "A0_VIEW_LOGIN_INFO_TAG"
    }
}