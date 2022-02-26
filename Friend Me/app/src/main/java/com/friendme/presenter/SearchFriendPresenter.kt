package com.friendme.presenter

import androidx.lifecycle.MutableLiveData
import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedListResponse
import com.friendme.api.WrappedResponse
import com.friendme.contract.SearchFriendContract
import com.friendme.ui.searchfriend.model.ListSearchFriendModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFriendPresenter(v : SearchFriendContract.searchFriendView) {

    private var apiService = ApiClient.APIService()
    private var view : SearchFriendContract.searchFriendView? = v
    private var listFriendLive : MutableLiveData<MutableList<ListSearchFriendModel>> = MutableLiveData()
    private var listFriend = mutableListOf<ListSearchFriendModel>()
    private var statusAddFriend : MutableLiveData<Boolean> = MutableLiveData()

    fun searchFriend(idUser : String, username : String) {
        val request = apiService.searchFriend(idUser, username)
        view?.showLoadingSearchFriend()
        request.enqueue(object : Callback<WrappedListResponse<ListSearchFriendModel>> {
            override fun onFailure(
                call: Call<WrappedListResponse<ListSearchFriendModel>>,
                t: Throwable
            ) {
                view?.hideLoadingSearchFriend()
                view?.showToastSearchFriend(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<ListSearchFriendModel>>,
                response: Response<WrappedListResponse<ListSearchFriendModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingSearchFriend()

                        val body = response.body()

                        listFriend.clear()

                        if (body!!.success.equals("1")) {
                            for (i in 0 until body.data.size) {
                                val data = body.data[i]

                                listFriend.add(ListSearchFriendModel("" + data.idUser, "" + data.username, "" + data.fotoProfile))
                            }

                            listFriendLive.value = listFriend
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun onRefreshData() : MutableLiveData<MutableList<ListSearchFriendModel>> {
        return listFriendLive
    }

    fun addFriend(idUser: String, idFriend : String) {
        val request = apiService.addFriend(idUser, idFriend)
        view?.showLoadingSearchFriend()
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.hideLoadingSearchFriend()
                view?.showToastSearchFriend(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingSearchFriend()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            view?.showToastSearchFriend(body.message)
                            statusAddFriend.value = true
                        } else {
                            view?.showToastSearchFriend(body.message)
                            statusAddFriend.value = false
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun statusAddFriend() : MutableLiveData<Boolean> {
        return statusAddFriend
    }
}