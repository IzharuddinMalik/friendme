package com.friendme.contract

interface GiftContract {

    interface giftContractView {
        fun showLoadingGift()
        fun hideLoadingGift()
        fun showToastGift(message : String)
    }
}