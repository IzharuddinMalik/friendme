package com.friendme.ui.forgetpass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.friendme.R
import com.friendme.contract.RessetPasswordContract
import com.friendme.presenter.RessetPasswordPresenter
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPasswordActivity : AppCompatActivity(), RessetPasswordContract.ressetPassView {

    private var presenterRessetPass : RessetPasswordPresenter? = null
    private var customProgressDialog = CustomProgressDialog()
    private var customToast = CustomToast()

    private var strEmail : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        presenterRessetPass = RessetPasswordPresenter(this)

        cvKirimLupaPass.setOnClickListener {
            if (!checkEmail()) {
                customToast.customToast(this, "Cek kembali data Anda")
            } else {
                presenterRessetPass?.verifEmail(strEmail!!)
            }
        }

        presenterRessetPass?.onVerifEmail()!!.observe(this) {
            if (it) {
                var intent = Intent(this, RessetPasswordActivity::class.java)
                intent.putExtra("email", strEmail)
                startActivity(intent)
            }
        }
    }

    fun checkEmail() : Boolean {
        strEmail = tietEmailLupaPass.text.toString()

        if (strEmail!!.isEmpty()) {
            customToast.customToast(this, "Email tidak boleh kosong!")
            return false
        }

        return true
    }

    override fun showLoadingRessetPass() {
        customProgressDialog.show(this, "Sedang verifikasi email Anda")
    }

    override fun hideLoadingRessetPass() {
        customProgressDialog.dialog.dismiss()
    }

    override fun showToastRessetPass(message: String) {
        customToast.customToast(this, message)
    }
}