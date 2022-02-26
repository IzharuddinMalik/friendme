package com.friendme.presenter

import androidx.lifecycle.MutableLiveData
import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedListResponse
import com.friendme.api.WrappedResponse
import com.friendme.contract.BalanceContract
import com.friendme.ui.balance.model.BalanceUserModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BalancePresenter(v : BalanceContract.balanceView) {

    private var apiService = ApiClient.APIService()
    private var view : BalanceContract.balanceView? = v
    private var listBalance = mutableListOf<BalanceUserModel>()
    private var listBalanceLive : MutableLiveData<MutableList<BalanceUserModel>> = MutableLiveData()
    private var addBalance : MutableLiveData<Boolean> = MutableLiveData()

    fun getBalanceUser(idUser : String) {
        val request = apiService.getBalanceUser(idUser)
        view?.showLoadingBalance()
        request.enqueue(object : Callback<WrappedListResponse<BalanceUserModel>> {
            override fun onFailure(
                call: Call<WrappedListResponse<BalanceUserModel>>,
                t: Throwable
            ) {
                view?.hideLoadingBalance()
                view?.showToastBalance(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<BalanceUserModel>>,
                response: Response<WrappedListResponse<BalanceUserModel>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingBalance()

                        val body = response.body()
                        listBalance.clear()
                        if (body!!.success.equals("1")) {
                            for (i in 0 until body.data.size) {
                                val balance = body.data[i]

                                listBalance.add(BalanceUserModel("" + balance.titleBalance, "" + balance.balance, "" + balance.dateBalance))
                            }

                            listBalanceLive.value = listBalance
                        } else {
                            view?.showToastBalance(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun produceBalance(idAdmin : String, balance : String) {
        val request = apiService.produceBalance(idAdmin, balance)
        view?.showLoadingBalance()
        request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
            override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
                view?.hideLoadingBalance()
                view?.showToastBalance(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedResponse<AnotherResponse>>,
                response: Response<WrappedResponse<AnotherResponse>>
            ) {
                if (response.isSuccessful) {
                    try {
                        view?.hideLoadingBalance()

                        val body = response.body()

                        if (body!!.success.equals("1")) {
                            addBalance.value = true
                        } else {
                            addBalance.value = false
                            view?.showToastBalance(body.message)
                        }
                    } catch (e : JSONException) {
                        e.printStackTrace()
                        view?.hideLoadingBalance()
                    }
                }
            }
        })
    }

    fun onRefreshBalance() : MutableLiveData<MutableList<BalanceUserModel>> {
        return listBalanceLive
    }

    fun onAddBalance() : MutableLiveData<Boolean> {
        return addBalance
    }
}