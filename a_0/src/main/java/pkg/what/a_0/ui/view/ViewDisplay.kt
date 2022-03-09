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
import pkg.what.a_0.data.model.CardAdapter
import pkg.what.a_0.domain.controller.ViewModelDisplay
import pkg.what.a_0.domain.core.constants.FragLcTags.LOG_CREATE_VIEW
import pkg.what.a_0.domain.core.constants.FragLcTags.LOG_VIEW_CREATED
import pkg.what.a_0.domain.core.di.DomainDi
import pkg.what.pq.databinding.LayoutA0DisplayBinding

class ViewDisplay : Fragment() {

    private lateinit var bind: LayoutA0DisplayBinding

    private var navCntrl: NavController? = null

    private lateinit var vmDisplay: ViewModelDisplay

    private fun di() {
        val domain = DomainDi()
        vmDisplay = ViewModelDisplay(
            //domain.getRemoteDataRepoImagesFromRoboHash(),
            domain.getRemoteDataRepoUsersFromTypiCode())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        di()
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

        listenOnUiObservers()
        setupUi()
    }

    private fun listenOnUiObservers(){
        vmDisplay.getUsers().observe(viewLifecycleOwner) { users ->
            users.forEach {
                vmDisplay.model.getData().add(it)
                bind.a0DisplayRv.adapter?.notifyDataSetChanged()
            }
        }
    }
    private fun setupUi(){
        bind.a0DisplayRv.adapter = CardAdapter(vmDisplay.model.getData())
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "A0_VIEW_DISPLAY_DEBUG_TAG"
        const val LOG_INFO_TAG = "A0_VIEW_DISPLAY_INFO_TAG"
    }
}