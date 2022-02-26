package com.friendme.contract

interface LikeFeedContract {

    interface likeFeedView {
        fun showToastLikeFeed(message : String)
        fun updateFeed()
    }

    interface likeFeedPresenter {
        fun sendLikeFeed(idUser : String, idFeed : String)
    }
}