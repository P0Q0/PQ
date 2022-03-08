package pkg.what.a_0.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import pkg.what.pq.databinding.LayoutA0DisplayBinding

class ViewDisplay : Fragment() {

    private lateinit var bind: LayoutA0DisplayBinding

    private var navCntrl: NavController? = null

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View? {
        this.bind = LayoutA0DisplayBinding.inflate(layoutInflater, parent, false)
        Log.d(LOG_INFO_TAG,LOG_CREATE_VIEW)
        return bind.root
    }

    override fun onViewCreated(view: View, state: Bundle?) {
        super.onViewCreated(view, state)
        Log.d(LOG_INFO_TAG, LOG_VIEW_CREATED)
        this.navCntrl = Navigation.findNavController(view)
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "A0_VIEW_DISPLAY_DEBUG_TAG"
        const val LOG_INFO_TAG = "A0_VIEW_DISPLAY_INFO_TAG"
            /** @NOTE Fragment lifecycle as of API 30 */
            const val LOG_ATTACH = "ON_ATTACH"
            const val LOG_CREATE_VIEW = "ON_CREATE_VIEW"
            const val LOG_VIEW_CREATED = "ON_VIEW_CREATED"
            const val LOG_VIEW_STATE_RESTORED = "ON_VIEW_STATE_RESTORED"
            const val LOG_START = "ON_START"
            const val LOG_RESUME = "ON_RESUME"
            const val LOG_PAUSE = "ON_PAUSE"
            const val LOG_STOP = "ON_STOP"
            const val LOG_SAVE_INSTANCE_STATE = "ON_SAVE_INSTANCE_STATE"
            const val LOG_DESTROY_VIEW = "ON_DESTROY_VIEW"
            const val LOG_DESTROY = "ON_DESTROY"
            const val LOG_DETACH = "ON_DETACH"
    }
}