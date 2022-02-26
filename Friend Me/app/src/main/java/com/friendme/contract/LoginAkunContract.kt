package com.friendme.contract

interface LoginAkunContract {

    interface loginAkunView {
        fun showLoadingLoginAkun()
        fun hideLoadingLoginAkun()
        fun showToastLoginAkun(message : String)
        fun successLoginAkun(idUser : String, levelManagement : String)
    }

    interface loginAkunPresenter {
        fun sendLogin(username : String, password : String)
    }
}