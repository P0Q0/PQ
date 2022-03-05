package pkg.what.a_4

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        snack(
            R.string.purpose_a4
            ,"$localClassName , ${resources.getString(R.string.sb_on_click)}")
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
        cntrl.fireRobohashCall(BASE_ROBOHASH_ENDPOINT + IMAGES_PATH,this,model)
    }

    /** @param [data] is a false-positive, it is null and should remain null, this is a real lazy way to fx overload
     * , an alternative would be another fx name, but doing it like this semantics feel more intuitive, suppress for this */
    @Suppress("UNUSED_PARAMETER")
    fun update(data: ModelA4.UserModel?) {
        var display = ""
        var debug = ""
        this.model.dataOfUsers.forEach {
            val id = it.value.id
            val name = it.value.name
            display += "$id $name"
            debug += "${id.toString()} ${name.toString()}"
        }
        this.lifecycleScope.launch(Dispatchers.Main) {
            bind.a4NetworkDataMtv.text = display
            snack("$LOG_USER_MODEL  $debug")
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun update(data: ModelA4.ImageModel?){
        this.lifecycleScope.launch(Dispatchers.Main) {
            bind.a4NetworkImgIv.setImageBitmap(model.dataOfImages[IMAGE_ITEM])
            snack(LOG_IMAGE_MODEL)
        }
        snack(LOG_IMAGE_MODEL)
    }

    private fun snack(who: String) = Snackbar.make(
        findViewById(R.id.layout_a4)
        , "$LOG_NET_CALLED and returned a $who"
        , Snackbar.LENGTH_SHORT)
        .setTextColor(getColor(R.color.colorLight))
        .setAction(getString(R.string.sb_dismiss))
        { Log.d(LOG_DEBUG_TAG,"$localClassName , ${resources.getString(R.string.sb_on_click)}") }
        .show()

    /** @desc file specific for short snackbar */
    private fun snack(ui_msg: Int, log_msg: String) =
        Snackbar.make(
            findViewById(R.id.layout_a4)
            ,getString(ui_msg)
            , Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_INFO_TAG,log_msg) }
            .show()

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "VIEW_A4_DEBUG_TAG"
        const val LOG_INFO_TAG = "VIEW_A4_INFO_TAG"
        const val LOG_NET_CALLED = "after network call succeeded"
        const val LOG_USER_MODEL = "ModelA4.UserModel"
        const val LOG_IMAGE_MODEL = "ModelA4.ImageModel"
        const val IMAGE_ITEM = 0
    }
}