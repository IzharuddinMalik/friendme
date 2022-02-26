package com.friendme.ui.privatemessage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.friendme.R
import com.friendme.ui.detailroom.adapter.AdapterListChatRoom
import com.friendme.ui.privatemessage.PrivateMessageActivity
import com.friendme.ui.privatemessage.model.PrivateMessageModel
import com.friendme.utils.EmojiconUtils
import kotlinx.android.synthetic.main.inflater_list_chat.view.*

class AdapterPrivateMessage(val itemList : MutableList<PrivateMessageModel>) : RecyclerView.Adapter<AdapterPrivateMessage.PMViewHolder>() {

    private lateinit var context: Context
    class PMViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PMViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_chat, parent, false)
        val ksv = PMViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: PMViewHolder, position: Int) {
        var decodeEmoji = EmojiconUtils()

        holder.itemView.tvListNamaUserChatPM.text = itemList[position].namaPengirim + " : "
        holder.itemView.tvListMessageChatPM.text = decodeEmoji.unescapeJava(itemList[position].message!!)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}