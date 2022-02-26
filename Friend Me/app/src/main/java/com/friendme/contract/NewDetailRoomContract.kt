package com.friendme.contract

interface NewDetailRoomContract {

    interface newDetailRoomView {
        fun showToastDetailRoom(message : String)
        fun showLoadingDetailRoom()
        fun hideLoadingDetailRoom()
    }
}