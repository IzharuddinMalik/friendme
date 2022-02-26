package com.friendme.contract

interface JoinRoomContract {

    interface joinRoomView {
        fun showLoadingJoinRoom()
        fun hideLoadingJoinRoom()
        fun showToastJoinRoom(message : String)
        fun successJoinRoom(namaRoom : String)
        fun openRoom()
    }

    interface joinRoomPresenter {
        fun sendJoinRoom(idUser : String, idRoom : String)
    }
}