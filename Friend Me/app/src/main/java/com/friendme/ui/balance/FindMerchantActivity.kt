package com.friendme.ui.balance

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.friendme.R
import com.friendme.contract.ListPeopleContract
import com.friendme.presenter.ListPeoplePresenter
import com.friendme.ui.balance.adapter.AdapterFindMerchant
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_find_merchant.*

class FindMerchantActivity : AppCompatActivity(), ListPeopleContract.listPeopleView {

    private var presenterPeople : ListPeoplePresenter? = null
    private var progressDialog = CustomProgressDialog()
    private var customToast = CustomToast()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_merchant)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")

        presenterPeople = ListPeoplePresenter(this)
        presenterPeople?.getListPeople(idUser!!)

        initLiveData()

        ivBackPeople.setOnClickListener {
            this.onBackPressed()
        }
    }

    fun initLiveData() {
        presenterPeople?.onRefreshAdmin()!!.observe(this) {
            rvListAdminPeople.apply {
                rvListAdminPeople.layoutManager = LinearLayoutManager(this@FindMerchantActivity)
                rvListAdminPeople.adapter = AdapterFindMerchant(it)
            }
        }

        presenterPeople?.onRefreshMentor()!!.observe(this) {
            rvListMentorPeople.apply {
                rvListMentorPeople.layoutManager = LinearLayoutManager(this@FindMerchantActivity)
                rvListMentorPeople.adapter = AdapterFindMerchant(it)
            }
        }

        presenterPeople?.onRefreshMerchant()!!.observe(this) {
            rvListMerchantPeople.apply {
                rvListMerchantPeople.layoutManager = LinearLayoutManager(this@FindMerchantActivity)
                rvListMerchantPeople.adapter = AdapterFindMerchant(it)
            }
        }

        presenterPeople?.onRefreshStaff()!!.observe(this) {
            rvListStaffPeople.apply {
                rvListStaffPeople.layoutManager = LinearLayoutManager(this@FindMerchantActivity)
                rvListStaffPeople.adapter = AdapterFindMerchant(it)
            }
        }
    }

    override fun showLoadingListPeople() {
        progressDialog.show(this, "Harap menunggu...")
    }

    override fun hideLoadingListPeople() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastListPeople(message: String) {
        customToast.customToast(this, message)
    }
}