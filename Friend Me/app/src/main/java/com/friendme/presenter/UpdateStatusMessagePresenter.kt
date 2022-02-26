package com.friendme.presenter

import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedResponse
import com.friendme.contract.UpdateStatusMessageContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateStatusMessagePresenter(v : UpdateStatusMessageContract.updateStatusMessageView) : UpdateStatusMessageContract.updateStatusMessagePresenter {

    private var apiService = ApiClient.APIService()
    private var view : UpdateStatusMessageContract.updateStatusMessageView? = v

    override fun sendUpdateStatusMessage(idUser: String, message: String) {
        val request = apiService.updateStatusMessage(idUser, message)
        view?.showLoadingUpdateStatusMessage()
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.hideLoadingUpdateStatusMessage()
                view?.showToastUpdateStatusMessage(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingUpdateStatusMessage()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            view?.showToastUpdateStatusMessage(body.message)
                        } else {
                            view?.showToastUpdateStatusMessage(body.message)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}