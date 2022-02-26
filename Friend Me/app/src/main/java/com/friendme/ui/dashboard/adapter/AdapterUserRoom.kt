package com.friendme.ui.dashboard.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.friendme.R
import com.friendme.contract.DetailRoomContract
import com.friendme.contract.JoinRoomContract
import com.friendme.presenter.DetailRoomPresenter
import com.friendme.presenter.JoinRoomPresenter
import com.friendme.ui.dashboard.model.ListRoomModel
import com.friendme.ui.detailroom.NewDetailRoomActivity
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.inflater_room.view.tvListNamaRoom
import kotlinx.android.synthetic.main.inflater_user_room.view.*

class AdapterUserRoom(private val itemCell : MutableList<ListRoomModel>) : RecyclerView.Adapter<AdapterUserRoom.UserRoomViewHolder>(), JoinRoomContract.joinRoomView {

    private lateinit var context: Context
    private var presenterJoinRoom : JoinRoomPresenter? = null
    private var strNamaRoom : String? = ""
    private var customToast = CustomToast()

    class UserRoomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRoomViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_user_room, parent, false)
        val ksv = UserRoomViewHolder(view)
        context = parent.context
        presenterJoinRoom = JoinRoomPresenter(this)
        return ksv
    }

    override fun onBindViewHolder(holder: UserRoomViewHolder, position: Int) {
        var sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")

        holder.itemView.tvListNamaRoom.setText(itemCell[position].namaRoom)
        holder.itemView.tvListJumlahAnggotaRoom.text = "(" + itemCell[position].jumlahAnggota + "/" + itemCell[position].quotaRoom + ")"

        holder.itemView.setOnClickListener {
            joinRoom(idUser!!, itemCell[position].idRoom!!, itemCell[position].namaRoom!!)
        }
    }

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun showLoadingJoinRoom() {

    }

    override fun hideLoadingJoinRoom() {

    }

    override fun showToastJoinRoom(message: String) {
        customToast.customToast(context, message)
    }

    fun joinRoom(idUser : String, idRoom : String, namaRoom : String) {
        presenterJoinRoom?.sendJoinRoom(idUser!!, idRoom)

        strNamaRoom = namaRoom
    }

    override fun successJoinRoom(namaRom : String) {
        var intent = Intent(context, NewDetailRoomActivity::class.java)
        intent.putExtra("namaRoom", namaRom)
        context.startActivity(intent)
    }

    override fun openRoom() {
        var intent = Intent(context, NewDetailRoomActivity::class.java)
        intent.putExtra("namaRoom", strNamaRoom)
        context.startActivity(intent)
    }
}