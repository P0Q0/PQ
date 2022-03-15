package pkg.what.a_0.ui.media

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import pkg.what.a_0.domain.core.constant.FragLcTags
import pkg.what.pq.databinding.CameraxLayoutBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

typealias LumaListener = (luma: Double) -> Unit

class ViewCameraX : Fragment() {

    private lateinit var bind: CameraxLayoutBinding

    private var navCntrl: NavController? = null

    private var imgCapture: ImageCapture? = null

    private var vidCapture: VideoCapture<Recorder>? = null

    private var recCapture: Recording? = null

    private lateinit var cameraXExecutor: ExecutorService

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(LOG_INFO_TAG, FragLcTags.LOG_ATTACH)
    }

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        register()
        Log.d(LOG_INFO_TAG, FragLcTags.LOG_CREATE)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View? {
        this.bind = CameraxLayoutBinding.inflate(layoutInflater, parent, false)
        Log.d(LOG_INFO_TAG, FragLcTags.LOG_CREATE_VIEW)
        permissions()
        return bind.root
    }

    override fun onViewCreated(view: View, state: Bundle?) {
        super.onViewCreated(view, state)
        Log.d(LOG_INFO_TAG, FragLcTags.LOG_VIEW_CREATED)
        this.navCntrl = Navigation.findNavController(view)
        setListeners()

        cameraXExecutor = Executors.newSingleThreadExecutor()
    }

    private fun permissions(){
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            Toast.makeText(requireContext(),
                "Permissions not granted by the user.",
                Toast.LENGTH_SHORT).show()
            this.requireActivity().finish()
        }
    }

    private fun register(){
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                Log.d("$javaClass","G:permissions_status: $granted")
            } else {
                Log.d("$javaClass","D:permissions_status: $granted")
            }
        }

        if (allPermissionsGranted()) {
            Log.d("$javaClass","starting camera")
            startCamera()
        } else {
            Log.d("$javaClass","permission_check: Manifest.permission.CAMERA")
            requestPermissionLauncher.launch(
                Manifest.permission.CAMERA
            )
            Log.d("$javaClass","permission_check: Manifest.permission.RECORD_AUDIO")
            requestPermissionLauncher.launch(
                Manifest.permission.RECORD_AUDIO
            )
        }
    }

    private fun setListeners(){
        bind.cameraxPreviewView.setOnClickListener {
            Log.d("$javaClass","cameraxPreviewView.setOnClickListener")
        }
        bind.cameraxImageCaptureButton.setOnClickListener {
            Log.d("$javaClass","cameraxImageCaptureButton.setOnClickListener")
        }
        bind.cameraxVideoCaptureButton.setOnClickListener {
            Log.d("$javaClass","cameraxVideoCaptureButton.setOnClickListener")
        }
    }

    override fun onViewStateRestored(state: Bundle?) {
        super.onViewStateRestored(state)
        Log.i(LOG_INFO_TAG, FragLcTags.LOG_VIEW_STATE_RESTORED)
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOG_INFO_TAG, FragLcTags.LOG_START)
    }

    override fun onResume() {
        super.onResume()
        Log.i(LOG_INFO_TAG, FragLcTags.LOG_RESUME)
    }

    override fun onPause() {
        super.onPause()
        Log.i(LOG_INFO_TAG, FragLcTags.LOG_PAUSE)
    }

    override fun onStop() {
        super.onStop()
        Log.i(LOG_INFO_TAG, FragLcTags.LOG_STOP)
    }

    override fun onSaveInstanceState(state: Bundle) {
        Log.i(LOG_INFO_TAG, FragLcTags.LOG_SAVE_INSTANCE_STATE)
        super.onSaveInstanceState(state)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG_INFO_TAG, FragLcTags.LOG_DESTROY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(LOG_INFO_TAG, FragLcTags.LOG_DESTROY_VIEW)
    }

    override fun onDetach() {
        super.onDetach()
        cameraXExecutor.shutdown()
        Log.i(LOG_INFO_TAG, FragLcTags.LOG_DETACH)
    }

    private fun takePhoto() {}

    private fun captureVideo() {}

    private fun startCamera() {}

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object {
        const val LOG_DEBUG_TAG = "A0_VIEW_CAMERAX_DEBUG_TAG"
        const val LOG_INFO_TAG = "A0_VIEW_CAMERAX_INFO_TAG"
        private const val TAG = "a_0" //pkg.what.pq.ApplicationPQ
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}