package com.friendme.contract

import androidx.lifecycle.MutableLiveData
import com.friendme.ui.detailroom.model.DetailRoomModel
import com.friendme.ui.detailroom.model.ListChatRoomModel

interface DetailRoomContract {

    interface detailRoomView {
        fun showToastDetailRoom(message : String)
        fun getMessageLeave(message: String)
    }
}