package com.friendme.presenter

import com.friendme.api.ApiClient
import com.friendme.api.WrappedResponse
import com.friendme.contract.LoginAkunContract
import com.friendme.ui.login.LoginModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginAkunPresenter(v : LoginAkunContract.loginAkunView) : LoginAkunContract.loginAkunPresenter {

    private var apiService = ApiClient.APIService()
    private var view : LoginAkunContract.loginAkunView? = v

    override fun sendLogin(username: String, password: String) {
        val request = apiService.loginAkun(username, password)
        view?.showLoadingLoginAkun()
        request.enqueue(object : Callback<WrappedResponse<LoginModel>> {
            override fun onFailure(call: Call<WrappedResponse<LoginModel>>, t: Throwable) {
                view?.hideLoadingLoginAkun()
                view?.showToastLoginAkun(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<LoginModel>>,
                response: Response<WrappedResponse<LoginModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingLoginAkun()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            view?.showToastLoginAkun(body.message)
                            view?.successLoginAkun(body!!.data.idUser!!, body.data!!.levelManagement!!)
                        } else {
                            view?.showToastLoginAkun(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}