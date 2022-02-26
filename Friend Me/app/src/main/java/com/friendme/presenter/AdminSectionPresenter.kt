package com.friendme.presenter

import androidx.lifecycle.MutableLiveData
import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedListResponse
import com.friendme.api.WrappedResponse
import com.friendme.contract.AdminSectionContract
import com.friendme.ui.dashboardadmin.managementuser.model.LevelUserModel
import com.friendme.ui.dashboardadmin.managementuser.model.ListUserModel
import com.friendme.ui.searchfriend.model.ListSearchFriendModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminSectionPresenter(v : AdminSectionContract.adminSectionView) {

    private var apiService = ApiClient.APIService()
    private var view : AdminSectionContract.adminSectionView? = v
    private var listUser = mutableListOf<ListUserModel>()
    private var listUserLive : MutableLiveData<MutableList<ListUserModel>> = MutableLiveData()
    private var listLevelUser = mutableListOf<LevelUserModel>()
    private var listLevelUserLive : MutableLiveData<MutableList<LevelUserModel>> = MutableLiveData()
    private var changeStatusUser : MutableLiveData<Boolean> = MutableLiveData()

    fun getLevelUser(idUser : String) {
        val request = apiService.listLevelUser(idUser)
        view?.showLoadingAdminSection()
        request.enqueue(object : Callback<WrappedListResponse<LevelUserModel>> {
            override fun onFailure(call: Call<WrappedListResponse<LevelUserModel>>, t: Throwable) {
                view?.hideLoadingAdminSection()
                view?.showToastAdminSection(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<LevelUserModel>>,
                response: Response<WrappedListResponse<LevelUserModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingAdminSection()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            for (i in 0 until body.data.size) {
                                val data = body.data[i]

                                listLevelUser.add(LevelUserModel("" + data.idLevel, "" + data.levelUser))
                            }

                            listLevelUserLive.value = listLevelUser
                        } else {
                            view?.hideLoadingAdminSection()
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                        view?.hideLoadingAdminSection()
                    }
                }
            }
        })
    }

    fun listUser(idUser: String) {
        val request = apiService.listUserAdmin(idUser)
        view?.showLoadingAdminSection()
        request.enqueue(object : Callback<WrappedListResponse<ListUserModel>> {
            override fun onFailure(
                call: Call<WrappedListResponse<ListUserModel>>,
                t: Throwable
            ) {
                view?.hideLoadingAdminSection()
                view?.showToastAdminSection(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<ListUserModel>>,
                response: Response<WrappedListResponse<ListUserModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingAdminSection()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            for (i in 0 until body.data.size) {
                                val data = body.data[i]

                                listUser.add(ListUserModel("" + data.idUser, "" + data.username, "" + data.fotoProfile, "" + data.idLevelManagement, "" + data.levelManagement))
                            }

                            listUserLive.value = listUser
                        } else {
                            view?.hideLoadingAdminSection()
                            view?.showToastAdminSection(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                        view?.hideLoadingAdminSection()
                    }
                }
            }
        })
    }

    fun setUserManagement(idAdmin : String, idUser : String, levelManagement : String) {
        val request = apiService.setUserManagement(idAdmin, idUser, levelManagement)
        view?.showLoadingAdminSection()
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.hideLoadingAdminSection()
                view?.showToastAdminSection(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingAdminSection()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            changeStatusUser.value = true
                        } else {
                            changeStatusUser.value = false
                            view?.showToastAdminSection(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                        view?.hideLoadingAdminSection()
                    }
                }
            }
        })
    }

    fun onUserLevel() : MutableLiveData<MutableList<LevelUserModel>> {
        return listLevelUserLive
    }

    fun onUserList() : MutableLiveData<MutableList<ListUserModel>> {
        return listUserLive
    }

    fun onChangeStatus() : MutableLiveData<Boolean> {
        return changeStatusUser
    }
}