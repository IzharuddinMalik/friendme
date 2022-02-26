package com.friendme.ui.dashboardadmin.producegift

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.friendme.R
import com.friendme.contract.GiftContract
import com.friendme.presenter.GiftPresenter
import com.friendme.ui.dashboardadmin.DashboardAdminActivity
import com.friendme.ui.dashboardadmin.producegift.adapter.AdapterGiftAdmin
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_list_gift.*

class ListGiftActivity : AppCompatActivity(), GiftContract.giftContractView {

    private var presenterGift : GiftPresenter? = null
    private var customToast = CustomToast()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_gift)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")

        presenterGift = GiftPresenter(this)
        presenterGift?.getListGift(idUser!!)

        initLiveData()

        ivBackGiftAdmin.setOnClickListener {
            this.onBackPressed()
        }

        ivAddProduceGiftAdmin.setOnClickListener {
            var intent = Intent(this, ProduceGiftActivity::class.java)
            intent.putExtra("from", "add")
            startActivity(intent)
        }
    }

    fun initLiveData() {
        presenterGift?.onRefreshGetGift()!!.observe(this) {
            rvListCariGiftAdmin.apply {
                rvListCariGiftAdmin.layoutManager = GridLayoutManager(this@ListGiftActivity, 2)
                rvListCariGiftAdmin.adapter = AdapterGiftAdmin(it)
            }
        }
    }

    override fun showLoadingGift() {
        shimmerLayoutGiftAdmin.startShimmer()
        shimmerLayoutGiftAdmin.visibility = View.VISIBLE
        rvListCariGiftAdmin.visibility = View.GONE
    }

    override fun hideLoadingGift() {
        shimmerLayoutGiftAdmin.stopShimmer()
        shimmerLayoutGiftAdmin.visibility = View.GONE
        rvListCariGiftAdmin.visibility = View.VISIBLE
    }

    override fun showToastGift(message: String) {
        customToast.customToast(this, message)
    }

    @Override
    override fun onBackPressed() {
        startActivity(Intent(this, DashboardAdminActivity::class.java))
    }
}