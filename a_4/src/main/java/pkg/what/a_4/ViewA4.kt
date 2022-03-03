package pkg.what.a_4

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.a_4.ControllerA4.ENDPOINTS.BASE_ROBOHASH_ENDPOINT
import pkg.what.a_4.ControllerA4.ENDPOINTS.BASE_TYPICODE_ENDPOINT
import pkg.what.a_4.ControllerA4.ENDPOINTS.IMAGES_PATH
import pkg.what.a_4.ControllerA4.ENDPOINTS.USERS_PATH
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA4Binding

class ViewA4 : AppCompatActivity() {
    private lateinit var bind: LayoutA4Binding

    private val cntrl: ControllerA4 by lazy { ControllerA4() }

    private val model: ModelA4 by lazy { ModelA4() }

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutA4Binding.inflate(layoutInflater).also { setContentView(it.root) }
        Snackbar.make(
            findViewById(R.id.layout_a4)
            ,getString(R.string.purpose_a4)
            ,Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_DEBUG_TAG,"$localClassName , ${resources.getString(R.string.sb_on_click)}") }
            .show()
        setListeners()
    }

    private fun setListeners(){
        bind.a4NetReqUsersBtn.setOnClickListener { netGetUsersData() }
        bind.a4NetReqImgBtn.setOnClickListener { netGetImgData() }
    }

    private fun netGetUsersData(){
        cntrl.request(BASE_TYPICODE_ENDPOINT + USERS_PATH).also { cntrl.fireTypicodeCall(it,this,model) }
    }

    private fun netGetImgData(){
        cntrl.request(BASE_ROBOHASH_ENDPOINT + IMAGES_PATH).also { cntrl.fireRobohashCall(it,this,model) }
    }

    fun update(data: ModelA4.UserModel) {
        val id = data.id.toString()
        val name = data.name.toString()
        val display = "$id $name"
        bind.a4NetworkDataMtv.text = display

        Snackbar.make(
            findViewById(R.id.layout_a4)
            , "$LOG_NET_CALLED and returned a ModelA4.UserModel"
            , Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_DEBUG_TAG,"$localClassName , ${resources.getString(R.string.sb_on_click)}") }
            .show()
    }

    fun update(data: ModelA4.ImageModel){
        //TODO update for ImageModel

        Snackbar.make(
            findViewById(R.id.layout_a4)
            , "$LOG_NET_CALLED and returned a ModelA4.ImageModel"
            , Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_DEBUG_TAG,"$localClassName , ${resources.getString(R.string.sb_on_click)}") }
            .show()
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "VIEW_A4_DEBUG_TAG"
        const val LOG_NET_CALLED = "after network call succeeded"
    }
}