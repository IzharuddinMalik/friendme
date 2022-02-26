package com.friendme.presenter

import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedResponse
import com.friendme.contract.SendMessageChatRoomContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendMessageChatRoomPresenter(v : SendMessageChatRoomContract.sendMessageChatRoomView) : SendMessageChatRoomContract.sendMessageChatRoomPresenter {

    private var apiService = ApiClient.APIService()
    private var view : SendMessageChatRoomContract.sendMessageChatRoomView? = v

    override fun sendMessageChatRoom(idUser: String, idRoom: String, message: String) {
        val request = apiService.sendChatRoom(idUser, idRoom, message)
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.showToastSendMessageChatRoom(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            view?.successSendMessageChatRoom()
                        } else {
                            view?.showToastSendMessageChatRoom(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}