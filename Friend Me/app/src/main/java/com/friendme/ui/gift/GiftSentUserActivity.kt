package com.friendme.ui.gift

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.friendme.R
import com.friendme.contract.GiftContract
import com.friendme.presenter.GiftPresenter
import com.friendme.ui.gift.adapter.AdapterGiftUser
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_gift_sent_user.*

class GiftSentUserActivity : AppCompatActivity(), GiftContract.giftContractView {

    private var presenterGift : GiftPresenter? = null
    private var customToast = CustomToast()
    private var shimmerFrameLayout : ShimmerFrameLayout? = null
    private var rvListGiftSentUser : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift_sent_user)

        shimmerFrameLayout = findViewById(R.id.shimmerLayoutGiftSentUser)
        rvListGiftSentUser = findViewById(R.id.rvListGiftSentUser)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")

        presenterGift = GiftPresenter(this)
        presenterGift?.giftSentUser(idUser!!)

        initLiveData()

        ivBackGiftSent.setOnClickListener {
            this.onBackPressed()
        }
    }

    fun initLiveData(){
        presenterGift?.onRefreshListGiftSentUser()!!.observe(this) {
            rvListGiftSentUser.apply {
                rvListGiftSentUser!!.layoutManager = LinearLayoutManager(this@GiftSentUserActivity)
                rvListGiftSentUser!!.adapter = AdapterGiftUser(it)
            }
        }
    }

    override fun showLoadingGift() {
        shimmerFrameLayout!!.startShimmer()
        shimmerFrameLayout!!.visibility = View.VISIBLE
        rvListGiftSentUser!!.visibility = View.GONE
    }

    override fun hideLoadingGift() {
        shimmerFrameLayout!!.stopShimmer()
        shimmerFrameLayout!!.visibility = View.GONE
        rvListGiftSentUser!!.visibility = View.VISIBLE
    }

    override fun showToastGift(message: String) {
        customToast.customToast(this, message)
    }
}