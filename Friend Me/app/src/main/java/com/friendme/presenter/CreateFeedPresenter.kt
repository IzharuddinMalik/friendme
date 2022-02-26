package com.friendme.presenter

import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedListResponse
import com.friendme.api.WrappedResponse
import com.friendme.contract.CreateFeedContract
import com.friendme.ui.dashboard.model.ListFeedModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateFeedPresenter(v : CreateFeedContract.createFeedView) : CreateFeedContract.createFeedPresenter {

    private var apiService = ApiClient.APIService()
    private var view : CreateFeedContract.createFeedView? = v

    override fun sendCreateFeed(idUser: String, message: String, imageFeed: String) {
        val request = apiService.createFeed(idUser, message, imageFeed)
        view?.showLoadingCreateFeed()
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.hideLoadingCreateFeed()
                view?.showToastCreateFeed(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingCreateFeed()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            view?.successCreateFeed()
                        } else {
                            view?.showToastCreateFeed(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}