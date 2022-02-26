package com.friendme.presenter

import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import com.friendme.api.ApiClient
import com.friendme.api.WrappedListResponse
import com.friendme.api.WrappedResponse
import com.friendme.contract.ListRoomUserContract
import com.friendme.ui.dashboard.model.DataRoomModel
import com.friendme.ui.dashboard.model.ListRoomModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListRoomUserPresenter(v : ListRoomUserContract.listRoomUserView) {

    private var apiService = ApiClient.APIService()
    private var view : ListRoomUserContract.listRoomUserView? = v
    private var listRoomOfficial = mutableListOf<ListRoomModel>()
    private var listRoomOfficialLive : MutableLiveData<MutableList<ListRoomModel>> = MutableLiveData()
    private var listRoomFavourites = mutableListOf<ListRoomModel>()
    private var listRoomFavouritesLive : MutableLiveData<MutableList<ListRoomModel>> = MutableLiveData()
    private var listRoomPlayGames = mutableListOf<ListRoomModel>()
    private var listRoomPlayGamesLive : MutableLiveData<MutableList<ListRoomModel>> = MutableLiveData()
    private var listRoomRecentRoom = mutableListOf<ListRoomModel>()
    private var listRoomRecentRoomLive : MutableLiveData<MutableList<ListRoomModel>> = MutableLiveData()
    private var listRoomRandom = mutableListOf<ListRoomModel>()
    private var listRoomRandomLive : MutableLiveData<MutableList<ListRoomModel>> = MutableLiveData()

    fun sendListRoomUser(idUser: String) {
        val request = apiService.listRoomUser(idUser)
        view?.showLoadingRoomUser()
        request.enqueue(object : Callback<WrappedResponse<DataRoomModel>> {
            override fun onFailure(call: Call<WrappedResponse<DataRoomModel>>, t: Throwable) {
                view?.hideLoadingRoomUser()
                view?.showToastRoomUser(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<DataRoomModel>>,
                response: Response<WrappedResponse<DataRoomModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingRoomUser()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            for (i in 0 until body.data.official!!.size) {
                                val body = body.data.official!![i]

                                listRoomOfficial.add(ListRoomModel("" + body.idRoom, "" + body.namaRoom, "" + body.lastChat, "" + body.jumlahChat, ""+ body.jumlahAnggota, "" + body.quotaRoom))
                            }

                            for (i in 0 until body.data.favourites!!.size) {
                                val body = body.data.favourites!![i]

                                listRoomFavourites.add(ListRoomModel("" + body.idRoom, "" + body.namaRoom, "" + body.lastChat, "" + body.jumlahChat, ""+ body.jumlahAnggota, "" + body.quotaRoom))
                            }

                            for (i in 0 until body.data.playGames!!.size) {
                                val body = body.data.playGames!![i]

                                listRoomPlayGames.add(ListRoomModel("" + body.idRoom, "" + body.namaRoom, "" + body.lastChat, "" + body.jumlahChat, ""+ body.jumlahAnggota, "" + body.quotaRoom))
                            }

                            for (i in 0 until body.data.recentRoom!!.size) {
                                val body = body.data.recentRoom!![i]

                                listRoomRecentRoom.add(ListRoomModel("" + body.idRoom, "" + body.namaRoom, "" + body.lastChat, "" + body.jumlahChat, ""+ body.jumlahAnggota, "" + body.quotaRoom))
                            }

                            for (i in 0 until body.data.random!!.size) {
                                val body = body.data.random!![i]

                                listRoomRandom.add(ListRoomModel("" + body.idRoom, "" + body.namaRoom, "" + body.lastChat, "" + body.jumlahChat, ""+ body.jumlahAnggota, "" + body.quotaRoom))
                            }

                            listRoomOfficialLive.value = listRoomOfficial
                            listRoomFavouritesLive.value = listRoomFavourites
                            listRoomPlayGamesLive.value = listRoomPlayGames
                            listRoomRecentRoomLive.value = listRoomRecentRoom
                            listRoomRandomLive.value = listRoomRandom
                        } else {
                            view?.showToastRoomUser(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun listRoomChatUser(idUser: String) {
        val request = apiService.listRoomChatUser(idUser)
        view?.showLoadingRoomUser()
        request.enqueue(object : Callback<WrappedListResponse<ListRoomModel>> {
            override fun onFailure(call: Call<WrappedListResponse<ListRoomModel>>, t: Throwable) {
                view?.hideLoadingRoomUser()
                view?.showToastRoomUser(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<ListRoomModel>>,
                response: Response<WrappedListResponse<ListRoomModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingRoomUser()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            for (i in 0 until body.data.size) {
                                val body = body.data[i]

                                listRoomRecentRoom.add(ListRoomModel("" + body.idRoom, "" + body.namaRoom, "" + body.lastChat, "" + body.jumlahChat, ""+ body.jumlahAnggota, "" + body.quotaRoom))
                            }

                            listRoomRecentRoomLive.value = listRoomRecentRoom
                        } else {
                            view?.showToastRoomUser(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                        view?.hideLoadingRoomUser()
                    }
                }
            }
        })
    }

    fun onRefreshOfficial() : MutableLiveData<MutableList<ListRoomModel>> {
        return listRoomOfficialLive
    }

    fun onRefreshFavourites() : MutableLiveData<MutableList<ListRoomModel>> {
        return listRoomFavouritesLive
    }

    fun onRefreshPlayGames() : MutableLiveData<MutableList<ListRoomModel>> {
        return listRoomPlayGamesLive
    }

    fun onRefreshRecentRoom() : MutableLiveData<MutableList<ListRoomModel>> {
        return listRoomRecentRoomLive
    }

    fun onRefreshRandom() : MutableLiveData<MutableList<ListRoomModel>> {
        return listRoomRandomLive
    }

    fun onRefreshRoomChat() : MutableLiveData<MutableList<ListRoomModel>> {
        return listRoomRecentRoomLive
    }
}