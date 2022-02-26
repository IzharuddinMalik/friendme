package com.friendme.ui.profile

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import com.bumptech.glide.Glide
import com.friendme.R
import com.friendme.contract.EditProfileContract
import com.friendme.contract.GetProfileContract
import com.friendme.presenter.EditProfilePresenter
import com.friendme.presenter.GetProfilePresenter
import com.friendme.ui.profile.model.ProfileModel
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.frag_home.*
import java.io.ByteArrayOutputStream
import java.util.*

class EditProfileActivity : AppCompatActivity(), GetProfileContract.getProfileView, EditProfileContract.editProfileView, DatePickerDialog.OnDateSetListener {

    private var presenterGetProfile : GetProfileContract.getProfilePresenter? = null
    private var presenterUpdateProfile : EditProfileContract.editProfilePresenter? = null
    private var progressDialog = CustomProgressDialog()
    private var customToast = CustomToast()

    private var strNamaEdit : String? = ""
    private var strAbout : String? = ""
    private var strSex : String? = ""

    var days = 0
    var months: Int = 0
    var years: Int = 0

    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0

    var bulanWaktu : String? = ""

    val calendar: Calendar = Calendar.getInstance()

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
        setContentView(R.layout.activity_edit_profile)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")

        presenterGetProfile = GetProfilePresenter(this)
        presenterUpdateProfile = EditProfilePresenter(this)

        presenterGetProfile?.sendGetProfile(idUser!!)

        if (Build.VERSION.SDK_INT >= 21) {
            val colorStateList = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_enabled),
                    intArrayOf(android.R.attr.state_enabled)
                ), intArrayOf(
                    resources.getColor(R.color.greyText),  // disabled
                    resources.getColor(R.color.mainBlueColor) // enabled
                )
            )
            rbLakilakiEditProfil.setButtonTintList(colorStateList) // set the color tint list
            rbLakilakiEditProfil.invalidate() // Could not be necessary

            rbPerempuanEditProfil.buttonTintList = colorStateList
            rbPerempuanEditProfil.invalidate()
        }

        rgGenderEditProfil.setOnCheckedChangeListener(
            { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                strSex = radio.text.toString()
            })

        ivSaveEditProfile.setOnClickListener {
            if (!checkNama() || !checkSex()) {
                customToast.customToast(this, "Cek data Anda!")
            } else {
                if (strSex == "Laki-laki") {
                    strSex = "l"
                } else if (strSex == "Perempuan") {
                    strSex = "p"
                }
                strAbout = tietAboutEditProfil.text.toString()

                var image = convertImageToStringForServer(gambarKu)
                if (image == null) {
                    image = ""
                }

                presenterUpdateProfile?.sendEditProfile(idUser!!, strNamaEdit!!, strSex!!, bulanWaktu!!, strAbout!!, image)
            }
        }

        tietDateBirthEditProfil.setOnClickListener {
            days = calendar.get(Calendar.DAY_OF_MONTH)
            months = calendar.get(Calendar.MONTH)
            years = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this, this, years, months,days)
            datePickerDialog.show()
        }

        cvUploadFotoEditProfil.setOnClickListener {
            popupDialog()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month+1

        if (myMonth < 10){
            tietDateBirthEditProfil.setText("" + myDay + "/" + "0" + myMonth + "/" + myYear)
        } else{
            tietDateBirthEditProfil.setText("" + myDay + "/" + myMonth + "/" + myYear)
        }

        if (myMonth<10){
            bulanWaktu = "" + myYear + "-" + "0" + myMonth + "-" + myDay

            calendar.apply {
                set(Calendar.YEAR, myYear)
                set(Calendar.MONTH,0+myMonth)
                set(Calendar.DAY_OF_MONTH, myDay)
            }

        }else{
            bulanWaktu = "" + myYear + "-" + myMonth + "-" + myDay

            calendar.apply {
                set(Calendar.YEAR, myYear)
                set(Calendar.MONTH, myMonth)
                set(Calendar.DAY_OF_MONTH, myDay)
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
            ivFotoEditProfile.setImageBitmap(resized)
            gambarKu = resized
        }

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            val photo : Bitmap = MediaStore.Images.Media.getBitmap(this!!.contentResolver, data?.data)
            resized = Bitmap.createScaledBitmap(photo, 480, 640, true)
            var outputStream = ByteArrayOutputStream()
            resized?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            ivFotoEditProfile.setImageBitmap(resized)
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

    fun checkNama() : Boolean {
        strNamaEdit = tietNamaEditProfil.text.toString()

        if (strNamaEdit!!.isEmpty()) {
            customToast.customToast(this, "Nama tidak boleh kosong!")
            return false
        }

        return true
    }

    fun checkSex() : Boolean {
        if (strSex!!.isEmpty() || strSex == "") {
            customToast.customToast(this, "Pilih jenis kelamin Anda.")
            return false
        }

        return true
    }

    override fun showLoadingProfile() {
        progressDialog.show(this, "Harap menunggu...")
    }

    override fun hideLoadingProfile() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastProfile(message: String) {
        customToast.customToast(this, message)
    }

    override fun getData(profile: ProfileModel) {

        Log.i("USERNAME", " === " + profile.username)

        tietUsernameEditProfil.setText(profile.username)
        tietEmailEditProfil.setText(profile.emailUser)
        tietNamaEditProfil.setText(profile.namaUser)
        tietDateBirthEditProfil.setText(profile.dateBirth)
        tietAboutEditProfil.setText(profile.aboutUser)

        if (profile.sexUser == "l") {
            rbLakilakiEditProfil.isChecked = true
        } else {
            rbPerempuanEditProfil.isChecked = true
        }

        Glide.with(this).load("https://idfriendme.com/imageapps/"+profile.fotoProfile).into(ivFotoEditProfile)
    }

    override fun showLoadingEditProfile() {
        progressDialog.show(this, "Harap menunggu...")
    }

    override fun hideLoadingEditProfile() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastEditProfile(message: String) {
        customToast.customToast(this, message)
    }

    override fun successEditProfile() {
        startActivity(Intent(this, ProfileActivity::class.java)).also {
            finish()
        }
    }
}