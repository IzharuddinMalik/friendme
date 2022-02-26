package com.friendme.ui.balance

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.friendme.R
import com.friendme.contract.TransferBalanceContract
import com.friendme.presenter.TransferBalancePresenter
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_transfer_credit.*

class TransferCreditActivity : AppCompatActivity(), TransferBalanceContract.transferBalanceView {

    private var presenterTransferBalance : TransferBalancePresenter? = null
    private var progressDialog = CustomProgressDialog()
    private var customToast = CustomToast()

    private var strUsername : String? = ""
    private var strBalance : String? = ""
    private var strPin : String? = ""
    private var userBalance : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_credit)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")

        presenterTransferBalance = TransferBalancePresenter(this)

        userBalance = intent.getStringExtra("balance")

        tvAccountBalanceTransferCredit.text = userBalance + " IDR"

        cvTransferCredit.setOnClickListener {
            if (!checkUsername() || !checkAmount()) {
                customToast.customToast(this, "Harap cek kembali data Anda")
            } else {
                if (strBalance!!.toInt() > userBalance!!.toInt()) {
                    customToast.customToast(this, "Balance anda tidak cukup")
                } else {
                    presenterTransferBalance?.transferBalance(idUser!!, strUsername!!, strBalance!!)
                }
            }
        }

        ivBackTransferCredit.setOnClickListener {
            startActivity(Intent(this, AccountBalanceActivity::class.java)).also {
                finish()
            }
        }
    }

    fun checkUsername() : Boolean {
        strUsername = edtUsernameTransferCredit.text.toString()

        if (strUsername!!.isEmpty()) {
            customToast.customToast(this, "Tidak boleh kosong!")
            return false
        }

        return true
    }

    fun checkAmount() : Boolean {
        strBalance = edtAmountMCRTransferCredit.text.toString()

        if (strBalance!!.isEmpty()) {
            customToast.customToast(this, "Tidak boleh kosong!")
            return false
        } else if (strBalance!!.equals("0")) {
            customToast.customToast(this, "Harap masukan angka dengan benar")
            return false
        }

        return true
    }

    override fun showLoadingTransferBalance() {
        progressDialog.show(this, "Harap menunggu...")
    }

    override fun hideLoadingTransferBalance() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastTransferBalance(message: String) {
        customToast.customToast(this, message)
    }

    override fun successTransferBalance() {
        startActivity(Intent(this, AccountBalanceActivity::class.java)).also {
            finish()
        }
    }
}