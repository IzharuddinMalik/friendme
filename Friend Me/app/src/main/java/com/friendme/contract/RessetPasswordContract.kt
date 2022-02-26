package com.friendme.contract

interface RessetPasswordContract {

    interface ressetPassView {
        fun showLoadingRessetPass()
        fun hideLoadingRessetPass()
        fun showToastRessetPass(message : String)
    }
}