package com.friendme.presenter

import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedResponse
import com.friendme.contract.TransferBalanceContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransferBalancePresenter(v : TransferBalanceContract.transferBalanceView) {

    private var apiService = ApiClient.APIService()
    private var view : TransferBalanceContract.transferBalanceView? = v

    fun transferBalance(idUser : String, usernameDituju : String, balance : String) {
        val request = apiService.transferBalance(idUser, usernameDituju, balance)
        view?.showLoadingTransferBalance()
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.hideLoadingTransferBalance()
                view?.showToastTransferBalance(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingTransferBalance()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            view?.successTransferBalance()
                            view?.showToastTransferBalance(body.message)
                        } else {
                            view?.showToastTransferBalance(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}