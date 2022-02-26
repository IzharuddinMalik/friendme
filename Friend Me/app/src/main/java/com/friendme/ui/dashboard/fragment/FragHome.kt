package com.friendme.ui.dashboard.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.friendme.R
import com.friendme.contract.GetProfileContract
import com.friendme.contract.StatusFriendContract
import com.friendme.contract.UpdateStatusMessageContract
import com.friendme.contract.UpdateStatusOnlineContract
import com.friendme.presenter.GetProfilePresenter
import com.friendme.presenter.StatusFriendPresenter
import com.friendme.presenter.UpdateStatusMessagePresenter
import com.friendme.presenter.UpdateStatusOnlinePresenter
import com.friendme.ui.balance.AccountBalanceActivity
import com.friendme.ui.dashboard.adapter.AdapterStatusFriend
import com.friendme.ui.profile.model.ProfileModel
import com.friendme.ui.searchfriend.SearchFriendActivity
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.frag_home.*


class FragHome : Fragment(), GetProfileContract.getProfileView, UpdateStatusMessageContract.updateStatusMessageView,
UpdateStatusOnlineContract.updateStatusOnlineView, StatusFriendContract.statusFriendView{

    private var presenterProfile : GetProfileContract.getProfilePresenter? = null
    private var presenterUpdateStatus : UpdateStatusMessageContract.updateStatusMessagePresenter? = null
    private var presenterUpdateStatusOnline : UpdateStatusOnlineContract.updateStatusOnlinePresenter? = null
    private var presenterStatusFriend : StatusFriendPresenter? = null
    var idUser : String? = ""
    private var customToast = CustomToast()
    private var statusUserKirim : String? = ""
    private var statusOnlineUser : String? = ""
    private var shimmerFrameLayout : ShimmerFrameLayout? = null
    private var llFragHome : LinearLayout? = null

    companion object{
        fun newInstance() : FragHome {
            val fragment = FragHome()
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
        var v = inflater.inflate(R.layout.frag_home, container, false)

        shimmerFrameLayout = v.findViewById(R.id.shimmerLayoutFragHome)
        llFragHome = v.findViewById(R.id.llFragHome)

        presenterProfile = GetProfilePresenter(this)
        presenterUpdateStatus = UpdateStatusMessagePresenter(this)
        presenterUpdateStatusOnline = UpdateStatusOnlinePresenter(this)
        presenterStatusFriend = StatusFriendPresenter(this)

        var sharedPreferences = requireActivity().getSharedPreferences("session", Context.MODE_PRIVATE)
        idUser = sharedPreferences.getString("idUser", "")

        presenterProfile?.sendGetProfile(idUser!!)
        presenterStatusFriend?.statusFriend(idUser!!)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        edtStatusMessageDashboard.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                var messageStatus = edtStatusMessageDashboard.text.toString()
                presenterUpdateStatus?.sendUpdateStatusMessage(idUser!!, messageStatus)
                return@OnEditorActionListener true
            }
            false
        })

        val statusUser = resources.getStringArray(R.array.StatusUser)

        var adapter = requireActivity()?.let { ArrayAdapter(it, R.layout.inflater_textview, statusUser) }
        adapter?.setDropDownViewResource(R.layout.inflater_textview)

        if (statusOnlineUser!="") {
            if (statusOnlineUser == "1") {
                spinStatusOnline.setSelection(1)
            } else if (statusOnlineUser == "2") {
                spinStatusOnline.setSelection(2)
            } else if (statusOnlineUser == "3") {
                spinStatusOnline.setSelection(3)
            } else if (statusOnlineUser == "4") {
                spinStatusOnline.setSelection(4)
            }
        }

        spinStatusOnline.setAdapter(adapter)
        spinStatusOnline.setSelection(0, false)
        spinStatusOnline.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                Log.i("STATUSUSER", " === " + statusUser[position])

                if (statusUser[position] == "Online") {
                    statusUserKirim = "1"
                } else if (statusUser[position] == "Away") {
                    statusUserKirim = "2"
                } else if (statusUser[position] == "Busy") {
                    statusUserKirim = "3"
                } else if (statusUser[position] == "Offline") {
                    statusUserKirim = "4"
                }

                presenterUpdateStatusOnline?.sendUpateStatusOnline(idUser!!, statusUserKirim!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        cvSearchFriendHome.setOnClickListener {
            startActivity(Intent(requireContext(), SearchFriendActivity::class.java))
        }

        presenterStatusFriend?.onRefreshData()!!.observe(requireActivity()) {
            rvListStatusFriendHome.layoutManager = LinearLayoutManager(requireContext())
            rvListStatusFriendHome.adapter = AdapterStatusFriend(it)
        }

        cvBalanceHome.setOnClickListener {
            startActivity(Intent(requireActivity(), AccountBalanceActivity::class.java))
        }
    }

    override fun showLoadingProfile() {
        shimmerFrameLayout!!.startShimmer()
        shimmerFrameLayout!!.visibility = View.VISIBLE
        llFragHome!!.visibility = View.GONE
    }

    override fun hideLoadingProfile() {
        shimmerFrameLayout!!.stopShimmer()
        shimmerFrameLayout!!.visibility = View.GONE
        llFragHome!!.visibility = View.VISIBLE
    }

    override fun showToastProfile(message: String) {
    }

    override fun getData(profile: ProfileModel) {

        tvUsernameDashboard.setText(profile.username)
        edtStatusMessageDashboard.setText(profile.statusMessage)
        tvBalanceUserHome.text = profile.userBalance

        statusOnlineUser = profile.statusOnline

        Glide.with(requireActivity()).load("https://idfriendme.com/imageapps/"+profile.fotoProfile).into(ivFotoDashboard)
    }

    override fun showLoadingUpdateStatusMessage() {

    }

    override fun hideLoadingUpdateStatusMessage() {

    }

    override fun showToastUpdateStatusMessage(message: String) {
        if (isAdded) {
            customToast.customToast(requireActivity(), message)
        }
    }

    override fun showToastUpdateStatusOnline(message: String) {
        if (isAdded) {
            customToast.customToast(requireActivity(), message)
        }
    }

    override fun showToastStatusFriend(message: String) {
        if (isAdded) {
            customToast.customToast(requireActivity(), message)
        }
    }
}