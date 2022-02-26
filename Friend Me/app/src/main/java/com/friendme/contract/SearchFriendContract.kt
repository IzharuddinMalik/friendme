package com.friendme.contract

interface SearchFriendContract {

    interface searchFriendView {
        fun showLoadingSearchFriend()
        fun hideLoadingSearchFriend()
        fun showToastSearchFriend(message : String)
    }
}