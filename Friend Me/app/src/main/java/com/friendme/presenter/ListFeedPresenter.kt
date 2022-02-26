package com.friendme.presenter

import androidx.lifecycle.MutableLiveData
import com.friendme.api.ApiClient
import com.friendme.api.WrappedListResponse
import com.friendme.contract.ListFeedContract
import com.friendme.ui.dashboard.model.ListFeedModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFeedPresenter(v : ListFeedContract.listFeedView) : ListFeedContract.listFeedPresenter {
    private var apiService = ApiClient.APIService()
    private var view : ListFeedContract.listFeedView? = v
    private var listFeedLive : MutableLiveData<MutableList<ListFeedModel>> = MutableLiveData()
    private var listFeed = mutableListOf<ListFeedModel>()

    override fun sendGetListFeed(idUser: String) {
        val request = apiService.listFeed(idUser)
        view?.showLoadingFeed()
        request.enqueue(object : Callback<WrappedListResponse<ListFeedModel>> {
            override fun onFailure(call: Call<WrappedListResponse<ListFeedModel>>, t: Throwable) {
                view?.hideLoadingFeed()
                view?.showToastListFeed(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<ListFeedModel>>,
                response: Response<WrappedListResponse<ListFeedModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingFeed()
                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            for (i in 0 until body.data.size) {
                                val data = body.data[i]

                                listFeed.add(ListFeedModel( "" + data.idFeed,"" + data.idUser, "" + data.message, "" + data.imageFeed,
                                    "" + data.createOn, "" + data.likeCount, "" + data.namaUser, "" + data.fotoProfile))
                            }

                            listFeedLive.value = listFeed
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                        view?.hideLoadingFeed()
                    }
                }
            }
        })
    }

    override fun onRefreshData(): MutableLiveData<MutableList<ListFeedModel>> {
        return listFeedLive
    }
}