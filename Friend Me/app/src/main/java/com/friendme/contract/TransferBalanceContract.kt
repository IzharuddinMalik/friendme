package com.friendme.contract

interface TransferBalanceContract {

    interface transferBalanceView {
        fun showLoadingTransferBalance()
        fun hideLoadingTransferBalance()
        fun showToastTransferBalance(message: String)
        fun successTransferBalance()
    }
}