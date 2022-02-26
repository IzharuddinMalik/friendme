package com.friendme.ui.dashboardadmin.producegift.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.friendme.R
import com.friendme.ui.dashboardadmin.producegift.ProduceGiftActivity
import com.friendme.ui.gift.model.GiftModel
import kotlinx.android.synthetic.main.inflater_list_giftadmin.view.*

class AdapterGiftAdmin(private val itemList : MutableList<GiftModel>) : RecyclerView.Adapter<AdapterGiftAdmin.GiftAdminViewHolder>() {

    private lateinit var context: Context

    class GiftAdminViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiftAdminViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_giftadmin, parent, false)
        val ksv = GiftAdminViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: GiftAdminViewHolder, position: Int) {
        holder.itemView.tvListNamaGiftAdmin.text = itemList[position].namaGift
        holder.itemView.tvListIDRGiftAdmin.text = "IDR " + itemList[position].idrGift

        Glide.with(context).load("https://idfriendme.com/imageapps/"+itemList[position].imageGift).into(holder.itemView.ivListFotoGiftAdmin)

        holder.itemView.cvListEditGift.setOnClickListener {
            var intent = Intent(context, ProduceGiftActivity::class.java)
            intent.putExtra("idGift", itemList[position].idGift)
            intent.putExtra("namaGift", itemList[position].namaGift)
            intent.putExtra("idrGift", itemList[position].idrGift)
            intent.putExtra("imageGift", itemList[position].imageGift)
            intent.putExtra("from", "edit")
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}