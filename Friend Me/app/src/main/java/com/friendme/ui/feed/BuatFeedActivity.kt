package com.friendme.ui.feed

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.Gravity
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import com.friendme.R
import com.friendme.contract.CreateFeedContract
import com.friendme.presenter.CreateFeedPresenter
import com.friendme.ui.dashboard.DashboardActivity
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import com.friendme.utils.EmojiconUtils
import kotlinx.android.synthetic.main.activity_buat_feed.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.ByteArrayOutputStream

class BuatFeedActivity : AppCompatActivity(), CreateFeedContract.createFeedView {

    private var presenterFeed : CreateFeedContract.createFeedPresenter? = null
    private var progressDialog = CustomProgressDialog()
    private var customToast = CustomToast()

    private var strMessage : String? = ""

    private var dialogPreview : Dialog? = null

    //image pick code
    private val IMAGE_PICK_CODE = 1000;
    //Permission code
    private val PERMISSION_CODE = 1001;
    var CAMERA_REQUEST : Int? = 0
    var gambarKu : Bitmap? = null

    var resized : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buat_feed)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")

        presenterFeed = CreateFeedPresenter(this)

        ivUploadImageFeed.setOnClickListener {
            popupDialog()
        }

        ivSendFeed.setOnClickListener {
            if (!checkMessage()) {
                customToast.customToast(this, "Cek kembali!")
            } else {
                var image = convertImageToStringForServer(gambarKu)
                if (image == null) {
                    image = ""
                }

                presenterFeed?.sendCreateFeed(idUser!!, strMessage!!, image)
            }
        }
    }

    fun popupDialog(){
        dialogPreview = Dialog(this)
        dialogPreview!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogPreview!!.setContentView(R.layout.dialog_camera_gallery)
        dialogPreview!!.setCancelable(true)
        val window = dialogPreview!!.window
        window!!.setGravity(Gravity.BOTTOM)
        window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        window!!.setBackgroundDrawable(resources.getDrawable(R.drawable.background_transparent))

        val openCamera = dialogPreview!!.findViewById<LinearLayout>(R.id.llOpenCamera)
        val openGallery = dialogPreview!!.findViewById<LinearLayout>(R.id.llOpenGallery)

        openCamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST!!)
        }

        openGallery.setOnClickListener {
            openImageGallery()
        }

        dialogPreview!!.show()
    }

    fun openImageGallery(){
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    openImageGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //untuk mengambil gambar hasil capture camera tadi kita harus override onActivityResult dan membaca resultCode apakah sukses dan requestCode apakah dari Camera_Request
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val mphoto = (data!!.extras!!["data"] as Bitmap?)!!
            //panggil method uploadImage
            resized = Bitmap.createScaledBitmap(mphoto, 480, 640, true)
            var outputStream = ByteArrayOutputStream()
            resized?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            ivImageFeed.setImageBitmap(resized)
            gambarKu = resized
        }

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            val photo : Bitmap = MediaStore.Images.Media.getBitmap(this!!.contentResolver, data?.data)
            resized = Bitmap.createScaledBitmap(photo, 480, 640, true)
            var outputStream = ByteArrayOutputStream()
            resized?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            ivImageFeed.setImageBitmap(resized)
            gambarKu = resized
        }
    }

    fun convertImageToStringForServer(imageBitmap: Bitmap?): String? {
        val stream = ByteArrayOutputStream()
        return if (imageBitmap != null) {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()
            Base64.encodeToString(byteArray, Base64.DEFAULT)
        } else {
            null
        }
    }

    override fun showLoadingCreateFeed() {
        progressDialog.show(this, "Harap menunggu...")
    }

    override fun hideLoadingCreateFeed() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastCreateFeed(message: String) {
        customToast.customToast(this, message)
    }

    override fun successCreateFeed() {
        startActivity(Intent(this, DashboardActivity::class.java)).also {
            finish()
        }
    }

    fun checkMessage() : Boolean {
        var encodeEmoji = EmojiconUtils()
        strMessage = encodeEmoji.escapeJavaStringEmoji(edtMessageFeed.text.toString())

        if (strMessage!!.isEmpty()) {
            customToast.customToast(this, "Harap isi pesan feed anda!")
            return false
        }

        return true
    }
}