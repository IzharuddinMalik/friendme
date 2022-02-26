package com.friendme.ui.balance.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.friendme.R
import com.friendme.ui.balance.model.PeopleModel
import com.friendme.ui.privatemessage.PrivateMessageActivity
import kotlinx.android.synthetic.main.inflater_list_people.view.*

class AdapterFindMerchant(private val itemList : MutableList<PeopleModel>) : RecyclerView.Adapter<AdapterFindMerchant.MerchantViewHolder>() {

    private lateinit var context: Context

    class MerchantViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchantViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_people, parent, false)
        val ksv = MerchantViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: MerchantViewHolder, position: Int) {
        holder.itemView.tvUsernameListPeople.text = itemList[position].usernamePeople
        holder.itemView.tvUserLevelListPeople.text = itemList[position].userLevelPeople + ","
        if (itemList[position].userSex == "p") {
            holder.itemView.tvSexListPeople.text = "Perempuan,"
        } else {
            holder.itemView.tvSexListPeople.text = "Laki-Laki,"
        }
        Glide.with(context).load("https://idfriendme.com/imageapps/"+itemList[position].fotoProfilePeople).into(holder.itemView.ivFotoListPeople)

        if (itemList[position].levelManagement == "1") {
            holder.itemView.tvUsernameListPeople.setTextColor(context.resources.getColor(R.color.mainBlueColor))
            holder.itemView.cvStatusManagementListPeople.setCardBackgroundColor(context.resources.getColor(R.color.mainBlueColor))
            holder.itemView.tvStatusManagementListPeople.setText("A")
        } else if (itemList[position].levelManagement == "2") {
            holder.itemView.tvUsernameListPeople.setTextColor(context.resources.getColor(R.color.purple_500))
            holder.itemView.cvStatusManagementListPeople.setCardBackgroundColor(context.resources.getColor(R.color.purple_500))
            holder.itemView.tvStatusManagementListPeople.setText("M")
        } else if (itemList[position].levelManagement == "3") {
            holder.itemView.tvUsernameListPeople.setTextColor(context.resources.getColor(R.color.redLight))
            holder.itemView.cvStatusManagementListPeople.setCardBackgroundColor(context.resources.getColor(R.color.redLight))
            holder.itemView.tvStatusManagementListPeople.setText("MT")
        } else if (itemList[position].levelManagement == "4") {
            holder.itemView.tvUsernameListPeople.setTextColor(context.resources.getColor(R.color.mainOrangeColor))
            holder.itemView.cvStatusManagementListPeople.setCardBackgroundColor(context.resources.getColor(R.color.mainOrangeColor))
            holder.itemView.tvStatusManagementListPeople.setText("M")
        }

        if (itemList[position].statusOnlinePeople == "1") {
            holder.itemView.cvStatusOnlineListPeople.setCardBackgroundColor(context.resources.getColor(R.color.greenLight))
        } else if (itemList[position].statusOnlinePeople == "2") {
            holder.itemView.cvStatusOnlineListPeople.setCardBackgroundColor(context.resources.getColor(R.color.yellowLight))
        } else if (itemList[position].statusOnlinePeople == "3") {
            holder.itemView.cvStatusOnlineListPeople.setCardBackgroundColor(context.resources.getColor(R.color.redLight))
        } else if (itemList[position].statusOnlinePeople == "4") {
            holder.itemView.cvStatusOnlineListPeople.setCardBackgroundColor(context.resources.getColor(R.color.white))
        }

        holder.itemView.setOnClickListener {
            var intent = Intent(context, PrivateMessageActivity::class.java)
            intent.putExtra("idUserTo", itemList[position].idUserPeople)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}