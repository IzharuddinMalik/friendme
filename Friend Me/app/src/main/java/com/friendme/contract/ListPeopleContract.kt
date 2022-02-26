package com.friendme.contract

interface ListPeopleContract {

    interface listPeopleView {
        fun showLoadingListPeople()
        fun hideLoadingListPeople()
        fun showToastListPeople(message : String)
    }
}