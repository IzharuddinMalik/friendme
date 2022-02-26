package com.friendme.ui.gift.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.friendme.R
import com.friendme.contract.GiftContract
import com.friendme.presenter.GiftPresenter
import com.friendme.ui.detailroom.NewDetailRoomActivity
import com.friendme.ui.gift.GiftActivity
import com.friendme.ui.gift.model.GiftModel
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.inflater_list_gift.view.*

class AdapterGift(private val itemList : MutableList<GiftModel>) : RecyclerView.Adapter<AdapterGift.GiftViewHolder>() {

    private lateinit var context: Context

    class GiftViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiftViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_gift, parent, false)
        val ksv = GiftViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: GiftViewHolder, position: Int) {

        var sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")

        var sharedPreferences2 = context.getSharedPreferences("gift", Context.MODE_PRIVATE)
        var idUserTo = sharedPreferences2.getString("idUserTo", "")

        holder.itemView.tvListNamaGift.text = itemList[position].namaGift
        holder.itemView.tvListIDRGift.text = "IDR " + itemList[position].idrGift

        Glide.with(context).load("https://idfriendme.com/imageapps/"+itemList[position].imageGift).into(holder.itemView.ivListFotoGift)

        holder.itemView.cvSendGift.setOnClickListener {
            (context as GiftActivity).sendGift(idUser!!, idUserTo!!, itemList[position].idGift!!)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}