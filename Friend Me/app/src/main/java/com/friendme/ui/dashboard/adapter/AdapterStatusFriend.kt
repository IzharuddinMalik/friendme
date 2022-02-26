package com.friendme.ui.dashboard.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.friendme.R
import com.friendme.ui.dashboard.model.StatusFriendModel
import com.friendme.ui.privatemessage.PrivateMessageActivity
import kotlinx.android.synthetic.main.inflater_status_friend.view.*
import java.text.SimpleDateFormat

class AdapterStatusFriend(private val itemList : MutableList<StatusFriendModel>) : RecyclerView.Adapter<AdapterStatusFriend.StatusFriendViewHolder>() {

    private lateinit var context: Context

    class StatusFriendViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusFriendViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_status_friend, parent, false)
        val ksv = StatusFriendViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: StatusFriendViewHolder, position: Int) {
        var patternTTL = "h:m:s dd MMMM yyyy"
        var simpleDateFormat2 : SimpleDateFormat = SimpleDateFormat(patternTTL)
        var tanggalFromAPI2 = SimpleDateFormat("yyyy-MM-dd h:m:s")
        var tanggalLastSeen = simpleDateFormat2.format(tanggalFromAPI2.parse(itemList[position].logouton))

        holder.itemView.tvListNamaUserStatusFriend.text = itemList[position].username
        holder.itemView.tvListMessageStatusFriend.text = itemList[position].statusMessage
        holder.itemView.tvListLastSeenStatusFriend.text = tanggalLastSeen

        Glide.with(context).load("https://idfriendme.com/imageapps/"+itemList[position].fotoProfile).into(holder.itemView.civListProfileStatusFriend)

        holder.itemView.setOnClickListener {
            // Go to Private Message
            var intent = Intent(context, PrivateMessageActivity::class.java)
            intent.putExtra("namaPenerima", itemList[position].username)
            intent.putExtra("fotoPenerima", itemList[position].fotoProfile)
            context.startActivity(intent)
        }

        if (itemList[position].statusOnline.equals("1")) {
            holder.itemView.cvListIndicatorStatusFriend.setCardBackgroundColor(context.resources.getColor(R.color.greenLight))
        } else if (itemList[position].statusOnline.equals("2")) {
            holder.itemView.cvListIndicatorStatusFriend.setCardBackgroundColor(context.resources.getColor(R.color.yellowLight))
        } else if (itemList[position].statusOnline.equals("3")) {
            holder.itemView.cvListIndicatorStatusFriend.setCardBackgroundColor(context.resources.getColor(R.color.redLight))
        } else if (itemList[position].statusOnline.equals("4")) {
            holder.itemView.cvListIndicatorStatusFriend.setCardBackgroundColor(context.resources.getColor(R.color.white))
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}