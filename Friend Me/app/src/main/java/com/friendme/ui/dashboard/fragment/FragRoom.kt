package com.friendme.ui.dashboard.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.friendme.R
import com.friendme.contract.ListRoomUserContract
import com.friendme.presenter.ListRoomUserPresenter
import com.friendme.ui.dashboard.adapter.AdapterUserRoom
import com.friendme.ui.dashboard.buatroom.BuatRoomActivity
import com.friendme.ui.dashboard.cariroom.CariRoomActivity
import com.friendme.ui.dashboard.model.ListRoomModel
import com.friendme.ui.detailroom.adapter.AdapterListChatRoom
import com.friendme.ui.feed.BuatFeedActivity
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.frag_feed.*
import kotlinx.android.synthetic.main.frag_room.*

class FragRoom : Fragment(), ListRoomUserContract.listRoomUserView {

    private var presenterListRoomUser : ListRoomUserPresenter? = null
    private var customToast = CustomToast()
    private var shimmerFrameLayout : ShimmerFrameLayout? = null
    private var frameLayout : FrameLayout? = null
    private var strNamaRoom : String? = ""
    var flag : Boolean = false

    companion object{
        fun newInstance() : FragRoom {
            val fragment = FragRoom()
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
        var v = inflater.inflate(R.layout.frag_room, container, false)

        presenterListRoomUser = ListRoomUserPresenter(this)

        var sharedPreferences = requireActivity().getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")
        shimmerFrameLayout = v.findViewById(R.id.shimmerLayout)
        frameLayout = v.findViewById(R.id.frameUserRoom)
        presenterListRoomUser?.sendListRoomUser(idUser!!)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cvRoom.setOnClickListener {
            if (!flag) {
                cvAddRoom.visibility = View.VISIBLE
                cvAddRoom.animate().translationY(-(cvRoom.size).toFloat())

                flag = false
            } else {
                cvAddRoom.visibility = View.GONE
                cvAddRoom.animate().translationY(0F)

                flag = true
            }
        }

        cvAddRoom.setOnClickListener {
            startActivity(Intent(requireActivity(), BuatRoomActivity::class.java))
        }

        cvSearchRoom.setOnClickListener {
            if (!onCheckCariRoom()) {
                customToast.customToast(requireActivity(), "Cek kembali data Anda")
            } else {
                var intent = Intent(requireActivity(), CariRoomActivity::class.java)
                intent.putExtra("namaRoom", strNamaRoom)
                startActivity(intent)
            }
        }

        initLiveData()
    }

    fun onCheckCariRoom() : Boolean {
        strNamaRoom = edtCariRoomFragRoom.text.toString()

        if (strNamaRoom!!.isEmpty()) {
            customToast.customToast(requireActivity(), "Pastikan nama room telah diketik")
            return false
        }

        return true
    }

    fun initLiveData() {
        presenterListRoomUser?.onRefreshOfficial()!!.observe(requireActivity()) {
            rvListRoomOfficial.apply {
                rvListRoomOfficial.layoutManager = LinearLayoutManager(requireActivity())
                rvListRoomOfficial.adapter = AdapterUserRoom(it)
            }
        }

        presenterListRoomUser?.onRefreshFavourites()!!.observe(requireActivity()) {
            rvListRoomFavourites.apply {
                rvListRoomFavourites.layoutManager = LinearLayoutManager(requireActivity())
                rvListRoomFavourites.adapter = AdapterUserRoom(it)
            }
        }

        presenterListRoomUser?.onRefreshPlayGames()!!.observe(requireActivity()) {
            rvListRoomPlayGames.apply {
                rvListRoomPlayGames.layoutManager = LinearLayoutManager(requireActivity())
                rvListRoomPlayGames.adapter = AdapterUserRoom(it)
            }
        }

        presenterListRoomUser?.onRefreshRecentRoom()!!.observe(requireActivity()) {
            rvListRoomRecentRooms.apply {
                rvListRoomRecentRooms.layoutManager = LinearLayoutManager(requireActivity())
                rvListRoomRecentRooms.adapter = AdapterUserRoom(it)
            }
        }

        presenterListRoomUser?.onRefreshRandom()!!.observe(requireActivity()) {
            rvListRoomRandom.apply {
                rvListRoomRandom.layoutManager = LinearLayoutManager(requireActivity())
                rvListRoomRandom.adapter = AdapterUserRoom(it)
            }
        }
    }

    override fun showLoadingRoomUser() {
        shimmerFrameLayout!!.startShimmer()
        shimmerFrameLayout!!.visibility = View.VISIBLE
        frameLayout!!.visibility = View.GONE
    }

    override fun hideLoadingRoomUser() {
        shimmerFrameLayout!!.stopShimmer()
        shimmerFrameLayout!!.visibility = View.GONE
        frameLayout!!.visibility = View.VISIBLE
    }

    override fun showToastRoomUser(message: String) {
        if (isAdded) {
            customToast.customToast(requireActivity(), message)
        }
    }
}