package com.friendme.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.size
import com.bumptech.glide.Glide
import com.friendme.R
import com.friendme.contract.GetProfileContract
import com.friendme.contract.SearchFriendContract
import com.friendme.presenter.GetProfilePresenter
import com.friendme.presenter.SearchFriendPresenter
import com.friendme.ui.profile.model.ProfileModel
import com.friendme.ui.searchfriend.SearchFriendActivity
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile_teman.*
import kotlinx.android.synthetic.main.frag_room.*

class ProfileTemanActivity : AppCompatActivity(), GetProfileContract.getProfileView, SearchFriendContract.searchFriendView {

    private var presenterGetProfile : GetProfileContract.getProfilePresenter? = null
    private var presenterSearchFriend : SearchFriendPresenter? = null
    private var customToast = CustomToast()
    private var progressDialog = CustomProgressDialog()
    private var idFriend : String? = ""
    private var idUser : String? = ""
    var flag : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_teman)

        idFriend = intent.getStringExtra("idFriend")
        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        idUser = sharedPreferences.getString("idUser", "")

        presenterGetProfile = GetProfilePresenter(this)
        presenterSearchFriend = SearchFriendPresenter(this)

        presenterGetProfile?.sendGetProfile(idFriend!!)

        ivBackProfileTeman.setOnClickListener {
            startActivity(Intent(this, SearchFriendActivity::class.java)).also {
                finish()
            }
        }

        cvListAddFriendProfileTeman.setOnClickListener {
            if (!flag) {
                cvBlockProfileTeman.visibility = View.VISIBLE
                cvPrivateMessageProfileTeman.visibility = View.VISIBLE
                cvAddFriendProfileTeman.visibility = View.VISIBLE
                cvLikeProfileTeman.visibility = View.VISIBLE
                cvBlockProfileTeman.animate().translationY(-(cvListAddFriendProfileTeman.size).toFloat())
                cvPrivateMessageProfileTeman.animate().translationY(-(cvListAddFriendProfileTeman.size).toFloat() + (cvBlockProfileTeman.size).toFloat())
                cvAddFriendProfileTeman.animate().translationY(-(cvListAddFriendProfileTeman.size).toFloat() + (cvBlockProfileTeman.size).toFloat() + (cvPrivateMessageProfileTeman.size).toFloat())
                cvLikeProfileTeman.animate().translationY(-(cvListAddFriendProfileTeman.size).toFloat() + (cvBlockProfileTeman.size).toFloat() + (cvPrivateMessageProfileTeman.size).toFloat() + (cvAddFriendProfileTeman.size).toFloat())

                flag = false
            } else {
                cvBlockProfileTeman.visibility = View.GONE
                cvPrivateMessageProfileTeman.visibility = View.GONE
                cvAddFriendProfileTeman.visibility = View.GONE
                cvLikeProfileTeman.visibility = View.GONE
                cvBlockProfileTeman.animate().translationY(0F)
                cvPrivateMessageProfileTeman.animate().translationY(0F)
                cvAddFriendProfileTeman.animate().translationY(0F)
                cvLikeProfileTeman.animate().translationY(0F)

                flag = true
            }
        }

        cvAddFriendProfileTeman.setOnClickListener {
            presenterSearchFriend?.addFriend(idUser!!, idFriend!!)
        }

        initAddFriend()
    }

    fun initAddFriend() {
        presenterSearchFriend?.statusAddFriend()!!.observe(this) {
            if (it == true) {
                startActivity(Intent(this, SearchFriendActivity::class.java))
            }
        }
    }

    override fun showLoadingProfile() {
        progressDialog.show(this, "Harap menunggu...")
    }

    override fun hideLoadingProfile() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastProfile(message: String) {
        customToast.customToast(this, message)
    }

    override fun getData(profile: ProfileModel) {
        tvUsernameHeaderProfileTeman.text = profile.username
        tvUsernameProfileTeman.text = profile.username
        tvStatusMessageProfileTeman.text = profile.statusMessage
        tvNamaProfileTeman.text = profile.namaUser
        tvTanggalJoinProfileTeman.text = profile.createDate

        Glide.with(this).load("https://idfriendme.com/imageapps/" + profile.fotoProfile).into(ivFotoProfileTeman)

        if (profile.statusOnline.equals("1")) {
            cvIndicatorStatusProfileTeman.setCardBackgroundColor(resources.getColor(R.color.greenLight))
        } else if (profile.statusOnline.equals("2")) {
            cvIndicatorStatusProfileTeman.setCardBackgroundColor(resources.getColor(R.color.yellowLight))
        } else if (profile.statusOnline.equals("3")) {
            cvIndicatorStatusProfileTeman.setCardBackgroundColor(resources.getColor(R.color.redLight))
        } else if (profile.statusOnline.equals("4")) {
            cvIndicatorStatusProfileTeman.setCardBackgroundColor(resources.getColor(R.color.white))
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