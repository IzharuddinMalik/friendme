package com.friendme.presenter

import com.friendme.api.AnotherResponse
import com.friendme.api.ApiClient
import com.friendme.api.WrappedResponse
import com.friendme.contract.EditProfileContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfilePresenter(v : EditProfileContract.editProfileView) : EditProfileContract.editProfilePresenter {

    private var apiService = ApiClient.APIService()
    private var view : EditProfileContract.editProfileView? = v

    override fun sendEditProfile(
        idUser: String,
        namaUser: String,
        sex: String,
        dateOfBirth: String,
        aboutProfile: String,
        fotoProfile: String
    ) {
       val request = apiService.updateProfile(idUser, namaUser, sex, dateOfBirth, aboutProfile, fotoProfile)
       view?.showLoadingEditProfile()
       request.enqueue(object : Callback<WrappedResponse<AnotherResponse>> {
           override fun onFailure(call: Call<WrappedResponse<AnotherResponse>>, t: Throwable) {
               view?.hideLoadingEditProfile()
               view?.showToastEditProfile(t.message!!)
           }

           override fun onResponse(
               call: Call<WrappedResponse<AnotherResponse>>,
               response: Response<WrappedResponse<AnotherResponse>>
           ) {
               if (response.isSuccessful) {
                   try {
                       view?.hideLoadingEditProfile()

                       val body = response.body()

                       if (body!!.success.equals("1")) {
                           view?.showToastEditProfile(body.message)
                           view?.successEditProfile()
                       } else {
                           view?.showToastEditProfile(body.message)
                       }
                   } catch (e : JSONException) {
                       e.printStackTrace()
                   }
               }
           }
       })
    }
}