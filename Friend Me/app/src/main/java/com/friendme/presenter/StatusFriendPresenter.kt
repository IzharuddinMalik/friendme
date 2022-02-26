package com.friendme.presenter

import androidx.lifecycle.MutableLiveData
import com.friendme.api.ApiClient
import com.friendme.api.WrappedListResponse
import com.friendme.contract.StatusFriendContract
import com.friendme.ui.dashboard.model.StatusFriendModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatusFriendPresenter(v : StatusFriendContract.statusFriendView) {

    private var apiService = ApiClient.APIService()
    private var view : StatusFriendContract.statusFriendView? = v
    private var listStatusFriend = mutableListOf<StatusFriendModel>()
    private var listStatusFriendLive : MutableLiveData<MutableList<StatusFriendModel>> = MutableLiveData()

    fun statusFriend(iduser : String) {
        val request = apiService.statusFriend(iduser)
        request.enqueue(object : Callback<WrappedListResponse<StatusFriendModel>> {
            override fun onFailure(
                call: Call<WrappedListResponse<StatusFriendModel>>,
                t: Throwable
            ) {
                view?.showToastStatusFriend(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<StatusFriendModel>>,
                response: Response<WrappedListResponse<StatusFriendModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            for (i in 0 until body.data.size) {
                                val data = body.data[i]

                                listStatusFriend.add(StatusFriendModel("" + data.idUser, "" + data.username, "" + data.fotoProfile, ""+data.statusMessage, ""+data.statusOnline, "" + data.logouton))
                            }

                            listStatusFriendLive.value = listStatusFriend
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun onRefreshData() : MutableLiveData<MutableList<StatusFriendModel>> {
        return listStatusFriendLive
    }
}