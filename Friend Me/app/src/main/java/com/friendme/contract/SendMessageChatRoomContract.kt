package com.friendme.contract

interface SendMessageChatRoomContract {

    interface sendMessageChatRoomView{
        fun showToastSendMessageChatRoom(message : String)
        fun successSendMessageChatRoom()
    }

    interface sendMessageChatRoomPresenter {
        fun sendMessageChatRoom(idUser : String, idRoom : String, message : String)
    }
}