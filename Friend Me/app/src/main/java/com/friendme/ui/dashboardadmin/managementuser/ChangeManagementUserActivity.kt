package com.friendme.ui.dashboardadmin.managementuser

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.friendme.R
import com.friendme.contract.AdminSectionContract
import com.friendme.presenter.AdminSectionPresenter
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_change_management_user.*
import kotlinx.android.synthetic.main.frag_home.*

class ChangeManagementUserActivity : AppCompatActivity(), AdminSectionContract.adminSectionView {

    private var presenterAdmin : AdminSectionPresenter? = null
    private var customToast = CustomToast()
    private var customProgressDialog = CustomProgressDialog()

    private var strUsername : String? = ""
    private var strLevelUser : String? = ""
    private var idUser : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_management_user)

        presenterAdmin = AdminSectionPresenter(this)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idAdmin = sharedPreferences.getString("idUser", "")

        presenterAdmin?.getLevelUser(idAdmin!!)

        strUsername = intent.getStringExtra("username")
        idUser = intent.getStringExtra("idUser")
        strLevelUser = intent.getStringExtra("idLevel")

        edtUsernameChangeManagementUser.setText(strUsername)

        presenterAdmin?.onChangeStatus()!!.observe(this) {
            if (it) {
                startActivity(Intent(this, ManagementUserActivity::class.java))
            }
        }

        ivBackChangeManagement.setOnClickListener {
            this.onBackPressed()
        }

        cvUbahStatusManagementUser.setOnClickListener {
            presenterAdmin?.setUserManagement(idAdmin!!, idUser!!, strLevelUser!!)
        }

        initLiveData()
    }

    fun initLiveData() {
        presenterAdmin?.onUserLevel()!!.observe(this) {

            var idLevel = arrayOfNulls<String>(it.size + 1)
            var levelUser = arrayOfNulls<String>(it.size+1)

            idLevel[0] = "0"
            levelUser[0] = "Pilih Level Management"

            for (i in 0 until it.size) {
                val data = it[i]

                idLevel[i+1] = data.idLevel
                levelUser[i+1] = data.levelUser
            }

            var adapter = this?.let { ArrayAdapter(it, R.layout.inflater_textview, levelUser) }
            adapter?.setDropDownViewResource(R.layout.inflater_textview)
            spinLevelUser.setAdapter(adapter)

            if (strLevelUser!="") {
                for (i in 0 until idLevel.size) {
                    if (idLevel[i] == strLevelUser) {
                        spinLevelUser.setSelection(i)
                        strLevelUser = idLevel[i]
                    }
                }
            }

            spinLevelUser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    strLevelUser = idLevel[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }
    }

    override fun showLoadingAdminSection() {
        customProgressDialog.show(this, "Sedang mengubah status user")
    }

    override fun hideLoadingAdminSection() {
        customProgressDialog.dialog.dismiss()
    }

    override fun showToastAdminSection(message: String) {
        customToast.customToast(this, message)
    }
}