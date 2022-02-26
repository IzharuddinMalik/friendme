package com.friendme.contract

import androidx.lifecycle.MutableLiveData
import com.friendme.ui.dashboard.model.ListFeedModel

interface ListFeedContract {

    interface listFeedView {
        fun showToastListFeed(message : String)
        fun showLoadingFeed()
        fun hideLoadingFeed()
    }

    interface listFeedPresenter {
        fun sendGetListFeed(idUser : String)
        fun onRefreshData() : MutableLiveData<MutableList<ListFeedModel>>
    }
}