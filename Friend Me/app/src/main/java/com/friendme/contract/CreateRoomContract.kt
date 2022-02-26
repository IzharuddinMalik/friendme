package com.friendme.contract

interface CreateRoomContract {

    interface createRoomView {
        fun showLoadingCreateRoom()
        fun hideLoadingCreateRoom()
        fun showToastCreateRoom(message : String)
        fun successCreateRoom()
    }
}