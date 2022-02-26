package com.friendme.presenter

import androidx.lifecycle.MutableLiveData
import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedResponse
import com.friendme.contract.RessetPasswordContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RessetPasswordPresenter(v : RessetPasswordContract.ressetPassView) {

    private var apiService = ApiClient.APIService()
    private var view : RessetPasswordContract.ressetPassView? = v
    private var verifEmail : MutableLiveData<Boolean> = MutableLiveData()
    private var successResset : MutableLiveData<Boolean> = MutableLiveData()

    fun verifEmail(email : String) {
        val request = apiService.lupaPassword(email)
        view?.showLoadingRessetPass()
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.hideLoadingRessetPass()
                view?.showToastRessetPass(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingRessetPass()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            verifEmail.value = true
                        } else {
                            verifEmail.value = false
                            view?.showToastRessetPass(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                        view?.hideLoadingRessetPass()
                    }
                }
            }
        })
    }

    fun ressetPassword(email : String, password : String) {
        val request = apiService.ressetPass(email, password)
        view?.showLoadingRessetPass()
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.hideLoadingRessetPass()
                view?.showToastRessetPass(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingRessetPass()

                        val body = response.body()
                        if (body!!.success.equals("1")) {
                            successResset.value = true
                        } else {
                            successResset.value = false
                            view?.showToastRessetPass(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                        view?.hideLoadingRessetPass()
                    }
                }
            }
        })
    }

    fun onVerifEmail() : MutableLiveData<Boolean> {
        return verifEmail
    }

    fun onRessetPass() : MutableLiveData<Boolean> {
        return successResset
    }
}