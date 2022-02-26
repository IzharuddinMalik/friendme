package com.friendme.ui.dashboardadmin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.friendme.R
import com.friendme.contract.LogoutContract
import com.friendme.presenter.LogoutPresenter
import com.friendme.ui.dashboard.fragment.FragChat
import com.friendme.ui.dashboard.fragment.FragFeed
import com.friendme.ui.dashboard.fragment.FragHome
import com.friendme.ui.dashboard.fragment.FragRoom
import com.friendme.ui.dashboardadmin.fragment.FragManagementUser
import com.friendme.ui.login.LoginActivity
import com.friendme.ui.profile.ProfileActivity
import com.friendme.utils.CustomProgressDialog
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_content_dashboard_admin.*
import kotlinx.android.synthetic.main.activity_dashboard_admin.*

class DashboardAdminActivity : AppCompatActivity(), LogoutContract.logoutAkunView {

    private var presenterLogout : LogoutPresenter? = null
    private var progressDialog = CustomProgressDialog()
    private var dialogPreview : Dialog? = null
    private var idUserPref : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_admin)

        presenterLogout = LogoutPresenter(this)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")
        idUserPref = idUser

        setSupportActionBar(toolbarAdmin)

        val adapter =
            Viewpager(
                supportFragmentManager,
                this
            )
        viewpagerDashboardAdmin.setAdapter(adapter)

        tabDashboardAdmin.setupWithViewPager(viewpagerDashboardAdmin)
        tabDashboardAdmin.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewpagerDashboardAdmin.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        ivProfileDashboardAdmin.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    class Viewpager(fm: FragmentManager?, private val context: Context) :
        FragmentStatePagerAdapter(fm!!) {
        private val tabTitles = arrayOf("Home", "Feed", "Room", "Chat", "Management")
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    FragHome()
                }
                1 -> {
                    FragFeed()
                }
                2 -> {
                    FragRoom()
                }
                3 -> {
                    FragChat()
                }
                4 -> {
                    FragManagementUser()
                }
                else -> FragHome()
            }
        }

        override fun getCount(): Int {
            return tabTitles.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitles[position]
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(applicationContext, "click on setting", Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_exit ->{
                presenterLogout?.logoutAkun(idUserPref!!)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoadingLogout() {
        progressDialog.show(this, "Harap menunggu...")
    }

    override fun hideLoadingLogout() {
        progressDialog.dialog.dismiss()
    }

    override fun getMessageLogout(message: String) {
        popupDialog(message)
    }

    fun popupDialog(message: String){
        dialogPreview = Dialog(this)
        dialogPreview!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogPreview!!.setContentView(R.layout.dialog_message_logout)
        dialogPreview!!.setCancelable(false)
        val window = dialogPreview!!.window
        window!!.setGravity(Gravity.CENTER)
        window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        window!!.setBackgroundDrawable(resources.getDrawable(R.drawable.background_transparent))

        val tvMessage = dialogPreview!!.findViewById<TextView>(R.id.tvMessageLogout)
        val tvLogout = dialogPreview!!.findViewById<TextView>(R.id.tvLogout)

        tvMessage.text = message
        tvLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            editor.clear()
            editor.commit()
        }

        dialogPreview!!.show()
    }
}