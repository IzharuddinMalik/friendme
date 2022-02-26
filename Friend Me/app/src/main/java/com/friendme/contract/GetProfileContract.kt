package com.friendme.contract

import com.friendme.ui.profile.model.ProfileModel

interface GetProfileContract {

    interface getProfileView {
        fun showLoadingProfile()
        fun hideLoadingProfile()
        fun showToastProfile(message : String)
        fun getData(profile : ProfileModel)
    }

    interface getProfilePresenter {
        fun sendGetProfile(idUser : String)
    }
}