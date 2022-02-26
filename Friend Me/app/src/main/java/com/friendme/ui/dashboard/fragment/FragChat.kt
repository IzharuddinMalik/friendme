package com.friendme.ui.dashboard.fragment

import android.content.Context
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.friendme.R
import com.friendme.contract.ListRoomUserContract
import com.friendme.presenter.ListRoomUserPresenter
import com.friendme.ui.dashboard.adapter.AdapterUserChatRoom
import com.friendme.ui.dashboard.model.ListRoomModel
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.frag_chat.*

class FragChat : Fragment(), ListRoomUserContract.listRoomUserView {

    private var presenterUserChatRoom : ListRoomUserPresenter? = null
    private var customToast = CustomToast()
    private var shimmerFrameLayout : ShimmerFrameLayout? = null
    private var llFragChat : LinearLayout? = null

    companion object{
        fun newInstance() : FragChat {
            val fragment = FragChat()
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
        var v = inflater.inflate(R.layout.frag_chat, container, false)
        shimmerFrameLayout = v.findViewById(R.id.shimmerLayoutFragChat)
        llFragChat = v.findViewById(R.id.llFragUserChat)

        presenterUserChatRoom = ListRoomUserPresenter(this)

        var sharedPreferences = requireActivity().getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")

        presenterUserChatRoom?.listRoomChatUser(idUser!!)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initLiveData()
    }

    fun initLiveData() {
        presenterUserChatRoom?.onRefreshRoomChat()!!.observe(requireActivity()) {
            rvListRoomChat.apply {
                rvListRoomChat.layoutManager = LinearLayoutManager(requireActivity())
                rvListRoomChat.adapter = AdapterUserChatRoom(it)
            }
        }
    }

    override fun showLoadingRoomUser() {
        shimmerFrameLayout!!.startShimmer()
        shimmerFrameLayout!!.visibility = View.VISIBLE
        llFragChat!!.visibility = View.GONE
    }

    override fun hideLoadingRoomUser() {
        shimmerFrameLayout!!.stopShimmer()
        shimmerFrameLayout!!.visibility = View.GONE
        llFragChat!!.visibility = View.VISIBLE
    }

    override fun showToastRoomUser(message: String) {
        if (isAdded) {
            customToast.customToast(requireActivity(), message)
        }
    }
}