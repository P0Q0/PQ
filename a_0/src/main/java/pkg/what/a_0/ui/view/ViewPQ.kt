package pkg.what.a_0.ui.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import pkg.what.a_0.domain.controller.ViewModelPQ
import pkg.what.a_0.ui.messages.Snacks
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA0Binding

class ViewPQ : AppCompatActivity() {
    private lateinit var bind: LayoutA0Binding

    /** @STRATEGY_FOR_INVOKING_WORKER
     * 0) this activity is omnipotent for this application
     * 1) fragment is in the background explicitly, activity is in the background implicitly
     * 2) fragment is in the foreground, activity is in the foreground implicitly
     * 3) fragment is in process death, activity is in process life
     * 4) activity is in destroyed */
    private lateinit var vmPQ: ViewModelPQ

    private fun di() {
        vmPQ = ViewModelPQ(this.applicationContext)
    }

    private var path: String? = null

    private lateinit var host: NavHostFragment

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)

        di()

        Log.d(LOG_DEBUG_TAG,"$javaClass , onCreate")
        this.bind = LayoutA0Binding.inflate(layoutInflater).also { setContentView(it.root) }

        Snacks(bind.root.rootView, getString(R.string.purpose_a0), Snackbar.LENGTH_LONG,this.javaClass)

        host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        if(intent.hasExtra("KEY_DESTINATION")) {
            path = intent.getStringExtra("KEY_DESTINATION")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(LOG_INFO_TAG,"$javaClass , onStart")

        if(path != null){
            when (path) {
                "ViewLogin" -> {
                    supportFragmentManager.beginTransaction().replace(host.id,ViewLogin()).commit()
                }
                "ViewDisplay" -> {
                    supportFragmentManager.beginTransaction().replace(host.id,ViewDisplay()).commit()
                }
                "ViewProfile" -> {
                    supportFragmentManager.beginTransaction().replace(host.id,ViewProfile()).commit()
                }
            }
            val controller = Navigation.findNavController(host.requireView())
            Navigation.setViewNavController(host.requireView(),controller)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(LOG_INFO_TAG,"$javaClass , onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(LOG_INFO_TAG,"$javaClass , onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(LOG_INFO_TAG,"$javaClass , onStop")
        vmPQ.loadOnStopWork(this)
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(LOG_INFO_TAG,"$javaClass , onRestart")
    }

    override fun onDestroy() {
        var stash = ""
        host.view?.let lift@ { that ->
                stash = Navigation.findNavController(that)
                    .currentDestination?.label.toString()
                        .substringAfter(':')
                return@lift
            }
        super.onDestroy()
        Log.i(LOG_INFO_TAG,"$javaClass , onDestroy")
        vmPQ.loadOnDestroyWork(this,stash)
    }

    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        Log.i(LOG_INFO_TAG,"$javaClass , onSaveInstanceState")
    }

    override fun onRestoreInstanceState(state: Bundle) {
        super.onRestoreInstanceState(state)
        Log.i(LOG_INFO_TAG,"$javaClass , onRestoreInstanceState")
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "VIEW_PQ_A0_DEBUG_TAG"
        const val LOG_INFO_TAG = "VIEW_PQ_A0_INFO_TAG"
    }
}