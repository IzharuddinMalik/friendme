package com.friendme.contract

import com.friendme.ui.dashboard.model.ListRoomModel

interface ListRoomUserContract {

    interface listRoomUserView {
        fun showLoadingRoomUser()
        fun hideLoadingRoomUser()
        fun showToastRoomUser(message : String)
    }
}