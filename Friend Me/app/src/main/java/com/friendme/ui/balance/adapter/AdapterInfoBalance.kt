package com.friendme.ui.balance.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.friendme.R
import com.friendme.presenter.LikeFeedPresenter
import com.friendme.ui.balance.model.BalanceUserModel
import com.friendme.ui.dashboard.adapter.AdapterFeed
import kotlinx.android.synthetic.main.inflater_list_info_balance.view.*
import java.text.SimpleDateFormat

class AdapterInfoBalance(private val itemCell : MutableList<BalanceUserModel>) : RecyclerView.Adapter<AdapterInfoBalance.BalanceViewHolder>() {

    private lateinit var context: Context
    class BalanceViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_info_balance, parent, false)
        val ksv = BalanceViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        var patternTTL = "h:m:s dd MMMM yyyy"
        var simpleDateFormat2 : SimpleDateFormat = SimpleDateFormat(patternTTL)
        var tanggalFromAPI2 = SimpleDateFormat("yyyy-MM-dd h:m:s")
        var tanggalBalance = simpleDateFormat2.format(tanggalFromAPI2.parse(itemCell[position].dateBalance))

        holder.itemView.tvListTitleInfoAccountBalance.text = itemCell[position].titleBalance
        holder.itemView.tvListDateInfoAccountBalance.text = tanggalBalance
        holder.itemView.tvJumlahBalanceInfoAccountBalance.text = itemCell[position].balance + " IDR"
    }

    override fun getItemCount(): Int {
        return itemCell.size
    }
}