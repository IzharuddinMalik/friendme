package com.friendme.ui.dashboardadmin.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.friendme.R
import com.friendme.presenter.ListFeedPresenter
import com.friendme.ui.dashboard.adapter.AdapterFeed
import com.friendme.ui.dashboard.fragment.FragFeed
import com.friendme.ui.dashboardadmin.managementuser.ManagementUserActivity
import com.friendme.ui.dashboardadmin.producegift.ListGiftActivity
import com.friendme.ui.feed.BuatFeedActivity
import kotlinx.android.synthetic.main.frag_feed.*
import kotlinx.android.synthetic.main.fragment_managementadmin.*

class FragManagementUser : Fragment() {

    companion object{
        fun newInstance() : FragManagementUser {
            val fragment = FragManagementUser()
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
        var v = inflater.inflate(R.layout.fragment_managementadmin, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cvManagementUserAdmin.setOnClickListener {
            startActivity(Intent(requireActivity(), ManagementUserActivity::class.java))
        }

        cvProduceGiftAdmin.setOnClickListener {
            startActivity(Intent(requireActivity(), ListGiftActivity::class.java))
        }
    }
}