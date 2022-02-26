package com.friendme.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.friendme.R
import com.friendme.contract.GetProfileContract
import com.friendme.presenter.GetProfilePresenter
import com.friendme.ui.dashboard.DashboardActivity
import com.friendme.ui.gift.GiftSentUserActivity
import com.friendme.ui.gift.SendGiftActivity
import com.friendme.ui.profile.model.ProfileModel
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.inflater_status_friend.view.*

class ProfileActivity : AppCompatActivity(), GetProfileContract.getProfileView {

    private var presenterProfile : GetProfileContract.getProfilePresenter? = null
    private var progressDialog = CustomProgressDialog()
    private var customToast = CustomToast()
    private var idUser : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        presenterProfile = GetProfilePresenter(this)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        idUser = sharedPreferences.getString("idUser", "")

        presenterProfile?.sendGetProfile(idUser!!)

        ivBackProfile.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        cvEditProfile.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        cvGiftReceivedProfile.setOnClickListener {
            startActivity(Intent(this, SendGiftActivity::class.java))
        }

        cvGiftSentProfile.setOnClickListener {
            startActivity(Intent(this, GiftSentUserActivity::class.java))
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
        tvUsernameHeaderProfile.setText(profile.username)
        tvNamaProfile.setText(profile.namaUser)
        tvGenderProfile.setText(profile.sexUser)
        tvTanggalJoinProfile.setText(profile.createDate)
        tvStatusMessageProfile.text = profile.statusMessage
        tvUsernameProfile.text = profile.username
        tvJumlahPostsProfile.text = profile.jumlahPosts
        tvJumlahFollowersProfile.text = profile.jumlahFollower
        tvJumlahFollowingProfile.text = profile.jumlahFollowing
        tvFriendsProfile.text = profile.jumlahFollower
        tvLevelProfile.text = profile.userLevel

        Glide.with(this).load("https://idfriendme.com/imageapps/" + profile.fotoProfile).into(ivFotoProfile)

        if (profile.statusOnline.equals("1")) {
            cvIndicatorStatusProfile.setCardBackgroundColor(resources.getColor(R.color.greenLight))
        } else if (profile.statusOnline.equals("2")) {
            cvIndicatorStatusProfile.setCardBackgroundColor(resources.getColor(R.color.yellowLight))
        } else if (profile.statusOnline.equals("3")) {
            cvIndicatorStatusProfile.setCardBackgroundColor(resources.getColor(R.color.redLight))
        } else if (profile.statusOnline.equals("4")) {
            cvIndicatorStatusProfile.setCardBackgroundColor(resources.getColor(R.color.white))
        }
    }
}