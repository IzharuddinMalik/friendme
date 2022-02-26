package com.friendme.ui.balance

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.friendme.R
import com.friendme.contract.BalanceContract
import com.friendme.contract.GetProfileContract
import com.friendme.presenter.BalancePresenter
import com.friendme.presenter.GetProfilePresenter
import com.friendme.ui.balance.adapter.AdapterInfoBalance
import com.friendme.ui.balance.model.BalanceUserModel
import com.friendme.ui.dashboard.DashboardActivity
import com.friendme.ui.dashboardadmin.DashboardAdminActivity
import com.friendme.ui.profile.model.ProfileModel
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_account_balance.*

class AccountBalanceActivity : AppCompatActivity(), BalanceContract.balanceView, GetProfileContract.getProfileView {

    private var presenterBalanceUser : BalancePresenter? = null
    private var presenterProfile : GetProfileContract.getProfilePresenter? = null
    private var progressDialog = CustomProgressDialog()
    private var customToast = CustomToast()
    private var userBalance : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_balance)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")
        var levelManagement = sharedPreferences.getString("levelManagement", "")

        if (levelManagement == "1") {
            cvProduceCreditAccountBalance.visibility = View.VISIBLE
        } else if (levelManagement == "5") {
            cvProduceCreditAccountBalance.visibility = View.VISIBLE
        } else if (levelManagement == "7") {
            cvProduceCreditAccountBalance.visibility = View.VISIBLE
        } else {
            cvProduceCreditAccountBalance.visibility = View.GONE
        }

        presenterBalanceUser = BalancePresenter(this)
        presenterBalanceUser?.getBalanceUser(idUser!!)

        presenterProfile = GetProfilePresenter(this)
        presenterProfile?.sendGetProfile(idUser!!)

        cvTransferCreditAccountBalance.setOnClickListener {
            var intent = Intent(this, TransferCreditActivity::class.java)
            intent.putExtra("balance", userBalance)
            startActivity(intent)
        }

        cvFindMerchantAccountBalance.setOnClickListener {
            startActivity(Intent(this, FindMerchantActivity::class.java))
        }

        presenterBalanceUser?.onRefreshBalance()!!.observe(this) {
            rvListInfoAccountBalance.layoutManager = LinearLayoutManager(this)
            rvListInfoAccountBalance.adapter = AdapterInfoBalance(it)
        }

        ivBackAccountBalance.setOnClickListener {
            this.onBackPressed()
        }

        cvProduceCreditAccountBalance.setOnClickListener {
            var intent = Intent(this, ProduceBalanceActivity::class.java)
            intent.putExtra("balance", userBalance)
            startActivity(intent)
        }
    }

    override fun showLoadingBalance() {
        progressDialog.show(this, "Harap menunggu...")
    }

    override fun hideLoadingBalance() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastBalance(message: String) {
        customToast.customToast(this, message)
    }

    override fun showLoadingProfile() {

    }

    override fun hideLoadingProfile() {

    }

    override fun showToastProfile(message: String) {
        customToast.customToast(this, message)
    }

    override fun getData(profile: ProfileModel) {
        tvAccountBalance.text = profile.userBalance + " IDR"
        userBalance = profile.userBalance
    }

    @Override
    override fun onBackPressed() {
        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var levelManagement = sharedPreferences.getString("levelManagement", "")

        if (levelManagement == "1") {
            startActivity(Intent(this, DashboardAdminActivity::class.java))
        } else if (levelManagement == "5") {
            startActivity(Intent(this, DashboardAdminActivity::class.java))
        } else if (levelManagement == "7") {
            startActivity(Intent(this, DashboardAdminActivity::class.java))
        } else {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }
}