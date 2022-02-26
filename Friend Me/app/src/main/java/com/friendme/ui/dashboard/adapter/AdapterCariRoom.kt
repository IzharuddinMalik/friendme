package com.friendme.ui.dashboard.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.friendme.R
import com.friendme.contract.JoinRoomContract
import com.friendme.presenter.JoinRoomPresenter
import com.friendme.ui.dashboard.model.ListCariRoomModel
import com.friendme.ui.detailroom.NewDetailRoomActivity
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.inflater_room.view.*

class AdapterCariRoom(private val itemCell : MutableList<ListCariRoomModel>) : RecyclerView.Adapter<AdapterCariRoom.CariRoomViewHolder>(), JoinRoomContract.joinRoomView {

    private lateinit var context: Context
    private var presenterJoin : JoinRoomContract.joinRoomPresenter? = null
    private var progressDialog = CustomProgressDialog()
    private var customToast = CustomToast()
    var pos : Int? = 0
    var idUser : String? = ""
    var strNamaRoom : String? = ""

    class CariRoomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CariRoomViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_room, parent, false)
        val ksv = CariRoomViewHolder(view)
        context = parent.context
        presenterJoin = JoinRoomPresenter(this)

        var sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        idUser = sharedPreferences.getString("idUser", "")
        return ksv
    }

    override fun onBindViewHolder(holder: CariRoomViewHolder, position: Int) {
        holder.itemView.tvListNamaRoom.setText(itemCell[position].namaRoom)

        pos = position

        holder.itemView.setOnClickListener {
            joinRoom(idUser!!, itemCell[position].idRoom!!, itemCell[position].namaRoom!!)
        }
    }

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun showLoadingJoinRoom() {
        progressDialog.show(context, "Harap menunggu...")
    }

    override fun hideLoadingJoinRoom() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastJoinRoom(message: String) {
        customToast.customToast(context, message)
    }

    fun joinRoom(idUser : String, idRoom : String, namaRoom: String){
        presenterJoin?.sendJoinRoom(idUser!!, idRoom)
        strNamaRoom = namaRoom
    }

    override fun successJoinRoom(namaRoom : String) {
        var intent = Intent(context, NewDetailRoomActivity::class.java)
        intent.putExtra("idRoom", itemCell[pos!!].idRoom)
        intent.putExtra("namaRoom", namaRoom)
        Log.i("IDROOM", " " + itemCell[pos!!].idRoom)
        context.startActivity(intent)
    }

    override fun openRoom() {
        var intent = Intent(context, NewDetailRoomActivity::class.java)
        intent.putExtra("namaRoom", strNamaRoom)
        context.startActivity(intent)
    }
}