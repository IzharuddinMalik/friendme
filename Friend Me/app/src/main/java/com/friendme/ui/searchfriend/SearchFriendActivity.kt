package com.friendme.ui.searchfriend

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.friendme.R
import com.friendme.contract.SearchFriendContract
import com.friendme.presenter.SearchFriendPresenter
import com.friendme.ui.dashboard.DashboardActivity
import com.friendme.ui.searchfriend.adapter.AdapterSearchFriend
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_search_friend.*

class SearchFriendActivity : AppCompatActivity(), SearchFriendContract.searchFriendView {

    private var progressDialog = CustomProgressDialog()
    private var customToast = CustomToast()
    private var presenterSearchFriend : SearchFriendPresenter? = null
    var idUser : String? = ""
    var searchName : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_friend)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        idUser = sharedPreferences.getString("idUser", "")

        presenterSearchFriend = SearchFriendPresenter(this)
        presenterSearchFriend?.searchFriend(idUser!!, "")

        ivCariFriend.setOnClickListener {
            searchName = edtCariFriend.text.toString()
            presenterSearchFriend?.searchFriend(idUser!!, searchName!!)
        }

        presenterSearchFriend?.onRefreshData()!!.observe(this) {
            rvListCariFriend.apply {
                rvListCariFriend.layoutManager = GridLayoutManager(this@SearchFriendActivity, 3)
                rvListCariFriend.adapter = AdapterSearchFriend(it)
            }
        }

        ivBackSearchFriend.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java)).also {
                finish()
            }
        }
    }

    override fun showLoadingSearchFriend() {
        progressDialog.show(this, "Harap menunggu...")
    }

    override fun hideLoadingSearchFriend() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastSearchFriend(message: String) {
        customToast.customToast(this, message)
    }
}