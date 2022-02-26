package com.friendme.ui.searchfriend.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.friendme.R
import com.friendme.ui.detailroom.adapter.AdapterListChatRoom
import com.friendme.ui.profile.ProfileTemanActivity
import com.friendme.ui.searchfriend.model.ListSearchFriendModel
import kotlinx.android.synthetic.main.inflater_list_cari_friend.view.*

class AdapterSearchFriend(private val itemList : MutableList<ListSearchFriendModel>) : RecyclerView.Adapter<AdapterSearchFriend.SearchFriendViewHolder>() {

    lateinit var context: Context

    class SearchFriendViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFriendViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_cari_friend, parent, false)
        val ksv = SearchFriendViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: SearchFriendViewHolder, position: Int) {
        holder.itemView.tvListNamaCariFriend.text = itemList[position].username
        Glide.with(context).load("https://idfriendme.com/imageapps/" + itemList[position].fotoProfile).into(holder.itemView.ivAvatarCariFriend)

        holder.itemView.setOnClickListener {
            var intent = Intent(context, ProfileTemanActivity::class.java)
            intent.putExtra("idFriend", itemList[position].idUser)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}