package pkg.what.a_0.ui.media

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageProxy
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import pkg.what.a_0.domain.core.constant.FragLcTags
import pkg.what.pq.databinding.CameraxLayoutBinding
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.Locale

typealias LumaListener = (luma: Double) -> Unit

class ViewCameraX : Fragment() {

    private lateinit var bind: CameraxLayoutBinding

    private var navCntrl: NavController? = null

    private var recCapture: Recording? = null

    private var imageCapture: ImageCapture? = null

    private var videoCapture: VideoCapture<Recorder>? = null

    private var requestPermissionLauncher: ActivityResultLauncher<String>? = null

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
        prePermissions()
        return bind.root
    }

    override fun onViewCreated(view: View, state: Bundle?) {
        super.onViewCreated(view, state)
        Log.d(LOG_INFO_TAG, FragLcTags.LOG_VIEW_CREATED)
        this.navCntrl = Navigation.findNavController(view)
        setListeners()

        cameraXExecutor = Executors.newSingleThreadExecutor()
    }

    private fun prePermissions(){
        if (allPermissionsGranted()) {
            Log.d("$javaClass","starting camera")
            startCameraForImageAnalysis()
        } else {
            Toast.makeText(requireContext(),
                "Permissions not granted by the user.",
                Toast.LENGTH_SHORT).show()
            this.requireActivity().finish()
        }
    }

    private fun postPermissions(useCaseFlag : Int){
        when(useCaseFlag) {
            USE_CASE_CAMERA_ANALYSIS_IMAGE -> {
                if (allPermissionsGranted()) {
                    Log.d("$javaClass","starting camera")
                    startCameraForImageAnalysis()
                } else {
                    Log.d("$javaClass","permission_check: Manifest.permission.CAMERA")
                    requestPermissionLauncher?.launch(Manifest.permission.CAMERA)
                    Log.d("$javaClass","permission_check: Manifest.permission.RECORD_AUDIO")
                    requestPermissionLauncher?.launch(Manifest.permission.RECORD_AUDIO)
                }
            }
            USE_CASE_CAMERA_CAPTURE_IMAGE -> {
                if (allPermissionsGranted()) {
                    Log.d("$javaClass","starting camera")
                    startCameraForImageCapture()
                } else {
                    Log.d("$javaClass","permission_check: Manifest.permission.CAMERA")
                    requestPermissionLauncher?.launch(Manifest.permission.CAMERA)
                    Log.d("$javaClass","permission_check: Manifest.permission.RECORD_AUDIO")
                    requestPermissionLauncher?.launch(Manifest.permission.RECORD_AUDIO)
                }
            }
            USE_CASE_CAMERA_CAPTURE_VIDEO -> {
                if (allPermissionsGranted()) {
                    Log.d("$javaClass","starting camera")
                    startCameraForVideCapture()
                } else {
                    Log.d("$javaClass","permission_check: Manifest.permission.CAMERA")
                    requestPermissionLauncher?.launch(Manifest.permission.CAMERA)
                    Log.d("$javaClass","permission_check: Manifest.permission.RECORD_AUDIO")
                    requestPermissionLauncher?.launch(Manifest.permission.RECORD_AUDIO)
                }
            }
        }
    }

    private fun register(){
        this.requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                Log.d("$javaClass","G:permissions_status: $granted")
            } else {
                Log.d("$javaClass","D:permissions_status: $granted")
            }
        }
    }

    private fun setListeners(){
        bind.cameraxPreviewView.setOnClickListener {
            Log.d("$javaClass","cameraxPreviewView.setOnClickListener")
        }
        bind.cameraxImageCaptureButton.setOnClickListener {
            Log.d("$javaClass","cameraxImageCaptureButton.setOnClickListener")
            postPermissions(USE_CASE_CAMERA_CAPTURE_IMAGE)
            this.takePhoto()
        }
        bind.cameraxVideoCaptureButton.setOnClickListener {
            Log.d("$javaClass","cameraxVideoCaptureButton.setOnClickListener")
            postPermissions(USE_CASE_CAMERA_CAPTURE_VIDEO)
            this.captureVideo()
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
        requestPermissionLauncher = null
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

    private fun startCameraForImageCapture() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext().applicationContext)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also { it.setSurfaceProvider(bind.cameraxPreviewView.surfaceProvider) }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                useCaseForImageCapture(cameraProvider,cameraSelector,preview)
            } catch(e: Exception) {
                Log.e(TAG, "Use case binding failed: $e")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun startCameraForVideCapture() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext().applicationContext)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also { it.setSurfaceProvider(bind.cameraxPreviewView.surfaceProvider) }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                useCaseForVideoCapture(cameraProvider,cameraSelector,preview)
            } catch(e: Exception) {
                Log.e(TAG, "Use case binding failed: $e")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun startCameraForImageAnalysis() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext().applicationContext)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also { it.setSurfaceProvider(bind.cameraxPreviewView.surfaceProvider) }
            imageCapture = ImageCapture.Builder().build()
            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraXExecutor, LuminosityAnalyzer { luma ->
                        Log.d(TAG, "Average luminosity: $luma") })
                }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                useCaseForImageAnalysis(cameraProvider,cameraSelector,imageAnalyzer,preview)
            } catch(e: Exception) {
                Log.e(TAG, "Use case binding failed: $e")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun useCaseForImageCapture(
        provider: ProcessCameraProvider
        , cameraSelector: CameraSelector
        , preview: Preview) {
            provider.bindToLifecycle(
                this
                , cameraSelector
                , preview
                , imageCapture
            )
    }

    private fun useCaseForImageAnalysis(
        provider: ProcessCameraProvider
        , cameraSelector: CameraSelector
        , analysisSelector: ImageAnalysis
        , preview: Preview) {
            provider.bindToLifecycle(
                this
                , cameraSelector
                , preview
                , analysisSelector
                , imageCapture
            )
    }

    private fun useCaseForVideoCapture(
        provider: ProcessCameraProvider
        , cameraSelector: CameraSelector
        , preview: Preview) {
            provider.bindToLifecycle(
                this
                , cameraSelector
                , preview
                , videoCapture
            )
    }

    private fun takePhoto() {
        val imgCapture = this.imageCapture ?: return
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                    .format(System.currentTimeMillis())
        val contentValues = ContentValues()
                .apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
                        }
                }
        val outputOptions = ImageCapture.OutputFileOptions
                                .Builder(requireContext().contentResolver,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                                        .build()

        imgCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(e: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${e.message}", e)
                }
                override fun onImageSaved(output: ImageCapture.OutputFileResults){
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            }
        )
    }

    private fun captureVideo() {
        val vidCapture = this.videoCapture ?: return

        bind.cameraxVideoCaptureButton.isEnabled = false

        if (recCapture != null) {
            recCapture!!.stop()
            recCapture = null
            return
        }
        
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/CameraX-Video")
            }
        }

        val mediaStoreOutputOptions = MediaStoreOutputOptions
            .Builder(requireContext().contentResolver, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            .setContentValues(contentValues)
            .build()
        recCapture = vidCapture.output
            .prepareRecording(requireContext(), mediaStoreOutputOptions)
            .apply {
                if (PermissionChecker.checkSelfPermission(requireActivity(),
                        Manifest.permission.RECORD_AUDIO) ==
                    PermissionChecker.PERMISSION_GRANTED)
                {
                    withAudioEnabled()
                }
            }
            .start(ContextCompat.getMainExecutor(requireContext())) { recordEvent ->
                when(recordEvent) {
                    is VideoRecordEvent.Start -> {
                        bind.cameraxVideoCaptureButton.apply {
                            text = getString(pkg.what.pq.R.string.camerax_stop_capture)
                            isEnabled = true
                        }
                    }
                    is VideoRecordEvent.Finalize -> {
                        if (!recordEvent.hasError()) {
                            val msg = "Video capture succeeded: " +
                                    "${recordEvent.outputResults.outputUri}"
                            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
                                .show()
                            Log.d(TAG, msg)
                        } else {
                            recCapture?.close()
                            recCapture = null
                            Log.e(TAG, "Video capture ends with error: " +
                                    "${recordEvent.error}")
                        }
                        bind.cameraxVideoCaptureButton.apply {
                            text = getString(pkg.what.pq.R.string.camerax_start_capture)
                            isEnabled = true
                        }
                    }
                }
            }


    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object {
        const val LOG_DEBUG_TAG = "A0_VIEW_CAMERAX_DEBUG_TAG"
        const val LOG_INFO_TAG = "A0_VIEW_CAMERAX_INFO_TAG"
        private const val TAG = "a_0" //pkg.what.pq.ApplicationPQ
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        const val USE_CASE_CAMERA_CAPTURE_IMAGE = 0
        const val USE_CASE_CAMERA_CAPTURE_VIDEO = 1
        const val USE_CASE_CAMERA_ANALYSIS_IMAGE = 2

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

    private inner class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {
        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()
                val data = ByteArray(remaining())
                    get(data)
            return data
        }
        override fun analyze(image: ImageProxy) {
            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val luma = pixels.average()
            listener(luma)
            image.close()
        }
    }

}