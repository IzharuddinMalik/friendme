package com.friendme.ui.dashboardadmin.managementuser

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.friendme.R
import com.friendme.contract.AdminSectionContract
import com.friendme.presenter.AdminSectionPresenter
import com.friendme.ui.dashboardadmin.DashboardAdminActivity
import com.friendme.ui.dashboardadmin.managementuser.adapter.AdapterUserAdmin
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_management_user.*

class ManagementUserActivity : AppCompatActivity(), AdminSectionContract.adminSectionView {

    private var presenterAdmin : AdminSectionPresenter? = null
    private var customToast = CustomToast()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_management_user)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")

        presenterAdmin = AdminSectionPresenter(this)
        presenterAdmin?.listUser(idUser!!)

        presenterAdmin?.onUserList()!!.observe(this) {
            rvListUserAdmin.apply {
                rvListUserAdmin.layoutManager = GridLayoutManager(this@ManagementUserActivity, 2)
                rvListUserAdmin.adapter = AdapterUserAdmin(it)
            }
        }

        ivBackUserManagement.setOnClickListener {
            this.onBackPressed()
        }
    }

    override fun showLoadingAdminSection() {
        shimmerLayoutManagementUser.startShimmer()
        shimmerLayoutManagementUser.visibility = View.VISIBLE
        rvListUserAdmin.visibility = View.GONE
    }

    override fun hideLoadingAdminSection() {
        shimmerLayoutManagementUser.stopShimmer()
        shimmerLayoutManagementUser.visibility = View.GONE
        rvListUserAdmin.visibility = View.VISIBLE
    }

    override fun showToastAdminSection(message: String) {
        customToast.customToast(this, message)
    }

    @Override
    override fun onBackPressed() {
        startActivity(Intent(this, DashboardAdminActivity::class.java))
    }
}