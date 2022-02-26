package com.friendme.ui.detailroom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.friendme.R
import com.friendme.contract.NewDetailRoomContract
import com.friendme.presenter.NewDetailRoomPresenter
import com.friendme.utils.CustomToast
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_new_detail_room.*
import androidx.viewpager.widget.ViewPager
import com.friendme.ui.dashboard.DashboardActivity
import com.friendme.ui.dashboardadmin.DashboardAdminActivity
import com.friendme.ui.detailroom.fragment.NewFragmentDetailRoom
import com.friendme.utils.CustomProgressDialog


class NewDetailRoomActivity : AppCompatActivity(), NewDetailRoomContract.newDetailRoomView {

    private var presenterNewDetailRoom : NewDetailRoomPresenter? = null
    private var customToast = CustomToast()
    private var customProgressDialog = CustomProgressDialog()

    //Fragment List
    private val mFragmentList: ArrayList<Fragment> = ArrayList()

    //Title List
    private val mFragmentTitleList: ArrayList<String> = ArrayList()
    private var adapter: ViewPagerAdapter? = null

    private var namaRoom : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_detail_room)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")

        namaRoom = intent.getStringExtra("namaRoom")

        presenterNewDetailRoom = NewDetailRoomPresenter(this)
        presenterNewDetailRoom?.sendDetailRoom(idUser!!)

        presenterNewDetailRoom?.onRefreshData()!!.observe(this) {

            var currentPos = 0

            for (i in 0 until it.size) {
                val data = it[i]

                Log.i("SELECTROOM", " === " + namaRoom)
                if (data.namaRoom == namaRoom) {
                    currentPos = i
                    Log.i("CURRPOS", " === " + currentPos)
                }

                mFragmentTitleList.add(data.namaRoom!!)
            }

            for (j in 0 until mFragmentTitleList.size) {
                mFragmentList.add(NewFragmentDetailRoom(it[j].idRoom!!, it[j].namaRoom))
            }

            setupViewPager(viewpagerDetailRoom)
            tabDetailRoom.setupWithViewPager(viewpagerDetailRoom)
            // Tab ViewPager setting
            viewpagerDetailRoom.setCurrentItem(currentPos)
            viewpagerDetailRoom.setOffscreenPageLimit(mFragmentList.size)
            tabDetailRoom.setupWithViewPager(viewpagerDetailRoom)
            tabDetailRoom.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewpagerDetailRoom.setCurrentItem(tab.position)
                    NewFragmentDetailRoom(it[tab.position].idRoom!!, it[tab.position].namaRoom)
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }

    }

    private fun setupViewPager(viewPager: ViewPager) {
        adapter = ViewPagerAdapter(supportFragmentManager, mFragmentList, mFragmentTitleList)
        viewPager.adapter = adapter
    }

    //ViewPagerAdapter settings
    internal class ViewPagerAdapter(
        fm: FragmentManager?,
        fragments: List<Fragment>?,
        titleLists: List<String>
    ) :
        FragmentPagerAdapter(fm!!) {
        private var mFragmentList: List<Fragment>? = ArrayList()
        private var mFragmentTitleList: List<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return mFragmentList!![position]
        }

        override fun getCount(): Int {
            return mFragmentList?.size ?: 0
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }

        init {
            mFragmentList = fragments
            mFragmentTitleList = titleLists
        }
    }

    override fun showToastDetailRoom(message: String) {
        customToast.customToast(this, message)
    }

    override fun showLoadingDetailRoom() {
        customProgressDialog.show(this, "Harap menunggu...")
    }

    override fun hideLoadingDetailRoom() {
        customProgressDialog.dialog.dismiss()
    }

    override fun onBackPressed() {
        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var levelManagement = sharedPreferences.getString("levelManagement", "")

        if (levelManagement == "1") {
            startActivity(Intent(this, DashboardAdminActivity::class.java))
        } else if (levelManagement == "5") {
            startActivity(Intent(this, DashboardAdminActivity::class.java))
        } else if (levelManagement == "7") {
            startActivity(Intent(this, DashboardAdminActivity::class.java))
        } else {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }
}