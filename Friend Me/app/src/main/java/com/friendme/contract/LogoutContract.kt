package com.friendme.contract

interface LogoutContract {

    interface logoutAkunView {
        fun showLoadingLogout()
        fun hideLoadingLogout()
        fun getMessageLogout(message : String)
    }
}