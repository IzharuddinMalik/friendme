package com.friendme.presenter

import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedResponse
import com.friendme.contract.RegisterAkunContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterAkunPresenter(v : RegisterAkunContract.registerAkunView) : RegisterAkunContract.registerAkunPresenter {

    private var apiService = ApiClient.APIService()
    private var view : RegisterAkunContract.registerAkunView? = v

    override fun sendRegister(
        nama: String,
        username: String,
        email : String,
        sex: String,
        password: String,
        token: String
    ) {
        val request = apiService.signupAkun(nama, username, email, sex, password, token)
        view?.showLoadingRegister()
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.hideLoadingRegister()
                view?.showToastRegister(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingRegister()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            view?.showToastRegister(body.message)
                            view?.successRegister()
                        } else {
                            view?.showToastRegister(body.message)
                        }
                    }catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })

    }
}