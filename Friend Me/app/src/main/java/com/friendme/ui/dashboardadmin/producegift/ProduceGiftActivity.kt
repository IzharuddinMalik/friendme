package com.friendme.ui.dashboardadmin.producegift

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.friendme.R
import com.friendme.contract.GiftContract
import com.friendme.presenter.GiftPresenter
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_produce_gift.*
import java.io.ByteArrayOutputStream

class ProduceGiftActivity : AppCompatActivity(), GiftContract.giftContractView {

    private var presenterGift : GiftPresenter? = null
    private var customToast = CustomToast()
    private var customProgressDialog = CustomProgressDialog()

    private var strIdGift : String? = ""
    private var strNamaGift : String? = ""
    private var strIdrGift : String? = ""
    private var strImageGift : String? = ""

    private var strIntentFrom : String? = ""

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
        setContentView(R.layout.activity_produce_gift)

        strIntentFrom = intent.getStringExtra("from")

        presenterGift = GiftPresenter(this)

        if (strIntentFrom == "add") {
            tvUploadImageProduceGiftAdmin.setOnClickListener {
                popupDialog()
            }

            cvTambahProduceGiftAdmin.setOnClickListener {
                if (!checkNamaGift() || !checkIdrGift()) {
                    customToast.customToast(this, "Cek kembali data Anda")
                } else {

                    var image = convertImageToStringForServer(gambarKu)

                    if (image == null) {
                        image = ""
                    }

                    presenterGift?.addGift(strNamaGift!!, image, strIdrGift!!)
                }
            }

            presenterGift?.onAddGift()!!.observe(this) {
                if (it) {
                    startActivity(Intent(this, ListGiftActivity::class.java))
                }
            }
        } else {
            strIdGift = intent.getStringExtra("idGift")
            strNamaGift = intent.getStringExtra("namaGift")
            strIdrGift = intent.getStringExtra("idrGift")
            strImageGift = intent.getStringExtra("imageGift")

            Glide.with(this).load("https://idfriendme.com/imageapps/" + strImageGift).into(ivImageProduceGiftAdmin)
            edtNamaProduceGiftAdmin.setText(strNamaGift)
            edtIDRProduceGiftAdmin.setText(strIdrGift)

            tvUploadImageProduceGiftAdmin.setOnClickListener {
                popupDialog()
            }

            cvTambahProduceGiftAdmin.setOnClickListener {
                if (!checkNamaGift() || !checkIdrGift()) {
                    customToast.customToast(this, "Cek kembali data Anda")
                } else {
                    var image = convertImageToStringForServer(gambarKu)

                    if (image == null) {
                        image = ""
                    }

                    Log.i("IDR GIFT", " === " + strIdrGift)

                    presenterGift?.editGift(strIdGift!!, strNamaGift!!, strIdrGift!!, image!!)
                }
            }

            presenterGift?.onEditGift()!!.observe(this) {
                startActivity(Intent(this, ListGiftActivity::class.java))
            }
        }

        ivBackProduceGiftAdmin.setOnClickListener {
            this.onBackPressed()
        }
    }

    fun checkNamaGift() : Boolean {
        strNamaGift = edtNamaProduceGiftAdmin.text.toString()

        if (strNamaGift!!.isEmpty()) {
            customToast.customToast(this, "Nama gift tidak boleh kosong!")
            return false
        }

        return true
    }

    fun checkIdrGift() : Boolean {
        strIdrGift = edtIDRProduceGiftAdmin.text.toString()

        if (strIdrGift!!.isEmpty()) {
            customToast.customToast(this, "Harga tidak boleh kosong!")
            return false
        }

        return true
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
            ivImageProduceGiftAdmin.setImageBitmap(resized)
            gambarKu = resized
        }

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            val photo : Bitmap = MediaStore.Images.Media.getBitmap(this!!.contentResolver, data?.data)
            resized = Bitmap.createScaledBitmap(photo, 480, 640, true)
            var outputStream = ByteArrayOutputStream()
            resized?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            ivImageProduceGiftAdmin.setImageBitmap(resized)
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

    override fun showLoadingGift() {
        customProgressDialog.show(this, "Sedang menambahkan gift...")
    }

    override fun hideLoadingGift() {
        customProgressDialog.dialog.dismiss()
    }

    override fun showToastGift(message: String) {
        customToast.customToast(this, message)
    }
}