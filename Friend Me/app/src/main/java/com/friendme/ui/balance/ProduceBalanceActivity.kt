package com.friendme.ui.balance

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.friendme.R
import com.friendme.contract.BalanceContract
import com.friendme.presenter.BalancePresenter
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_produce_balance.*

class ProduceBalanceActivity : AppCompatActivity(), BalanceContract.balanceView {

    private var presenterBalance : BalancePresenter? = null
    private var customToast = CustomToast()
    private var customProgressDialog = CustomProgressDialog()

    private var strAmountIDR : String? = ""
    private var strBalance : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produce_balance)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idAdmin = sharedPreferences.getString("idUser", "")

        presenterBalance = BalancePresenter(this)

        strBalance = intent.getStringExtra("balance")
        tvAccountBalanceProduceBalance.text = strBalance + " IDR"

        cvProduceBalanceAdmin.setOnClickListener {
            if (!checkAmountIDR()) {
                customToast.customToast(this, "Cek kembali data Anda")
            } else {
                presenterBalance?.produceBalance(idAdmin!!, strAmountIDR!!)
            }
        }

        presenterBalance?.onAddBalance()!!.observe(this) {
            if (it) {
                startActivity(Intent(this, AccountBalanceActivity::class.java)).also {
                    finish()
                }
            }
        }
    }

    fun checkAmountIDR() : Boolean {
        strAmountIDR = edtAmountMCRProduceBalance.text.toString()

        if (strAmountIDR!!.isEmpty()) {
            customToast.customToast(this, "Amount IDR tidak boleh kosong!")
            return false
        }

        return true
    }

    override fun showLoadingBalance() {
        customProgressDialog.show(this, "Produce balance sedang diproses...")
    }

    override fun hideLoadingBalance() {
        customProgressDialog.dialog.dismiss()
    }

    override fun showToastBalance(message: String) {
        customToast.customToast(this, message)
    }
}