package com.friendme.presenter

import com.friendme.api.ApiClient
import com.friendme.api.WrappedResponse
import com.friendme.contract.GetProfileContract
import com.friendme.ui.profile.model.ProfileModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetProfilePresenter(v : GetProfileContract.getProfileView) : GetProfileContract.getProfilePresenter {

    private var apiService = ApiClient.APIService()
    private var view : GetProfileContract.getProfileView? = v

    override fun sendGetProfile(idUser: String) {
        val request = apiService.getProfileAkun(idUser)
        view?.showLoadingProfile()
        request.enqueue(object : Callback<WrappedResponse<ProfileModel>> {
            override fun onFailure(call: Call<WrappedResponse<ProfileModel>>, t: Throwable) {
                view?.hideLoadingProfile()
                view?.showToastProfile(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<ProfileModel>>,
                response: Response<WrappedResponse<ProfileModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingProfile()
                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            view?.getData(body.data)
                            view?.showToastProfile(body.message)
                        } else {
                            view?.showToastProfile(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}