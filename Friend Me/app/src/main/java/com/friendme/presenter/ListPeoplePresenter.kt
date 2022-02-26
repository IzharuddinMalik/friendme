package com.friendme.presenter

import androidx.lifecycle.MutableLiveData
import com.friendme.api.ApiClient
import com.friendme.api.WrappedListResponse
import com.friendme.api.WrappedResponse
import com.friendme.contract.ListPeopleContract
import com.friendme.ui.balance.model.DataPeopleModel
import com.friendme.ui.balance.model.PeopleModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.fixedRateTimer

class ListPeoplePresenter(v : ListPeopleContract.listPeopleView) {

    private var apiService = ApiClient.APIService()
    private var view : ListPeopleContract.listPeopleView? = v
    private var listAdmin = mutableListOf<PeopleModel>()
    private var listAdminLive : MutableLiveData<MutableList<PeopleModel>> = MutableLiveData()
    private var listMerchant = mutableListOf<PeopleModel>()
    private var listMerchantLive : MutableLiveData<MutableList<PeopleModel>> = MutableLiveData()
    private var listMentor = mutableListOf<PeopleModel>()
    private var listMentorLive : MutableLiveData<MutableList<PeopleModel>> = MutableLiveData()
    private var listStaff = mutableListOf<PeopleModel>()
    private var listStaffLive : MutableLiveData<MutableList<PeopleModel>> = MutableLiveData()

    fun getListPeople(idUser : String) {
        val request = apiService.getListPeople(idUser)
        view?.showLoadingListPeople()
        request.enqueue(object : Callback<WrappedResponse<DataPeopleModel>> {
            override fun onFailure(call: Call<WrappedResponse<DataPeopleModel>>, t: Throwable) {
                view?.hideLoadingListPeople()
                view?.showToastListPeople(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<DataPeopleModel>>,
                response: Response<WrappedResponse<DataPeopleModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingListPeople()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            for (i in 0 until body.data.adminPeople!!.size) {
                                val admin = body.data.adminPeople!![i]

                                listAdmin.add(PeopleModel("" + admin.idUserPeople, "" + admin.usernamePeople, "" + admin.fotoProfilePeople, "" + admin.statusOnlinePeople, "" + admin.userLevelPeople, "" + admin.userSex, "" + admin.levelManagement))
                            }

                            for (i in 0 until body.data.merchantPeople!!.size) {
                                val merchant = body.data.merchantPeople!![i]

                                listMerchant.add(PeopleModel("" + merchant.idUserPeople, "" + merchant.usernamePeople, "" + merchant.fotoProfilePeople, "" + merchant.statusOnlinePeople, "" + merchant.userLevelPeople, "" + merchant.userSex, "" + merchant.levelManagement))
                            }

                            for (i in 0 until body.data.mentorPeople!!.size) {
                                val mentor = body.data.mentorPeople!![i]

                                listMentor.add(PeopleModel("" + mentor.idUserPeople, "" + mentor.usernamePeople, "" + mentor.fotoProfilePeople, "" + mentor.statusOnlinePeople, "" + mentor.userLevelPeople, "" + mentor.userSex, "" + mentor.levelManagement))
                            }

                            for (i in 0 until body.data.staffPeople!!.size) {
                                val staff = body.data.staffPeople!![i]

                                listStaff.add(PeopleModel("" + staff.idUserPeople, "" + staff.usernamePeople, "" + staff.fotoProfilePeople, "" + staff.statusOnlinePeople, "" + staff.userLevelPeople, "" + staff.userSex, "" + staff.levelManagement))
                            }

                            listAdminLive.value = listAdmin
                            listMerchantLive.value = listMerchant
                            listMentorLive.value = listMentor
                            listStaffLive.value = listStaff
                        } else {
                            view?.showToastListPeople(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun onRefreshAdmin() : MutableLiveData<MutableList<PeopleModel>> {
        return listAdminLive
    }

    fun onRefreshMerchant() : MutableLiveData<MutableList<PeopleModel>> {
        return listMerchantLive
    }

    fun onRefreshMentor() : MutableLiveData<MutableList<PeopleModel>> {
        return listMentorLive
    }

    fun onRefreshStaff() : MutableLiveData<MutableList<PeopleModel>> {
        return listStaffLive
    }
}