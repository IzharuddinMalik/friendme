package com.friendme.presenter

import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedResponse
import com.friendme.contract.JoinRoomContract
import com.friendme.ui.dashboard.model.JoinRoomModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinRoomPresenter(v : JoinRoomContract.joinRoomView) : JoinRoomContract.joinRoomPresenter {

    private var apiService = ApiClient.APIService()
    private var view : JoinRoomContract.joinRoomView? = v

    override fun sendJoinRoom(idUser: String, idRoom: String) {
        val request = apiService.joinRoom(idUser, idRoom)
        view?.showLoadingJoinRoom()
        request.enqueue(object : Callback<WrappedResponse<JoinRoomModel>> {
            override fun onFailure(call: Call<WrappedResponse<JoinRoomModel>>, t: Throwable) {
                view?.hideLoadingJoinRoom()
                view?.showToastJoinRoom(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<JoinRoomModel>>,
                response: Response<WrappedResponse<JoinRoomModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingJoinRoom()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            view?.successJoinRoom(body.data.namaRoom!!)
                        } else if (body!!.success.equals("2")) {
                            view?.showToastJoinRoom(body.message)
                        } else if (body!!.success.equals("3")) {
                            view?.openRoom()
                            view?.showToastJoinRoom(body.message)
                        } else {
                            view?.showToastJoinRoom(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}