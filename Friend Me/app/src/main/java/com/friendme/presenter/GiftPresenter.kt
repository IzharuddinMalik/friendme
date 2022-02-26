package com.friendme.presenter

import androidx.lifecycle.MutableLiveData
import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedListResponse
import com.friendme.api.WrappedResponse
import com.friendme.contract.GiftContract
import com.friendme.ui.gift.model.GiftModel
import com.friendme.ui.gift.model.UserGiftModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GiftPresenter(v : GiftContract.giftContractView) {

    private var apiService = ApiClient.APIService()
    private var view : GiftContract.giftContractView? = v
    private var listGift = mutableListOf<GiftModel>()
    private var listGiftLive : MutableLiveData<MutableList<GiftModel>> = MutableLiveData()
    private var statusSendGift : MutableLiveData<Boolean> = MutableLiveData()
    private var listUserGift = mutableListOf<UserGiftModel>()
    private var listUserGiftLive : MutableLiveData<MutableList<UserGiftModel>> = MutableLiveData()
    private var listUserSentGift = mutableListOf<UserGiftModel>()
    private var listUserSentGiftLive : MutableLiveData<MutableList<UserGiftModel>> = MutableLiveData()
    private var statusAddGift : MutableLiveData<Boolean> = MutableLiveData()
    private var statusEditGift : MutableLiveData<Boolean> = MutableLiveData()

    fun getListGift(idUser : String) {
        val request = apiService.getGift(idUser)
        view?.showLoadingGift()
        request.enqueue(object : Callback<WrappedListResponse<GiftModel>> {
            override fun onFailure(call: Call<WrappedListResponse<GiftModel>>, t: Throwable) {
                view?.hideLoadingGift()
                view?.showToastGift(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<GiftModel>>,
                response: Response<WrappedListResponse<GiftModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingGift()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            for (i in 0 until body.data.size) {
                                val gift = body.data[i]

                                listGift.add(GiftModel("" + gift.idGift, "" + gift.namaGift, "" + gift.imageGift, "" + gift.idrGift))
                            }

                            listGiftLive.value = listGift
                        } else {
                            view?.showToastGift(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                        view?.hideLoadingGift()
                    }
                }
            }
        })
    }

    fun sendGift(idUser: String, idUserTo : String, idGift : String) {
        val request = apiService.sendGift(idUser, idUserTo, idGift)
        view?.showLoadingGift()
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.hideLoadingGift()
                view?.showToastGift(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingGift()

                        val body = response.body()
                        if (body!!.success.equals("1")) {
                            statusSendGift.value = true
                        } else {
                            statusSendGift.value = false
                            view?.showToastGift(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                        view?.hideLoadingGift()
                    }
                }
            }
        })
    }

    fun listUserGift(idUser: String) {
        val request = apiService.getGiftUser(idUser)
        view?.showLoadingGift()
        request.enqueue(object : Callback<WrappedListResponse<UserGiftModel>> {
            override fun onFailure(call: Call<WrappedListResponse<UserGiftModel>>, t: Throwable) {
                view?.hideLoadingGift()
                view?.showToastGift(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<UserGiftModel>>,
                response: Response<WrappedListResponse<UserGiftModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingGift()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            for (i in 0 until body.data.size) {
                                val gift = body.data[i]

                                listUserGift.add(UserGiftModel("" + gift.fromUserGift, "" + gift.namaGiftUser, "" + gift.imageGiftUser))
                            }

                            listUserGiftLive.value = listUserGift
                        } else {
                            view?.hideLoadingGift()
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun giftSentUser(idUser: String) {
        val request = apiService.giftSentUser(idUser)
        view?.showLoadingGift()
        request.enqueue(object : Callback<WrappedListResponse<UserGiftModel>> {
            override fun onFailure(call: Call<WrappedListResponse<UserGiftModel>>, t: Throwable) {
                view?.hideLoadingGift()
                view?.showToastGift(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<UserGiftModel>>,
                response: Response<WrappedListResponse<UserGiftModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingGift()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            for (i in 0 until body.data.size) {
                                val gift = body.data[i]

                                listUserSentGift.add(UserGiftModel("" + gift.fromUserGift, "" + gift.namaGiftUser, "" + gift.imageGiftUser))
                            }

                            listUserSentGiftLive.value = listUserSentGift
                        } else {
                            view?.hideLoadingGift()
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                        view?.hideLoadingGift()
                    }
                }
            }
        })
    }

    fun addGift(namaGift : String, imageGift : String, idrGift : String) {
        val request = apiService.addGift(namaGift, imageGift, idrGift)
        view?.showLoadingGift()
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.hideLoadingGift()
                view?.showToastGift(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingGift()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            statusAddGift.value = true
                        } else {
                            statusAddGift.value = false
                            view?.showToastGift(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                        view?.hideLoadingGift()
                    }
                }
            }
        })
    }

    fun editGift(idGift: String, namaGift: String, idrGift: String, imageGift: String) {
        val request = apiService.editGift(idGift, namaGift, idrGift, imageGift)
        view?.showLoadingGift()
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.hideLoadingGift()
                view?.showToastGift(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingGift()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            statusEditGift.value = true
                        } else {
                            statusEditGift.value = false
                            view?.showToastGift(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                        view?.hideLoadingGift()
                    }
                }
            }
        })
    }

    fun onRefreshGetGift() : MutableLiveData<MutableList<GiftModel>> {
        return listGiftLive
    }

    fun onRefreshSendGift() : MutableLiveData<Boolean> {
        return statusSendGift
    }

    fun onRefreshListGift() : MutableLiveData<MutableList<UserGiftModel>> {
        return listUserGiftLive
    }

    fun onRefreshListGiftSentUser() : MutableLiveData<MutableList<UserGiftModel>> {
        return listUserSentGiftLive
    }

    fun onAddGift() : MutableLiveData<Boolean> {
        return statusAddGift
    }

    fun onEditGift() : MutableLiveData<Boolean> {
        return statusEditGift
    }
}