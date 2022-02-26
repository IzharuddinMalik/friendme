package com.friendme.ui.detailroom.model

import java.util.*

class SendChatRoomModel {
    var messageText: String? = null
    var messageUser: String? = null
    var messageTime: Long = 0
    var userManagement : String? = null

    constructor(messageText: String?, messageUser: String?, userManagement : String?) {
        this.messageText = messageText
        this.messageUser = messageUser
        this.userManagement = userManagement

        // Initialize to current time
        messageTime = Date().getTime()
    }

    constructor() {}
}