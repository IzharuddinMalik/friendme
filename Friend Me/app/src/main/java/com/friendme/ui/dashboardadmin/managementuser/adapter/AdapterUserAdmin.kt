package com.friendme.ui.dashboardadmin.managementuser.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.friendme.R
import com.friendme.ui.dashboardadmin.managementuser.ChangeManagementUserActivity
import com.friendme.ui.dashboardadmin.managementuser.model.ListUserModel
import com.friendme.ui.searchfriend.model.ListSearchFriendModel
import kotlinx.android.synthetic.main.inflater_list_cari_friend.view.*
import kotlinx.android.synthetic.main.inflater_list_useradmin.view.*

class AdapterUserAdmin(private val itemList : MutableList<ListUserModel>) : RecyclerView.Adapter<AdapterUserAdmin.UserViewHolder>() {

    private lateinit var context: Context

    class UserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_useradmin, parent, false)
        val ksv = UserViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.tvListNamaUserAdmin.text = itemList[position].username
        Glide.with(context).load(itemList[position].fotoProfile).into(holder.itemView.ivAvatarUserAdmin)
        holder.itemView.tvListLevelUserAdmin.text = itemList[position].levelManagement

        holder.itemView.setOnClickListener {
            var intent = Intent(context, ChangeManagementUserActivity::class.java)
            intent.putExtra("username", itemList[position].username)
            intent.putExtra("idUser", itemList[position].idUser)
            intent.putExtra("idLevel", itemList[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}