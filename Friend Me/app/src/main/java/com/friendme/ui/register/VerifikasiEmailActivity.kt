package com.friendme.ui.register

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.friendme.R
import com.friendme.contract.VerifikasiEmailContract
import com.friendme.presenter.VerifikasiEmailPresenter
import com.friendme.ui.login.LoginActivity
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast

class VerifikasiEmailActivity : AppCompatActivity(), VerifikasiEmailContract.verifikasiEmailView {

    private var presenterVerifikasi : VerifikasiEmailContract.verifikasiEmailPresenter? = null
    var strEmail : String? = ""

    private var customToast = CustomToast()
    private var progressDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifikasi_email)

        presenterVerifikasi = VerifikasiEmailPresenter(this)

        val appLinkData: Uri? = intent?.data
        showDynamicLinkOffer(appLinkData)

        if (strEmail != null || strEmail != "") {
            presenterVerifikasi?.sendVerifikasi(strEmail!!)
        }
    }

    private fun showDynamicLinkOffer(uri: Uri?) {
        strEmail = uri!!.getQueryParameter("email")
        Log.i("URI", "" + strEmail)
    }

    override fun showLoadingVerifikasiEmail() {
        progressDialog.show(this, "Harap menunggu...")
    }

    override fun hideLoadingVerifikasiEmail() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastVerifikasiEmail(message: String) {
        customToast.customToast(this, message)
    }

    override fun successVerifikasiEmail() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}