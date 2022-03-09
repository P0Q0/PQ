package pkg.what.a_0.ui.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import pkg.what.a_0.data.io.network.*
import pkg.what.a_0.data.model.CardAdapter
import pkg.what.a_0.data.model.DataModel
import pkg.what.a_0.domain.controller.ViewModelDisplay
import pkg.what.a_0.domain.core.constants.FragLcTags.LOG_CREATE_VIEW
import pkg.what.a_0.domain.core.constants.FragLcTags.LOG_VIEW_CREATED
import pkg.what.a_0.domain.core.di.DomainDi
import pkg.what.pq.databinding.LayoutA0DisplayBinding
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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
        setupData()
        setupUi()
    }

    private fun listenOnUiObservers(){
        vmDisplay.getUsers().observe(viewLifecycleOwner) { users ->
            //TODO:ViewDisplay, ui updates on observer
            Log.d(LOG_DEBUG_TAG, "$users, in ${javaClass.name}")
        }
    }

    private fun setupData(){
        //TODO: ViewDisplay, get the data, setupData
    }

    private fun setupUi(){
        //val source = DataSourceUsers() //TODO: ViewDisplay, package into a map of pairs, <ItemNumber, Pair<Source,Image>>
        //val images = DataSourceImages() //TODO: ViewDisplay, package into a map of pairs, <ItemNumber, Pair<Source,Image>>
        bind.a0DisplayRv.adapter = CardAdapter(0xFFFFFF) //todo: <--- change this back, after net io is fixed
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "A0_VIEW_DISPLAY_DEBUG_TAG"
        const val LOG_INFO_TAG = "A0_VIEW_DISPLAY_INFO_TAG"
    }
}