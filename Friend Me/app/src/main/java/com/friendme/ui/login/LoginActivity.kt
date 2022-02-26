package com.friendme.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.friendme.R
import com.friendme.contract.LoginAkunContract
import com.friendme.presenter.LoginAkunPresenter
import com.friendme.ui.dashboard.DashboardActivity
import com.friendme.ui.dashboardadmin.DashboardAdminActivity
import com.friendme.ui.forgetpass.ForgetPasswordActivity
import com.friendme.ui.register.RegisterActivity
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginAkunContract.loginAkunView {

    private var presenterLogin : LoginAkunContract.loginAkunPresenter? = null
    private var progressDialog = CustomProgressDialog()
    private var customToast = CustomToast()

    private var strUsername : String? = ""
    private var strPassword : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        FirebaseApp.initializeApp(this)

        presenterLogin = LoginAkunPresenter(this)

        tvRegisterLogin.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        var sharedPreferences : SharedPreferences = getSharedPreferences("sessionRemember", Context.MODE_PRIVATE)
        var statusRemember = sharedPreferences.getString("statusRemember", "")

        var sharedPreferencesLogin : SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferencesLogin.getString("idUser", "")
        var levelManagement = sharedPreferencesLogin.getString("levelManagement", "")

        Log.i("STATUSREMEMBER", " === " + statusRemember)

        if (statusRemember == "true") {
            if (idUser != "") {
                if (levelManagement == "1") {
                    startActivity(Intent(this, DashboardAdminActivity::class.java))
                    finish()
                } else if (levelManagement == "5") {
                    startActivity(Intent(this, DashboardAdminActivity::class.java))
                    finish()
                } else if (levelManagement == "7") {
                    startActivity(Intent(this, DashboardAdminActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                }
            }
        } else {
            var sharedPreferences : SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
            var editor : SharedPreferences.Editor = sharedPreferences!!.edit()
            editor.clear()
            editor.commit()
        }

        cbRemembermeLogin.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                rememberLogin()
            } else {
                notRememberLogin()
            }
        }

        cvLogin.setOnClickListener {
            if (!checkUsername() || !checkPassword()) {
                customToast.customToast(this, "Cek data Anda!")
            } else {
                presenterLogin?.sendLogin(strUsername!!, strPassword!!)
            }
        }

        tvLupaPasswordLogin.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }
    }

    fun rememberLogin() {
        var sharedPreferences : SharedPreferences = getSharedPreferences("sessionRemember", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.putString("statusRemember", "true")
        editor.commit()
        Log.i("STATUSREMEMBER", " === " + "true")
    }

    fun notRememberLogin() {
        var sharedPreferences : SharedPreferences = getSharedPreferences("sessionRemember", Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.clear()
        editor.commit()
    }

    override fun showLoadingLoginAkun() {
        progressDialog.show(this, "Harap menunggu...")
    }

    override fun hideLoadingLoginAkun() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastLoginAkun(message: String) {
        customToast.customToast(this, message)
    }

    override fun successLoginAkun(idUser: String, levelManagement : String) {
        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.putString("idUser", idUser)
        editor.putString("levelManagement", levelManagement)
        editor.commit()

        if(levelManagement == "1") {
            startActivity(Intent(this, DashboardAdminActivity::class.java))
        } else if (levelManagement == "5") {
            startActivity(Intent(this, DashboardAdminActivity::class.java))
        } else if (levelManagement == "7") {
            startActivity(Intent(this, DashboardAdminActivity::class.java))
        } else {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }

    fun checkUsername() : Boolean {
        strUsername = tietUsernameLogin.text.toString()

        if (strUsername!!.isEmpty()) {
            customToast.customToast(this, "Harap isi username Anda!")
            return false
        }

        return true
    }

    fun checkPassword() : Boolean {
        strPassword = tietPasswordLogin.text.toString()

        if (strPassword!!.isEmpty()) {
            customToast.customToast(this, "Harap isi password Anda!")
            return false
        } else if (strPassword!!.length < 6) {
            customToast.customToast(this, "Password minimal 6 karakter!")
            return false
        }

        return true
    }
}