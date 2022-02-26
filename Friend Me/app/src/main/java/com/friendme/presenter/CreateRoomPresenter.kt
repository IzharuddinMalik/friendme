package com.friendme.presenter

import androidx.lifecycle.MutableLiveData
import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedListResponse
import com.friendme.api.WrappedResponse
import com.friendme.contract.CreateRoomContract
import com.friendme.ui.dashboard.model.CategoryRoomModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateRoomPresenter(v : CreateRoomContract.createRoomView) {

    private var apiService = ApiClient.APIService()
    private var view : CreateRoomContract.createRoomView? = v
    private var listCategoryRoom = mutableListOf<CategoryRoomModel>()
    private var listCategoryLive : MutableLiveData<MutableList<CategoryRoomModel>> = MutableLiveData()

    fun sendCreateRoom(idUser: String, namaRoom: String, descRoom: String, idCategoryRoom : String) {
        val request = apiService.createRoom(idUser, namaRoom, descRoom, idCategoryRoom)
        view?.showLoadingCreateRoom()
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.hideLoadingCreateRoom()
                view?.showToastCreateRoom(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingCreateRoom()

                        val body = response.body()
                        if (body!!.success.equals("1")) {
                            view?.showToastCreateRoom(body.message)
                            view?.successCreateRoom()
                        } else {
                            view?.showToastCreateRoom(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun getCategoryRoom(idUser : String) {
        val request = apiService.listCatRoom(idUser)
        view?.showLoadingCreateRoom()
        request.enqueue(object : Callback<WrappedListResponse<CategoryRoomModel>> {
            override fun onFailure(
                call: Call<WrappedListResponse<CategoryRoomModel>>,
                t: Throwable
            ) {
                view?.hideLoadingCreateRoom()
                view?.showToastCreateRoom(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<CategoryRoomModel>>,
                response: Response<WrappedListResponse<CategoryRoomModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingCreateRoom()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            for (i in 0 until body.data.size) {
                                val data = body.data[i]

                                listCategoryRoom.add(CategoryRoomModel("" + data.idCategoryRoom, "" + data.categoryRoom))
                            }

                            listCategoryLive.value = listCategoryRoom
                        } else {
                            view?.showToastCreateRoom(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun onRefreshCatRoom() : MutableLiveData<MutableList<CategoryRoomModel>> {
        return listCategoryLive
    }
}