package pkg.what.a_2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA2Binding
import java.io.*
import android.Manifest.permission.READ_EXTERNAL_STORAGE as READ
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE as WRITE
import android.content.pm.PackageManager.PERMISSION_DENIED as DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED as GRANTED


class ViewA2 : AppCompatActivity() {
    private lateinit var bind: LayoutA2Binding

    /** @desc defines storage permissions status */
    private var permissions: MutableMap<String,Boolean> = mutableMapOf()

    /** @desc defines storage read access permission status */
    private var read: Boolean = false

    /** @desc defines storage write access permission status */
    private var write: Boolean = false

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutA2Binding.inflate(layoutInflater).also { setContentView(it.root) }
        this.permissions[PERMISSION_EXTERNAL_STORAGE_READ] = read
        this.permissions[PERMISSION_EXTERNAL_STORAGE_WRITE] = write
        snack(R.string.purpose_a2
            ,"$localClassName , ${resources.getString(R.string.sb_on_click)}")
        setUiData()
        setListeners()
    }

    private fun setUiData() {
        var q = ""
        for(p in permissions) {
            q += (p.toString() + System.lineSeparator())
        }
        this.bind.a2PermissionsMtv.text = q
    }

    /** @note for the purpose of demonstration, run the update using a separate unique thread on the ui thread,
     * , but do note this is not common and this will require processing and blocking of the ui thread
     * , in particular this is discouraged and practically bad because it will cause a "sluggish" experience
     * , so the alternative is an asynchronous forms such as observers, coroutines, or livedata, to update the ui*/
    private fun updateUiData(who: String, status: Boolean){
        if(who == READ){ permissions[PERMISSION_EXTERNAL_STORAGE_READ] = status }
        if(who == WRITE){ permissions[PERMISSION_EXTERNAL_STORAGE_WRITE] = status }
        Thread {
            runOnUiThread {
                Log.i(LOG_DEBUG_TAG, "runOnUiThread")
                setUiData()
                Thread.sleep(1000)
            }
        }.start()
    }

    private fun setListeners(){
        this.bind.a2PermissionBtn1.setOnClickListener {
            fireUiListenForRequestPermissions()
            workflow()
        }
    }

    override fun onRequestPermissionsResult(REQ: Int, PER: Array<out String>, RES: IntArray) {
        super.onRequestPermissionsResult(REQ, PER, RES)
        runtimePermissionsState()
        when(REQ){
            PERMISSION_REQUEST_STORAGE_READ -> {
                if(RES.isNotEmpty()){
                    if(RES[0] == GRANTED){
                        updateUiData(READ,true)
                        //granted state
                        snack(R.string.sb_permission_granted
                            ,"$localClassName , $PERMISSIONS_ON_RESULT , $GRANTED")
                        //DUMMY DATA TO READ FROM
                        if(!hasExternalStoragePrivateFile()) this.createExternalStoragePrivateFile()
                        //READ FROM EXTERNAL STORAGE IN PERSISTENT DIRECTORY
                        val external = this.getExternalFilesDir(null)
                        val file = File(external,"")
                        try {
                            val fis = FileInputStream(file)
                            val isr = InputStreamReader(fis)
                            val br = BufferedReader(isr)
                            br.close()
                            isr.close()
                            fis.close()
                            Log.d(LOG_DEBUG_TAG, "$FILE_IO_READ_SUCCESS $br")
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Log.d(LOG_DEBUG_TAG, FILE_IO_READ_FAILURE, e)
                        }
                    } else if(RES[0] == DENIED){
                        updateUiData(READ,false)
                        //denied state
                        snack(R.string.sb_permission_denied
                            ,"$localClassName , $PERMISSIONS_ON_RESULT , $DENIED")
                    }
                    else{
                        //unknown state
                        snack(R.string.sb_permission_unknown
                            ,"$localClassName , $PERMISSIONS_ON_RESULT , $UNKNOWN_STATE")
                    }
                }
            }
            PERMISSION_REQUEST_STORAGE_WRITE -> {
                if(RES.isNotEmpty()){
                    if(RES[0] == GRANTED){
                        updateUiData(WRITE,true)
                        //granted state
                        snack(R.string.sb_permission_granted
                            ,"$localClassName , $PERMISSIONS_ON_RESULT , $GRANTED")
                        //WRITE TO EXTERNAL STORAGE IN PERSISTENT DIRECTORY
                        val external = this.getExternalFilesDir(null)
                        val file = File(external,"")
                        try {
                            val fos = FileOutputStream(file)
                            fos.write(TEST_DATA.toByteArray(Charsets.UTF_8))
                            fos.close()
                            Log.d(LOG_DEBUG_TAG,FILE_IO_WRITE_SUCCESS)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Log.d(LOG_DEBUG_TAG, FILE_IO_WRITE_FAILURE, e)
                        }

                    } else if(RES[0] == DENIED){
                        updateUiData(WRITE,false)
                        //denied state
                        snack(R.string.sb_permission_denied
                            ,"$localClassName , $PERMISSIONS_ON_RESULT , $DENIED")
                    } else{
                        //unknown state
                        snack(R.string.sb_permission_unknown
                            ,"$localClassName , $PERMISSIONS_ON_RESULT , $UNKNOWN_STATE")
                    }
                }
            }
            else -> { Log.d(LOG_DEBUG_TAG,"unsupported, REQ: $REQ, PER: $PER, RES: $RES .")}
        }
        firePermissionsFlow()
    }

    private fun hasExternalStoragePrivateFile(): Boolean {
        return File(getExternalFilesDir(null), TEST_FILE).exists()
    }

    private fun createExternalStoragePrivateFile() {
        val file = File(getExternalFilesDir(null), TEST_FILE)
        try {
            val fos = FileOutputStream(file)
            fos.write(TEST_DATA.toByteArray(Charsets.UTF_8))
            fos.close()
        } catch (e: IOException) {
            Log.w(LOG_DEBUG_TAG, "$FILE_IO_WRITE_FAILURE $file", e)
        }
    }

    /** @desc workflow for permissions
     * 1) define permissions in manifest (explicit)
     * 2) define ui element to invoke permissions workflow, "workflow()" (explicit)
     * 3) define ui listener on element to request permissions, "fireUiListenForRequestPermissions()" (explicit)
     * 4) define logical check to determine permissions state, "checkPermissions()" (implicit)
     * 5) define ui element to communicate permissions rationale "fireRationale()" (implicit)
     * 6) define runtime permissions request "runtimePermissionsRequest()" (implicit)
     * 7) define runtime permissions approval "runtimePermissionsState()" (explicit)
     * 8) define logical program flow on permissions grant state "firePermissionsFlow()" (implicit)
     */
    private fun workflow(){
        Log.d(LOG_DEBUG_TAG,PERMISSIONS_WORKFLOW)
        checkPermissions()
        firePermissionsFlow()
    }//2

    private fun fireUiListenForRequestPermissions(){
        Log.d(LOG_DEBUG_TAG,PERMISSIONS_UI_LISTEN)
    }//3

    private fun checkPermissions(){
        Log.d(LOG_DEBUG_TAG,PERMISSIONS_CHECK)
        if(ActivityCompat.checkSelfPermission(this, READ) == GRANTED){
            snack(R.string.sb_permission_read_has_been_granted
                ,"$localClassName , $CHECK_READ_EXISTS")
        } else{
            fireRationale(READ)
        }
        if(ActivityCompat.checkSelfPermission(this,WRITE) == GRANTED){
            snack(R.string.sb_permission_write_has_been_granted
                ,"$localClassName , $CHECK_WRITE_EXISTS")
        } else{
            fireRationale(WRITE)
        }
    }//4

    private fun fireRationale(p : String){
        Log.d(LOG_DEBUG_TAG,PERMISSIONS_RATIONALE)
        when(p) {
            READ -> { runtimePermissionsRequest(READ) }
            WRITE -> { runtimePermissionsRequest(WRITE) }
            else -> { Log.d(LOG_DEBUG_TAG,"$PERMISSIONS_UNSUPPORTED_RATIONALE in $javaClass")}
        }
    }//5

    private fun runtimePermissionsRequest(p: String){
        Log.d(LOG_DEBUG_TAG,PERMISSIONS_REQUEST)
        when(p) {
            READ -> {
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,READ)){
                    snack(R.string.sb_permission_read_has_been_granted, arrayOf(READ), PERMISSION_REQUEST_STORAGE_READ)
                } else {
                    snack(R.string.sb_permission_storage_read_required
                        ,"$localClassName , $PERMISSIONS_REQUEST , LOG_INFO READ.")
                    ActivityCompat.requestPermissions(this,arrayOf(READ),PERMISSION_REQUEST_STORAGE_READ)
                }
            }
            WRITE -> {
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,WRITE)){
                    snack(R.string.sb_permission_write_has_been_granted, arrayOf(WRITE), PERMISSION_REQUEST_STORAGE_WRITE)
                } else {
                    snack(R.string.sb_permission_storage_write_required
                        ,"$localClassName , $PERMISSIONS_REQUEST , LOG_INFO WRITE.")
                    ActivityCompat.requestPermissions(this,arrayOf(WRITE),PERMISSION_REQUEST_STORAGE_WRITE)
                }

            }
            else -> { Log.d(LOG_DEBUG_TAG,"$PERMISSIONS_UNSUPPORTED_REQUEST in $javaClass")}
        }
    }//6

    private fun runtimePermissionsState(){
        Log.d(LOG_DEBUG_TAG,PERMISSIONS_STATE)
    }//7

    private fun firePermissionsFlow(){
        Log.d(LOG_DEBUG_TAG, PERMISSIONS_FLOW)
    }//8

    /** @desc file specific for short snackbar */
    private fun snack(ui_msg: Int, log_msg: String) =
        Snackbar.make(
            findViewById(R.id.layout_a2)
            ,getString(ui_msg)
            ,Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_INFO_TAG,log_msg) }
            .show()

    /** @desc file specific for indefinite snackbar */
    private fun snack(ui_msg: Int, per: Array<String>, req: Int) =
        Snackbar.make(
            findViewById(R.id.layout_a2)
            ,getString(ui_msg)
            ,Snackbar.LENGTH_INDEFINITE)
            .setTextColor(getColor(R.color.colorLight))
            .setAction(getString(R.string.sb_dismiss)) {
                ActivityCompat.requestPermissions(this,per,req)
            }.show()

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "VIEW_A2_DEBUG_TAG"
        const val LOG_INFO_TAG = "VIEW_A2_INFO_TAG"
        const val PERMISSION_EXTERNAL_STORAGE_READ = "PERMISSION_EXTERNAL_STORAGE_READ"
        const val PERMISSION_EXTERNAL_STORAGE_WRITE = "PERMISSION_EXTERNAL_STORAGE_WRITE"
        const val PERMISSION_REQUEST_STORAGE_READ = 0
        const val PERMISSION_REQUEST_STORAGE_WRITE = 1
        const val UNKNOWN_STATE = "UNKNOWN_STATE"
        const val PERMISSIONS_ON_RESULT = "onRequestPermissionsResult(...)"
        const val PERMISSIONS_WORKFLOW = "permissions: workflow()"
        const val PERMISSIONS_UI_LISTEN = "permissions: fireUiListenForRequestPermissions()"
        const val PERMISSIONS_CHECK = "permissions: checkPermissions()"
        const val CHECK_READ_EXISTS = "permissions: checkPermissions() READ exists"
        const val CHECK_WRITE_EXISTS = "permissions: checkPermissions() WRITE exists"
        const val PERMISSIONS_RATIONALE = "permissions: fireRationale()"
        const val PERMISSIONS_REQUEST = "permissions: runtimePermissionsRequest(...)"
        const val PERMISSIONS_STATE = "permissions: runtimePermissionsState()"
        const val PERMISSIONS_FLOW = "permissions: firePermissionsFlow()"
        const val PERMISSIONS_UNSUPPORTED_RATIONALE = "unsupported permissions rationale request"
        const val PERMISSIONS_UNSUPPORTED_REQUEST = "unsupported permissions runtime request"
        const val FILE_IO_READ_SUCCESS = "Read from file successful."
        const val FILE_IO_READ_FAILURE = "Read from file failure."
        const val FILE_IO_WRITE_SUCCESS = "Write to file successful."
        const val FILE_IO_WRITE_FAILURE = "Write to file failure."
        const val TEST_DATA = "TEST_WRITE_STRING_OF_DATA_IN_VIEW_A2"
        const val TEST_FILE = "TEST_FILE_OF_DATA_IN_VIEW_A2.txt"
    }
}
