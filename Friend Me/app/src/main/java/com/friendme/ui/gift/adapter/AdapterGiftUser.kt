package com.friendme.ui.gift.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.friendme.R
import com.friendme.ui.gift.model.UserGiftModel
import kotlinx.android.synthetic.main.inflater_user_gift.view.*

class AdapterGiftUser(private val itemList : MutableList<UserGiftModel>) : RecyclerView.Adapter<AdapterGiftUser.GiftUserViewHolder>() {

    private lateinit var context: Context

    class GiftUserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiftUserViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_user_gift, parent, false)
        val ksv = GiftUserViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: GiftUserViewHolder, position: Int) {
        holder.itemView.tvGiftFromUser.text = itemList[position].fromUserGift
        holder.itemView.tvGiftNamaGift.text = itemList[position].namaGiftUser

        Glide.with(context).load("https://idfriendme.com/imageapps/"+itemList[position].imageGiftUser).into(holder.itemView.ivGiftFromUser)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}