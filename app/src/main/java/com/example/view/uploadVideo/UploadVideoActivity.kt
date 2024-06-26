package com.example.view.uploadVideo

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.apisetup.BuildConfig
import com.example.apisetup.R
import com.example.apisetup.notmodel.Status
import com.example.utils.EditProfileTools
import com.example.utils.UploadTools
import com.example.utils.getRealPathFromURI
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UploadVideoActivity : AppCompatActivity() {

    //handle when user reject a permission
    //ui
    private lateinit var back_image: ImageView
    private lateinit var upload_video_rl: RelativeLayout
    private lateinit var videoView: VideoView
    private lateinit var image_view_bg: ImageView
    private lateinit var cancel_image_view: ImageView
    private lateinit var update_rl: RelativeLayout
    private lateinit var editText: EditText

    //value
    private var videoUri: Uri? = null
    private var uploadVideo: Boolean = false

    val REQUEST_VIDEO_CAPTURE = 101
    val PERMISSION_REQUEST_CODE = 102
    val REQUEST_VIDEO_PICK = 103

    //server
    private lateinit var view_model: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_video)

        statusBarColor()
        casting()
        actionListenerToBack()
        checkPermissions()
        actionListenerToUploadVideo()
        actionListenerToCancelButton()
        actionListenerToUploadVideoToServer()

        //server
        view_model = SpewViewModel.giveMeViewModelWithHeader(this@UploadVideoActivity)
        observeAResponse()
    }

    private fun observeAResponse() {
        view_model.uploadVideo.observe(this){
            if (it.status== Status.SUCCESS){
                //handle SUCCESS case
                Toast.makeText(this, getString(R.string.update_password_message_6), Toast.LENGTH_SHORT).show()

            }else{

            }
        }
    }

    private fun actionListenerToUploadVideoToServer() {
        update_rl.setOnClickListener {
            if (uploadVideo){
                val videoTitle = editText.text.toString()



                val map = UploadTools.makeMapForUploadVideo(videoTitle, videoUri!!,this@UploadVideoActivity)

                val filePath = getRealPathFromURI(videoUri!!,this@UploadVideoActivity)

                val file = File(filePath)

                val requestFile = RequestBody.create("video/*".toMediaTypeOrNull(), file)
                var body = MultipartBody.Part.createFormData("video", file.name, requestFile)
                val description = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), videoTitle)
                val title = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "empty_title")
                val type = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "3")

                view_model.uploadVideo(body,description,title,type)

            }else{
                Toast.makeText(this,getString(R.string.upload_video_message_5),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getRealPathFromURI(contentUri: Uri, context: Context): String {
        var result: String
        val cursor = contentResolver.query(contentUri, null, null, null, null)
        if (cursor == null) {
            result = contentUri.path!!
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

    private fun actionListenerToCancelButton() {
        cancel_image_view.setOnClickListener{
            videoView.pause()
            videoUri = null

            videoView.isVisible = false
            image_view_bg.isVisible = true
            cancel_image_view.isVisible = false
            update_rl.setBackgroundResource(R.drawable.bg_8)
            uploadVideo = false
        }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        }
    }

    private fun actionListenerToUploadVideo() {
        image_view_bg.setOnClickListener {
            if (image_view_bg.getVisibility() == View.VISIBLE)
                showOptionDialog()
        }
    }

    private fun showOptionDialog() {
        val options = arrayOf(getString(R.string.upload_video_message_3), getString(R.string.upload_video_message_4))

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, options)

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.upload_video_message_2))
            .setAdapter(adapter) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
                when (i) {
                    0 -> captureVideo()
                    1 -> pickVideoFromGallery()
                }
            }
            .setCancelable(true)
            .show()
    }

    private fun pickVideoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        intent.type = "video/*"
        startActivityForResult(intent, REQUEST_VIDEO_PICK)
    }

    private fun captureVideo() {
        // Intent to capture a video
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            // Create a file to store the video
            val videoFile: File? = try {
                createVideoFile()
            } catch (ex: Exception) {
                null
            }
            videoFile?.also {
                videoUri = FileProvider.getUriForFile(
                    this,
                    "${applicationContext.packageName}.provider",
                    it
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri)
                startActivityForResult(intent, REQUEST_VIDEO_CAPTURE)
            }
        }
    }

    @Throws(IOException::class)
    private fun createVideoFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        return File.createTempFile(
            "VIDEO_${timeStamp}_", /* prefix */
            ".mp4", /* suffix */
            storageDir /* directory */
        )
    }

    private fun actionListenerToBack() {
        back_image.setOnClickListener{
            finish()
        }
    }

    private fun statusBarColor() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun casting() {
        back_image      = findViewById(R.id.back_image)
        image_view_bg   = findViewById(R.id.image_view_bg)
        cancel_image_view = findViewById(R.id.cancel_image_view)
        upload_video_rl = findViewById(R.id.upload_video_rl)
        videoView = findViewById(R.id.videoView)
        update_rl   = findViewById(R.id.update_rl)
        editText   = findViewById(R.id.editText)

        image_view_bg.isVisible = true
        cancel_image_view.isVisible = false
        update_rl.setBackgroundResource(R.drawable.bg_8)
    }

    private fun playVideo() {
        videoView.isVisible = true
        image_view_bg.isVisible = false
        cancel_image_view.isVisible = true
        update_rl.setBackgroundResource(R.drawable.bg_5)
        uploadVideo = true

        videoView.setVideoURI(videoUri)
        videoView.start()

        // Set the OnPreparedListener to start the video and set up the loop
        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = false  // Disable the default looping

            mediaPlayer.setOnCompletionListener {
                mediaPlayer.seekTo(0)  // Seek to the beginning
                mediaPlayer.start()    // Start the video again
            }

            mediaPlayer.start() // Start the video for the first time
        }
    }

    private fun getPathFromUri(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                return cursor.getString(columnIndex)
            }
        }
        return null
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Permission was granted
            } else {
                // Permission was denied
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_VIDEO_PICK) {
            videoUri = data?.data
            playVideo()
        }

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_VIDEO_CAPTURE) {
            videoUri = data?.data
            // Handle the captured video Uri
            playVideo()
        }

    }
}