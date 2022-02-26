package com.friendme.contract

interface VerifikasiEmailContract {

    interface verifikasiEmailView {
        fun showLoadingVerifikasiEmail()
        fun hideLoadingVerifikasiEmail()
        fun showToastVerifikasiEmail(message : String)
        fun successVerifikasiEmail()
    }

    interface verifikasiEmailPresenter {
        fun sendVerifikasi(email : String)
    }
}