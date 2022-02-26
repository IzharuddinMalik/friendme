package com.friendme.contract

interface UpdateStatusOnlineContract {

    interface updateStatusOnlineView {
        fun showToastUpdateStatusOnline(message : String)
    }

    interface updateStatusOnlinePresenter {
        fun sendUpateStatusOnline(idUser : String, statusOnline : String)
    }
}