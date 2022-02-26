package com.friendme.ui.gift

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.friendme.R
import com.friendme.contract.GiftContract
import com.friendme.presenter.GiftPresenter
import com.friendme.ui.gift.adapter.AdapterGiftUser
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_send_gift.*

class SendGiftActivity : AppCompatActivity(), GiftContract.giftContractView {

    private var presenterGift : GiftPresenter? = null
    private var customToast = CustomToast()
    private var shimmerFrameLayout : ShimmerFrameLayout? = null
    private var rvListGift : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_gift)

        shimmerFrameLayout = findViewById(R.id.shimmerLayoutGiftUser)
        rvListGift = findViewById(R.id.rvListGiftUser)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")

        presenterGift = GiftPresenter(this)
        presenterGift?.listUserGift(idUser!!)

        initLiveData()

        ivBackGift.setOnClickListener {
            this.onBackPressed()
        }
    }

    fun initLiveData() {
        presenterGift?.onRefreshListGift()!!.observe(this) {
            rvListGift.apply {
                rvListGift!!.layoutManager = LinearLayoutManager(this@SendGiftActivity)
                rvListGift!!.adapter = AdapterGiftUser(it)
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
}