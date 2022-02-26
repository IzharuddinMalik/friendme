package com.friendme.presenter

import com.friendme.api.ApiClient
import com.friendme.api.WrappedListResponse
import com.friendme.contract.CariRoomContract
import com.friendme.ui.dashboard.model.ListCariRoomModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CariRoomPresenter(v : CariRoomContract.cariRoomView) : CariRoomContract.cariRoomPresenter {
    private var apiService = ApiClient.APIService()
    private var view : CariRoomContract.cariRoomView? = v
    private var listRoom = mutableListOf<ListCariRoomModel>()
    override fun sendCariRoom(idUser: String, namaRoom: String) {
        val request = apiService.cariRoom(idUser, namaRoom)
        view?.showLoadingCariRoom()
        request.enqueue(object : Callback<WrappedListResponse<ListCariRoomModel>> {
            override fun onFailure(
                call: Call<WrappedListResponse<ListCariRoomModel>>,
                t: Throwable
            ) {
                view?.hideLoadingCariRoom()
                view?.showToastCariRoom(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<ListCariRoomModel>>,
                response: Response<WrappedListResponse<ListCariRoomModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingCariRoom()

                        listRoom.clear()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            view?.showToastCariRoom(body.message)
                            for (i in 0 until body.data.size) {
                                val room = body.data[i]

                                listRoom.add(ListCariRoomModel("" + room.idRoom, "" + room.namaRoom))
                            }

                            view?.getData(listRoom)
                        } else {
                            view?.showToastCariRoom(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}