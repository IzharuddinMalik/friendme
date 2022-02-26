package com.friendme.ui.dashboard.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.friendme.R
import com.friendme.contract.ListFeedContract
import com.friendme.presenter.ListFeedPresenter
import com.friendme.ui.dashboard.adapter.AdapterFeed
import com.friendme.ui.dashboard.model.ListFeedModel
import com.friendme.ui.feed.BuatFeedActivity
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.frag_feed.*

class FragFeed : Fragment(), ListFeedContract.listFeedView {

    private var presenterListFeed : ListFeedContract.listFeedPresenter? = null
    private var customToast = CustomToast()
    private var idUserPref : String? = ""
    private var shimmerFrameLayout : ShimmerFrameLayout? = null
    private var frameLayoutFragFeed : FrameLayout? = null

    companion object{
        fun newInstance() : FragFeed {
            val fragment = FragFeed()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.frag_feed, container, false)

        shimmerFrameLayout = v.findViewById(R.id.shimmerLayoutFragFeed)
        frameLayoutFragFeed = v.findViewById(R.id.frameFragFeed)

        var sharedPreferences = requireActivity().getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")

        presenterListFeed = ListFeedPresenter(this)
        presenterListFeed?.sendGetListFeed(idUser!!)

        idUserPref = idUser
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cvBuatFeed.setOnClickListener {
            startActivity(Intent(requireContext(), BuatFeedActivity::class.java))
        }

        presenterListFeed?.onRefreshData()!!.observe(this.requireActivity()) {
            rvListFeed.layoutManager = LinearLayoutManager(this.requireActivity())
            rvListFeed.adapter = AdapterFeed(it)
        }
    }

    override fun showToastListFeed(message: String) {
        if (isAdded) {
            customToast.customToast(requireActivity(), message)
        }
    }

    override fun showLoadingFeed() {
        shimmerFrameLayout!!.startShimmer()
        shimmerFrameLayout!!.visibility = View.VISIBLE
        frameLayoutFragFeed!!.visibility = View.GONE
    }

    override fun hideLoadingFeed() {
        shimmerFrameLayout!!.stopShimmer()
        shimmerFrameLayout!!.visibility = View.GONE
        frameLayoutFragFeed!!.visibility = View.VISIBLE
    }

    fun onRefreshFeed() {
        presenterListFeed?.sendGetListFeed(idUserPref!!)
        presenterListFeed?.onRefreshData()!!.observe(requireActivity()) {
            rvListFeed.layoutManager = LinearLayoutManager(requireActivity())
            rvListFeed.adapter = AdapterFeed(it)
        }
    }
}