package com.friendme.contract

interface CreateFeedContract {
    interface createFeedView {
        fun showLoadingCreateFeed()
        fun hideLoadingCreateFeed()
        fun showToastCreateFeed(message : String)
        fun successCreateFeed()
    }

    interface createFeedPresenter {
        fun sendCreateFeed(idUser : String, message : String, imageFeed : String)
    }
}