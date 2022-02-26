package com.friendme.presenter

import androidx.lifecycle.MutableLiveData
import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedListResponse
import com.friendme.api.WrappedResponse
import com.friendme.contract.DetailRoomContract
import com.friendme.ui.detailroom.model.DetailRoomModel
import com.friendme.ui.detailroom.model.LeaveRoomModel
import com.friendme.ui.detailroom.model.ListAnggotaModel
import com.friendme.ui.detailroom.model.ListChatRoomModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRoomPresenter(v : DetailRoomContract.detailRoomView) {
    private var apiService = ApiClient.APIService()
    private var view : DetailRoomContract.detailRoomView? = v
    private var listAnggotaRoom = mutableListOf<ListAnggotaModel>()
    private var listAnggotaRoomLive : MutableLiveData<MutableList<ListAnggotaModel>> = MutableLiveData()
    private var statusKickUser : MutableLiveData<Boolean> = MutableLiveData()
    private var dataRoom : MutableLiveData<DetailRoomModel> = MutableLiveData()

    fun sendDetailRoom(idUser: String, idRoom: String) {
        val request = apiService.detailRoom(idUser, idRoom)
        request.enqueue(object : Callback<WrappedResponse<DetailRoomModel>> {
            override fun onFailure(call: Call<WrappedResponse<DetailRoomModel>>, t: Throwable) {
                view?.showToastDetailRoom(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<DetailRoomModel>>,
                response: Response<WrappedResponse<DetailRoomModel>>
            ) {
                if (response.isSuccessful) {
                    try {

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            dataRoom.value = body.data!!
                        } else {
                            view?.showToastDetailRoom(body.message)
                        }

                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun listAnggotaRoom(idUser: String, idRoom : String) {
        val request = apiService.listAnggotaRoom(idUser, idRoom)
        listAnggotaRoom.clear()
        request.enqueue(object : Callback<WrappedListResponse<ListAnggotaModel>> {
            override fun onFailure(
                call: Call<WrappedListResponse<ListAnggotaModel>>,
                t: Throwable
            ) {
                view?.showToastDetailRoom(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<ListAnggotaModel>>,
                response: Response<WrappedListResponse<ListAnggotaModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            for (i in 0 until body.data.size) {
                                val data = body.data[i]

                                listAnggotaRoom.add(ListAnggotaModel("" + data.idUser, "" + data.username))
                            }

                            listAnggotaRoomLive.value = listAnggotaRoom
                        } else {
                            view?.showToastDetailRoom(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun kickRoom(idUser: String, idRoom: String) {
        val request = apiService.kickRoom(idUser, idRoom)
        request.enqueue(object : Callback<WrappedResponse<LeaveRoomModel>> {
            override fun onFailure(call: Call<WrappedResponse<LeaveRoomModel>>, t: Throwable) {
                view?.showToastDetailRoom(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<LeaveRoomModel>>,
                response: Response<WrappedResponse<LeaveRoomModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            view?.getMessageLeave(body.data.leaveMessage!!)
                            statusKickUser.value = true
                        } else {
                            view?.showToastDetailRoom(body.message)
                            statusKickUser.value = false
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun onListAnggota() : MutableLiveData<MutableList<ListAnggotaModel>> {
        return listAnggotaRoomLive
    }

    fun onKickUser() : MutableLiveData<Boolean>{
        return statusKickUser
    }

    fun onDataRoom() : MutableLiveData<DetailRoomModel> {
        return dataRoom
    }
}