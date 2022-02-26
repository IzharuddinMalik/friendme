package com.friendme.presenter

import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedResponse
import com.friendme.contract.UpdateStatusOnlineContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateStatusOnlinePresenter(v : UpdateStatusOnlineContract.updateStatusOnlineView) : UpdateStatusOnlineContract.updateStatusOnlinePresenter {

    private var apiService = ApiClient.APIService()
    private var view : UpdateStatusOnlineContract.updateStatusOnlineView? = v

    override fun sendUpateStatusOnline(idUser: String, statusOnline: String) {
        val request = apiService.updateStatusOnline(idUser, statusOnline)
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.showToastUpdateStatusOnline(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        val body = response.body()

                        if (body!!.success.equals("1")) {

                        } else {
                            view?.showToastUpdateStatusOnline(body.message)
                        }
                    } catch (e : JSONException){
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}