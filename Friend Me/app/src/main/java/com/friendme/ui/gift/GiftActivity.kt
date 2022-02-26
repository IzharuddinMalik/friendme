package com.friendme.ui.gift

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
import com.friendme.ui.detailroom.NewDetailRoomActivity
import com.friendme.ui.gift.adapter.AdapterGift
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_gift.*

class GiftActivity : AppCompatActivity(), GiftContract.giftContractView {

    private var giftPresenter : GiftPresenter? = null
    private var customToast = CustomToast()
    private var shimmerFrameLayout : ShimmerFrameLayout? = null
    private var rvListGift : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")

        shimmerFrameLayout = findViewById(R.id.shimmerLayoutGift)
        rvListGift = findViewById(R.id.rvListCariGift)

        giftPresenter = GiftPresenter(this)
        giftPresenter?.getListGift(idUser!!)

        initLive()

        giftPresenter?.onRefreshSendGift()!!.observe(this) {
            if (it == true) {
                startActivity(Intent(this, GiftSentUserActivity::class.java))
            } else{
                customToast.customToast(this, "Balance anda tidak mencukupi untuk membeli gift ini")
            }
        }

        ivBackGift.setOnClickListener {
            this.onBackPressed()
        }
    }

    fun initLive() {
        giftPresenter?.onRefreshGetGift()!!.observe(this) {
            rvListGift!!.apply {
                rvListGift!!.layoutManager = GridLayoutManager(this@GiftActivity, 2)
                rvListGift!!.adapter = AdapterGift(it)
            }
        }
    }

    override fun showLoadingGift() {
        shimmerFrameLayout!!.startShimmer()
        shimmerFrameLayout!!.visibility = View.VISIBLE
        rvListGift!!.visibility = View.GONE
    }

    override fun hideLoadingGift() {
        shimmerFrameLayout!!.stopShimmer()
        shimmerFrameLayout!!.visibility = View.GONE
        rvListGift!!.visibility = View.VISIBLE
    }

    override fun showToastGift(message: String) {
        customToast.customToast(this, message)
    }

    fun sendGift(idUser : String, idUserTo : String, idGift : String) {
        giftPresenter?.sendGift(idUser, idUserTo, idGift)
    }
}