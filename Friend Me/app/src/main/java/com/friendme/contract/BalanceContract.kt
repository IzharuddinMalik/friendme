package com.friendme.contract

import androidx.lifecycle.MutableLiveData
import com.friendme.ui.balance.model.BalanceUserModel

interface BalanceContract {

    interface balanceView {
        fun showLoadingBalance()
        fun hideLoadingBalance()
        fun showToastBalance(message : String)
    }
}