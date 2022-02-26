package com.friendme.presenter

import androidx.lifecycle.MutableLiveData
import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedResponse
import com.friendme.contract.PrivateMessageContract
import com.friendme.ui.privatemessage.PrivateMessageActivity
import com.friendme.ui.privatemessage.model.PrivateMessageModel
import com.friendme.ui.privatemessage.model.PrivateMessageRoomModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrivateMessagePresenter(v : PrivateMessageContract.privateMessageView) {

    private var apiService = ApiClient.APIService()
    private var view : PrivateMessageContract.privateMessageView? = v
    private var privateMessageLive : MutableLiveData<PrivateMessageRoomModel> = MutableLiveData()
    private var statusSuccess : Boolean = false
    private var statusSuccessLive : MutableLiveData<Boolean> = MutableLiveData()

    fun getListPM(idUser : String, idUserFrom : String, tokenMessage : String) {
        val request = apiService.listChatPM(idUser, idUserFrom, tokenMessage)
        request.enqueue(object : Callback<WrappedResponse<PrivateMessageRoomModel>> {
            override fun onFailure(
                call: Call<WrappedResponse<PrivateMessageRoomModel>>,
                t: Throwable
            ) {
                view?.showToastPrivateMessage(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<PrivateMessageRoomModel>>,
                response: Response<WrappedResponse<PrivateMessageRoomModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")) {

                            privateMessageLive.value = body.data!!
                        } else {
                            view?.showToastPrivateMessage(body.message)
                        }
                    } catch (e : JSONException){
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun sendPM(idUserFrom: String, idUserTo : String, message : String, tokenMessage: String) {
        val request = apiService.createSendPM(idUserFrom, idUserTo, message, tokenMessage)
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.showToastPrivateMessage(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            statusSuccess = true
                            statusSuccessLive.value = statusSuccess
                        } else {
                            view?.showToastPrivateMessage(body.message)
                            statusSuccess = false
                            statusSuccessLive.value = statusSuccess
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun onRefreshPrivateMessage() : MutableLiveData<PrivateMessageRoomModel> {
        return privateMessageLive
    }

    fun onStatusSuccess() : MutableLiveData<Boolean> {
        return statusSuccessLive
    }
}