package com.friendme.ui.dashboard.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.friendme.R
import com.friendme.ui.dashboard.model.ListRoomModel
import com.friendme.ui.detailroom.NewDetailRoomActivity
import com.friendme.utils.EmojiconUtils
import kotlinx.android.synthetic.main.inflater_user_chat_room.view.*

class AdapterUserChatRoom(private val itemCell : MutableList<ListRoomModel>) : RecyclerView.Adapter<AdapterUserChatRoom.UserChatRoomViewHolder>() {

    private lateinit var context: Context

    class UserChatRoomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserChatRoomViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_user_chat_room, parent, false)
        val ksv = UserChatRoomViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: UserChatRoomViewHolder, position: Int) {
        holder.itemView.tvListNamaChatRoom.text = itemCell[position].namaRoom

        var decodeEmoji = EmojiconUtils()

        holder.itemView.tvListLastMessageChatRoom.text = decodeEmoji.unescapeJava(itemCell[position].lastChat!!)
        holder.itemView.tvListJumlahMessageChatRoom.text = itemCell[position].jumlahChat

        holder.itemView.setOnClickListener {
            var intent = Intent(context, NewDetailRoomActivity::class.java)
            intent.putExtra("namaRoom", itemCell[position].namaRoom)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemCell.size
    }
}