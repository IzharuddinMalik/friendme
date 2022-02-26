package com.friendme.contract

import com.friendme.ui.dashboard.model.ListCariRoomModel

interface CariRoomContract {

    interface cariRoomView {
        fun showLoadingCariRoom()
        fun hideLoadingCariRoom()
        fun showToastCariRoom(message : String)
        fun getData(data : MutableList<ListCariRoomModel> = mutableListOf())
    }

    interface cariRoomPresenter {
        fun sendCariRoom(idUser : String, namaRoom : String)
    }
}