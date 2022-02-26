package com.friendme.ui.forgetpass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.friendme.R
import com.friendme.contract.RessetPasswordContract
import com.friendme.presenter.RessetPasswordPresenter
import com.friendme.ui.login.LoginActivity
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_resset_password.*

class RessetPasswordActivity : AppCompatActivity(), RessetPasswordContract.ressetPassView {

    private var presenterRessetPass : RessetPasswordPresenter? = null
    private var customProgressDialog = CustomProgressDialog()
    private var customToast = CustomToast()

    private var strPassword : String? = ""
    private var strEmail : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resset_password)

        presenterRessetPass = RessetPasswordPresenter(this)

        strEmail = intent.getStringExtra("email")

        tietEmailResetPass.setText(strEmail)

        cvKirimResetPass.setOnClickListener {
            if (!checkPassword()) {
                customToast.customToast(this, "Cek kembali data Anda")
            } else {
                presenterRessetPass?.ressetPassword(strEmail!!, strPassword!!)
            }
        }

        presenterRessetPass?.onRessetPass()!!.observe(this) {
            if (it) {
                startActivity(Intent(this, LoginActivity::class.java)).also {
                    finish()
                }
            }
        }
    }

    fun checkPassword() : Boolean {
        strPassword = tietPasswordResetPass.text.toString()

        if (strPassword!!.isEmpty()) {
            customToast.customToast(this, "Password tidak boleh kosong")
            return false
        } else if (strPassword!!.length < 6) {
            customToast.customToast(this, "Passowrd minimal 6 karakter")
            return false
        }

        return true
    }

    override fun showLoadingRessetPass() {
        customProgressDialog.show(this, "Password anda sedang diubah...")
    }

    override fun hideLoadingRessetPass() {
        customProgressDialog.dialog.dismiss()
    }

    override fun showToastRessetPass(message: String) {
        customToast.customToast(this, message)
    }
}