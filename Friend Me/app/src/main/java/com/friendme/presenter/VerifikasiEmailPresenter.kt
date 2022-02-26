package com.friendme.presenter

import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedResponse
import com.friendme.contract.VerifikasiEmailContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifikasiEmailPresenter(v : VerifikasiEmailContract.verifikasiEmailView) : VerifikasiEmailContract.verifikasiEmailPresenter {

    private var apiService = ApiClient.APIService()
    private var view : VerifikasiEmailContract.verifikasiEmailView? = v

    override fun sendVerifikasi(email: String) {
        val request = apiService.verifikasiEmail(email)
        view?.showLoadingVerifikasiEmail()
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.hideLoadingVerifikasiEmail()
                view?.showToastVerifikasiEmail(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingVerifikasiEmail()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            view?.successVerifikasiEmail()
                            view?.showToastVerifikasiEmail(body.message)
                        } else {
                            view?.showToastVerifikasiEmail(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}