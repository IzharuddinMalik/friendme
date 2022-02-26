package com.friendme.ui.register

import android.R.attr
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.friendme.R
import com.friendme.contract.RegisterAkunContract
import com.friendme.presenter.RegisterAkunPresenter
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import android.widget.Toast

import com.friendme.ui.login.LoginActivity

import com.google.firebase.auth.AuthResult

import com.google.android.gms.tasks.OnCompleteListener

import android.R.attr.password
import android.util.Log
import android.view.View


class RegisterActivity : AppCompatActivity(), RegisterAkunContract.registerAkunView {

    private var presenterRegister : RegisterAkunContract.registerAkunPresenter? = null
    private var progressDialog = CustomProgressDialog()
    private var customToast = CustomToast()

    private var strNama : String? = ""
    private var strUsername : String? = ""
    private var strEmail : String? = ""
    private var strSex : String? = ""
    private var strPassword : String? = ""
    private var strToken : String? = ""

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        presenterRegister = RegisterAkunPresenter(this)

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
            rbLakilakiRegister.setButtonTintList(colorStateList) // set the color tint list
            rbLakilakiRegister.invalidate() // Could not be necessary

            rbPerempuanRegister.buttonTintList = colorStateList
            rbPerempuanRegister.invalidate()
        }

        rgGender.setOnCheckedChangeListener(
            { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                strSex = radio.text.toString()
            })

        cvRegister.setOnClickListener {
            if (!checkNama() || !checkUsername() || !checkEmail() || !checkGender() || !checkPassword()) {
                customToast.customToast(this, "Cek data Anda!")
            } else {

                if (strSex == "Laki-laki") {
                    strSex = "l"
                } else if (strSex == "Perempuan") {
                    strSex = "p"
                }

                presenterRegister?.sendRegister(strNama!!, strUsername!!, strEmail!!, strSex!!, strPassword!!, strToken!!)
            }
        }
    }

    override fun showLoadingRegister() {
        progressDialog.show(this, "Harap menunggu...")
    }

    override fun hideLoadingRegister() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastRegister(message: String) {
        customToast.customToast(this, message)
    }

    override fun successRegister() {
//        var intent = Intent(this, VerifikasiEmailActivity::class.java)
//        intent.putExtra(VerifikasiEmailActivity::strEmail.toString(), "")
//        startActivity(intent)

        Log.i("EMAIL", " === " + strEmail)
        Log.i("PASSWORD", " === " + strPassword)

        mAuth!!.createUserWithEmailAndPassword(strEmail!!, strPassword!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    customToast.customToast(this, "Registration successful!")
                    progressDialog.dialog.dismiss()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    customToast.customToast(this, "Registration failed! Please try again later")
                    progressDialog.dialog.dismiss()
                }
            }
    }

    fun checkNama() : Boolean {
        strNama = tietNamaRegister.text.toString()

        if (strNama!!.isEmpty()) {
            customToast.customToast(this, "Nama tidak boleh kosong!")
            return false
        }

        return true
    }

    fun checkUsername() : Boolean {
        strUsername = tietUsernameRegister.text.toString()

        if (strUsername!!.isEmpty()) {
            customToast.customToast(this, "Username tidak boleh kosong!")
            return false
        }

        return true
    }

    fun checkGender() : Boolean {
        if (strSex!!.equals("")) {
            customToast.customToast(this, "Harap pilih jenis kelamin Anda!")
            return false
        }

        return true
    }

    fun checkPassword() : Boolean {
        strPassword = tietPasswordRegister.text.toString()

        if (strPassword!!.isEmpty()) {
            customToast.customToast(this, "Password tidak boleh kosong!")
            return false
        } else if (strPassword!!.length < 6) {
            customToast.customToast(this, "Password minimal 6 karakter")
            return false
        }

        return true
    }

    fun checkEmail() : Boolean {
        strEmail = tietEmailRegister.text.toString()

        if (strEmail!!.isEmpty()) {
            customToast.customToast(this, "Email tidak boleh kosong!")
            return false
        }

        return true
    }
}