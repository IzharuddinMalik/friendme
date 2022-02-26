package com.friendme.contract

interface UpdateStatusMessageContract {

    interface updateStatusMessageView {
        fun showLoadingUpdateStatusMessage()
        fun hideLoadingUpdateStatusMessage()
        fun showToastUpdateStatusMessage(message : String)
    }

    interface updateStatusMessagePresenter {
        fun sendUpdateStatusMessage(idUser : String, message : String)
    }
}