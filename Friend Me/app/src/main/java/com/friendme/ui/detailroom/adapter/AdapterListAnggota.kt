package com.friendme.ui.detailroom.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.friendme.R
import com.friendme.contract.DetailRoomContract
import com.friendme.presenter.DetailRoomPresenter
import com.friendme.ui.balance.TransferCreditActivity
import com.friendme.ui.detailroom.model.ListAnggotaModel
import com.friendme.ui.gift.GiftActivity
import com.friendme.ui.privatemessage.PrivateMessageActivity
import com.friendme.ui.profile.ProfileTemanActivity
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.inflater_list_anggota.view.*

class AdapterListAnggota(private val itemList : MutableList<ListAnggotaModel>, var idRoom : String) : RecyclerView.Adapter<AdapterListAnggota.AnggotaViewHolder>(), DetailRoomContract.detailRoomView {

    private lateinit var context: Context
    private var dialogPreview : Dialog? = null
    private var presenterDetailRoom : DetailRoomPresenter? = null
    private var customToast = CustomToast()

    class AnggotaViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnggotaViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_anggota, parent, false)
        val ksv = AnggotaViewHolder(view)
        context = parent.context
        presenterDetailRoom = DetailRoomPresenter(this)
        return ksv
    }

    override fun onBindViewHolder(holder: AnggotaViewHolder, position: Int) {
        holder.itemView.tvListNamaAnggota.text = itemList[position].username
        holder.itemView.setOnClickListener {
            optionRoom(itemList[position].idUser!!)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun optionRoom(idUserTo : String){
        dialogPreview = Dialog(context)
        dialogPreview!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogPreview!!.setContentView(R.layout.dialog_option_anggota)
        dialogPreview!!.setCancelable(true)
        val window = dialogPreview!!.window
        window!!.setGravity(Gravity.CENTER)
        window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        window!!.setBackgroundDrawable(context.resources.getDrawable(R.drawable.background_transparent))

        val privateMessage = dialogPreview!!.findViewById<CardView>(R.id.cvPrivateMessageOptionAnggota)
        val viewProfile = dialogPreview!!.findViewById<CardView>(R.id.cvViewProfileOptionAnggota)
        val transfer = dialogPreview!!.findViewById<CardView>(R.id.cvTransferOptionAnggota)
        val kickUser = dialogPreview!!.findViewById<CardView>(R.id.cvKickOptionAnggota)
        val insert = dialogPreview!!.findViewById<CardView>(R.id.cvInsertOptionAnggota)

        privateMessage.setOnClickListener {
            // Go to Private Message
            var intent = Intent(context, PrivateMessageActivity::class.java)
            intent.putExtra("idUserTo", idUserTo)
            context.startActivity(intent)
        }

        viewProfile.setOnClickListener {
            var intent = Intent(context, ProfileTemanActivity::class.java)
            intent.putExtra("idFriend", idUserTo)
            context.startActivity(intent)
        }

        transfer.setOnClickListener {
            context.startActivity(Intent(context, TransferCreditActivity::class.java))
        }

        kickUser.setOnClickListener {
            Log.d("IDROOMKICK", " === " + idRoom)
            presenterDetailRoom?.kickRoom(idUserTo, idRoom)
        }

        insert.setOnClickListener {
            var sharedPreferences = context.getSharedPreferences("gift", Context.MODE_PRIVATE)
            var edit = sharedPreferences.edit()
            edit.putString("idUserTo", idUserTo)
            edit.commit()
            context.startActivity(Intent(context, GiftActivity::class.java))
        }

        dialogPreview!!.show()
    }

    override fun showToastDetailRoom(message: String) {
        customToast.customToast(context, message)
    }

    override fun getMessageLeave(message: String) {

    }

}