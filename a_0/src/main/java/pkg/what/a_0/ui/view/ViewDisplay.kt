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
import pkg.what.a_0.domain.controller.ViewModelDisplay
import pkg.what.a_0.domain.core.constants.FragLcTags.LOG_CREATE_VIEW
import pkg.what.a_0.domain.core.constants.FragLcTags.LOG_VIEW_CREATED
import pkg.what.pq.databinding.LayoutA0DisplayBinding

class ViewDisplay : Fragment() {

    private lateinit var bind: LayoutA0DisplayBinding

    private var navCntrl: NavController? = null

    private val vmDisplay: ViewModelDisplay by viewModels()

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View? {
        this.bind = LayoutA0DisplayBinding.inflate(layoutInflater, parent, false)
        Log.d(LOG_INFO_TAG, LOG_CREATE_VIEW)
        return bind.root
    }

    override fun onViewCreated(view: View, state: Bundle?) {
        super.onViewCreated(view, state)
        Log.d(LOG_INFO_TAG, LOG_VIEW_CREATED)
        this.navCntrl = Navigation.findNavController(view)

        listenOnUiObservers()
    }

    private fun listenOnUiObservers(){
        vmDisplay.getUsers().observe(viewLifecycleOwner) { users ->
            //TODO:ViewDisplay, ui updates on observer
            Log.d(LOG_DEBUG_TAG, "$users, in ${javaClass.name}")
        }
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "A0_VIEW_DISPLAY_DEBUG_TAG"
        const val LOG_INFO_TAG = "A0_VIEW_DISPLAY_INFO_TAG"
    }
}