package com.friendme.presenter

import androidx.lifecycle.MutableLiveData
import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedListResponse
import com.friendme.api.WrappedResponse
import com.friendme.contract.NewDetailRoomContract
import com.friendme.ui.detailroom.model.DetailRoomModel
import com.friendme.ui.detailroom.model.ListAnggotaModel
import com.friendme.ui.detailroom.model.ListChatRoomModel
import com.friendme.ui.detailroom.model.NewDetailRoomModel
import kotlinx.coroutines.currentCoroutineContext
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewDetailRoomPresenter(v : NewDetailRoomContract.newDetailRoomView) {

    private var apiService = ApiClient.APIService()
    private var view : NewDetailRoomContract.newDetailRoomView? = v
    private var listRoom = mutableListOf<NewDetailRoomModel>()
    private var listRoomLive : MutableLiveData<MutableList<NewDetailRoomModel>> = MutableLiveData()

    fun sendDetailRoom(idUser: String) {
        val request = apiService.newListDetailRoom(idUser)
        view?.showLoadingDetailRoom()
        request.enqueue(object : Callback<WrappedListResponse<NewDetailRoomModel>> {
            override fun onFailure(
                call: Call<WrappedListResponse<NewDetailRoomModel>>,
                t: Throwable
            ) {
                view?.showToastDetailRoom(t.message!!)
                view?.hideLoadingDetailRoom()
            }

            override fun onResponse(
                call: Call<WrappedListResponse<NewDetailRoomModel>>,
                response: Response<WrappedListResponse<NewDetailRoomModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingDetailRoom()
                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            for (i in 0 until body.data.size) {
                                val data = body.data[i]

                                listRoom.add(NewDetailRoomModel("" + data.idRoom, "" + data.namaRoom))
                            }

                            listRoomLive.value = listRoom
                        } else {
                            view?.showToastDetailRoom(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                        view?.hideLoadingDetailRoom()
                    }
                }
            }
        })
    }

    fun onRefreshData(): MutableLiveData<MutableList<NewDetailRoomModel>> {
        return listRoomLive
    }
}