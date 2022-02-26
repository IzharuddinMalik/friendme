package com.friendme.api

import com.friendme.ui.balance.model.BalanceUserModel
import com.friendme.ui.balance.model.DataPeopleModel
import com.friendme.ui.dashboard.model.*
import com.friendme.ui.dashboardadmin.managementuser.model.LevelUserModel
import com.friendme.ui.dashboardadmin.managementuser.model.ListUserModel
import com.friendme.ui.detailroom.model.*
import com.friendme.ui.gift.model.GiftModel
import com.friendme.ui.gift.model.UserGiftModel
import com.friendme.ui.login.LoginModel
import com.friendme.ui.privatemessage.model.PrivateMessageRoomModel
import com.friendme.ui.profile.model.ProfileModel
import com.friendme.ui.searchfriend.model.ListSearchFriendModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {

    @FormUrlEncoded
    @POST("user/signupakun")
    fun signupAkun(@Field("nama") nama : String, @Field("username") username : String, @Field("email") email: String, @Field("sex") sex : String,
                   @Field("password") passwordAkun : String, @Field("token") token : String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("user/loginakun")
    fun loginAkun(@Field("username") username: String, @Field("password") passwordAkun: String) : Call<WrappedResponse<LoginModel>>

    @FormUrlEncoded
    @POST("user/updateprofile")
    fun updateProfile(@Field("iduser") idUser : String, @Field("nama") nama: String, @Field("sex") sex: String, @Field("datebirth") dateBirth : String,
    @Field("aboutprofile") aboutProfile : String, @Field("fotoprofile") fotoProfile : String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("user/getprofile")
    fun getProfileAkun(@Field("iduser") idUser: String) : Call<WrappedResponse<ProfileModel>>

    @FormUrlEncoded
    @POST("user/verifikasiemail")
    fun verifikasiEmail(@Field("email") email: String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("user/updatestatusmessage")
    fun updateStatusMessage(@Field("iduser") idUser: String, @Field("statusmessage") statusMessage : String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("user/updatestatusonline")
    fun updateStatusOnline(@Field("iduser") idUser: String, @Field("statusonline") statusOnline : String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("feed/createfeed")
    fun createFeed(@Field("iduser") idUser: String, @Field("message") message : String, @Field("imagefeed") imageFeed : String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("feed/listfeed")
    fun listFeed(@Field("iduser") idUser: String) : Call<WrappedListResponse<ListFeedModel>>

    @FormUrlEncoded
    @POST("room/createroom")
    fun createRoom(@Field("iduser") idUser: String, @Field("namaroom") namaRoom : String, @Field("descriptionroom") descRoom : String, @Field("idcategoryroom") idCategoryRoom : String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("room/listroom")
    fun listRoomUser(@Field("iduser") idUser: String) : Call<WrappedResponse<DataRoomModel>>

    @FormUrlEncoded
    @POST("room/joinroom")
    fun joinRoom(@Field("iduser") idUser: String, @Field("idroom") idRoom : String) : Call<WrappedResponse<JoinRoomModel>>

    @FormUrlEncoded
    @POST("room/cariroom")
    fun cariRoom(@Field("iduser") idUser: String, @Field("namaroom") namaRoom: String) : Call<WrappedListResponse<ListCariRoomModel>>

    @FormUrlEncoded
    @POST("room/detailroom")
    fun detailRoom(@Field("iduser") idUser: String, @Field("idroom") idRoom: String) : Call<WrappedResponse<DetailRoomModel>>

    @FormUrlEncoded
    @POST("room/sendchatroom")
    fun sendChatRoom(@Field("iduser") idUser: String, @Field("idroom") idRoom: String, @Field("message") message: String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("feed/likefeed")
    fun likeFeed(@Field("iduser") idUser: String, @Field("idfeed") idFeed : String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("user/searchfriend")
    fun searchFriend(@Field("iduser") idUser: String, @Field("username") username : String) : Call<WrappedListResponse<ListSearchFriendModel>>

    @FormUrlEncoded
    @POST("user/addfriend")
    fun addFriend(@Field("iduser") idUser: String, @Field("idfriend") idFriend : String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("user/statusfriend")
    fun statusFriend(@Field("iduser") idUser: String) : Call<WrappedListResponse<StatusFriendModel>>

    @FormUrlEncoded
    @POST("balance/getbalanceuser")
    fun getBalanceUser(@Field("iduser") idUser: String) : Call<WrappedListResponse<BalanceUserModel>>

    @FormUrlEncoded
    @POST("balance/transferbalance")
    fun transferBalance(@Field("iduser") idUser: String, @Field("usernamedituju") usernameDituju : String, @Field("balance") balance : String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("people/getlistpeople")
    fun getListPeople(@Field("iduser") idUser: String) : Call<WrappedResponse<DataPeopleModel>>

    @FormUrlEncoded
    @POST("balance/producebalance")
    fun produceBalance(@Field("idadmin") idUser: String, @Field("balance") balance : String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("privatemessage/createpm")
    fun createSendPM(@Field("iduserfrom") idUser: String, @Field("iduserto") idUserTo : String, @Field("message") message : String, @Field("tokenmessage") tokenMessage : String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("privatemessage/listpm")
    fun listChatPM(@Field("iduser") idUser: String, @Field("iduserfrom") idUserFrom : String, @Field("tokenmessage") tokenMessage: String) : Call<WrappedResponse<PrivateMessageRoomModel>>

    @FormUrlEncoded
    @POST("room/listcategoryroom")
    fun listCatRoom(@Field("iduser") idUser: String) : Call<WrappedListResponse<CategoryRoomModel>>

    @FormUrlEncoded
    @POST("user/logoutakun")
    fun logoutAkun(@Field("iduser") idUser: String) : Call<WrappedResponse<LogoutMessageModel>>

    @FormUrlEncoded
    @POST("room/newlistdetailroom")
    fun newListDetailRoom(@Field("iduser") idUser: String) : Call<WrappedListResponse<NewDetailRoomModel>>

    @FormUrlEncoded
    @POST("room/listanggotaroom")
    fun listAnggotaRoom(@Field("iduser") idUser: String, @Field("idroom") idRoom: String) : Call<WrappedListResponse<ListAnggotaModel>>

    @FormUrlEncoded
    @POST("room/kickroom")
    fun kickRoom(@Field("iduser") idUser: String, @Field("idroom") idRoom: String) : Call<WrappedResponse<LeaveRoomModel>>

    @FormUrlEncoded
    @POST("room/listroomchatuser")
    fun listRoomChatUser(@Field("iduser") idUser: String) : Call<WrappedListResponse<ListRoomModel>>

    @FormUrlEncoded
    @POST("gift/getgift")
    fun getGift(@Field("iduser") idUser: String) : Call<WrappedListResponse<GiftModel>>

    @FormUrlEncoded
    @POST("gift/sendgift")
    fun sendGift(@Field("iduser") idUser: String, @Field("iduserto") idUserTo: String, @Field("idgift") idGift : String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("gift/getgiftuser")
    fun getGiftUser(@Field("iduser") idUser: String) : Call<WrappedListResponse<UserGiftModel>>

    @FormUrlEncoded
    @POST("gift/giftsentuser")
    fun giftSentUser(@Field("iduser") idUser: String) : Call<WrappedListResponse<UserGiftModel>>

    @FormUrlEncoded
    @POST("user/lupapassword")
    fun lupaPassword(@Field("email") email : String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("user/ressetpassword")
    fun ressetPass(@Field("email") email: String, @Field("password") passwordAkun: String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("user/getleveluser")
    fun listLevelUser(@Field("iduser") idUser: String) : Call<WrappedListResponse<LevelUserModel>>

    @FormUrlEncoded
    @POST("user/listuser")
    fun listUserAdmin(@Field("iduser") idUser: String) : Call<WrappedListResponse<ListUserModel>>

    @FormUrlEncoded
    @POST("user/setusermanagement")
    fun setUserManagement(@Field("idadmin") idAdmin : String, @Field("iduser") idUser: String, @Field("levelmanagement") levelManagement : String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("gift/addgift")
    fun addGift(@Field("namagift") namaGift : String, @Field("imagegift") imageGift : String, @Field("idrgift") idrGift : String) : Call<WrappedResponse<AnotherResponse>>

    @FormUrlEncoded
    @POST("gift/editgift")
    fun editGift(@Field("idgift") idGift : String, @Field("namagift") namaGift : String, @Field("idrgift") idrGift: String, @Field("imagegift") imageGift : String) : Call<WrappedResponse<AnotherResponse>>

}