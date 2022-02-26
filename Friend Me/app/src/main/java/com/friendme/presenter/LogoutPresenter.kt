package com.friendme.presenter

import androidx.lifecycle.MutableLiveData
import com.friendme.api.ApiClient
import com.friendme.api.WrappedResponse
import com.friendme.contract.LogoutContract
import com.friendme.ui.dashboard.model.LogoutMessageModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogoutPresenter(v : LogoutContract.logoutAkunView) {

    private var apiService = ApiClient.APIService()
    private var view : LogoutContract.logoutAkunView? = v

    fun logoutAkun(idUser : String) {
        val request = apiService.logoutAkun(idUser)
        view?.showLoadingLogout()
        request.enqueue(object : Callback<WrappedResponse<LogoutMessageModel>> {
            override fun onFailure(call: Call<WrappedResponse<LogoutMessageModel>>, t: Throwable) {
                view?.hideLoadingLogout()
            }

            override fun onResponse(
                call: Call<WrappedResponse<LogoutMessageModel>>,
                response: Response<WrappedResponse<LogoutMessageModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingLogout()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            view?.getMessageLogout(body.data.logoutMessage!!)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}