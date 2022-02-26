package com.friendme.contract

interface AdminSectionContract {

    interface adminSectionView {
        fun showLoadingAdminSection()
        fun hideLoadingAdminSection()
        fun showToastAdminSection(message : String)
    }
}