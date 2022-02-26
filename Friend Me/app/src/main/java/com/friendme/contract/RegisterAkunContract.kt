package com.friendme.contract

interface RegisterAkunContract {

    interface registerAkunView {
        fun showLoadingRegister()
        fun hideLoadingRegister()
        fun showToastRegister(message : String)
        fun successRegister()
    }

    interface registerAkunPresenter{
        fun sendRegister(nama : String, username : String, email : String, sex : String, password : String, token : String)
    }
}