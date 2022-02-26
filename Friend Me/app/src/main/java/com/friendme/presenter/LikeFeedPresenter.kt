package com.friendme.presenter

import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedResponse
import com.friendme.contract.LikeFeedContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikeFeedPresenter(v : LikeFeedContract.likeFeedView) : LikeFeedContract.likeFeedPresenter {

    private var apiService = ApiClient.APIService()
    private var view : LikeFeedContract.likeFeedView? = v
    override fun sendLikeFeed(idUser: String, idFeed: String) {
        val request = apiService.likeFeed(idUser, idFeed)
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.showToastLikeFeed(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")) {
                            view?.updateFeed()
                        } else {
                            view?.showToastLikeFeed(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}