package com.friendme.contract

interface EditProfileContract {

    interface editProfileView {
        fun showLoadingEditProfile()
        fun hideLoadingEditProfile()
        fun showToastEditProfile(message : String)
        fun successEditProfile()
    }

    interface editProfilePresenter {
        fun sendEditProfile(idUser : String, namaUser : String, sex : String, dateOfBirth : String, aboutProfile : String, fotoProfile : String)
    }
}