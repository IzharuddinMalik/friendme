package com.friendme.ui.detailroom.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.friendme.R
import com.friendme.ui.dashboard.adapter.AdapterFeed
import com.friendme.ui.detailroom.model.ListChatRoomModel
import com.friendme.utils.EmojiconUtils
import kotlinx.android.synthetic.main.inflater_chatroom.view.*
import java.text.SimpleDateFormat
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan

class AdapterListChatRoom(private var itemCell : MutableList<ListChatRoomModel>) : RecyclerView.Adapter<AdapterListChatRoom.ChatRoomViewHolder>() {

    private lateinit var context: Context

    class ChatRoomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_chatroom, parent, false)
        val ksv = ChatRoomViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        val dfJoin = SimpleDateFormat("yyyy-MM-dd h:m:s")
        var strJoinDate = dfJoin.parse(itemCell[position].joinDate)

        val dfLeave = SimpleDateFormat("yyyy-MM-dd h:m:s")
        var strLeaveDate = dfLeave.parse(itemCell[position].leaveDate)

        var joinTimestamp = convertDateToLong(itemCell[position].joinDate!!)
        var leaveTimestamp = convertDateToLong(itemCell[position].leaveDate!!)
        Log.i("TIMESTAMP", " === " + joinTimestamp)
        Log.i("TIMESTAMP2", " === " + leaveTimestamp)
        Log.i("COMPAREDATE", " === " + strJoinDate.compareTo(strLeaveDate))

//        if (strJoinDate.compareTo(strLeaveDate) > 0) {
//
//            var decodeEmoji = EmojiconUtils()
//            val spannableString = SpannableString(itemCell[position].namaUser)
//
//            if (itemCell[position].leveManagement == "1") {
//                val mainOrange = ForegroundColorSpan(context.resources.getColor(R.color.mainOrangeColor))
//                Log.i("LENGTH", " === " + itemCell[position].namaUser!!.length)
//                spannableString.setSpan(mainOrange, 0, itemCell[position].namaUser!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            } else if (itemCell[position].leveManagement == "2") {
//                val purple700 = ForegroundColorSpan(context.resources.getColor(R.color.purple_700))
//                Log.i("LENGTH", " === " + itemCell[position].namaUser!!.length)
//                spannableString.setSpan(purple700, 0, itemCell[position].namaUser!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            } else if (itemCell[position].leveManagement == "3") {
//                val redLight = ForegroundColorSpan(context.resources.getColor(R.color.redLight))
//                Log.i("LENGTH", " === " + itemCell[position].namaUser!!.length)
//                spannableString.setSpan(redLight, 0, itemCell[position].namaUser!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            } else if (itemCell[position].leveManagement == "5") {
//                val mainBlueColor = ForegroundColorSpan(context.resources.getColor(R.color.mainBlueColor))
//                Log.i("LENGTH", " === " + itemCell[position].namaUser!!.length)
//                spannableString.setSpan(mainBlueColor, 0, itemCell[position].namaUser!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            } else if (itemCell[position].leveManagement == "6") {
//                val brown = ForegroundColorSpan(context.resources.getColor(R.color.brown))
//                Log.i("LENGTH", " === " + itemCell[position].namaUser!!.length)
//                spannableString.setSpan(brown, 0, itemCell[position].namaUser!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            } else if (itemCell[position].leveManagement == "7") {
//                holder.itemView.tvListNamaUserChatRoom.text = " : " + decodeEmoji.unescapeJava(itemCell[position].message!!)
//                holder.itemView.ivUserFriendme.visibility = View.VISIBLE
//            } else if (itemCell[position].leveManagement == "8") {
//                val yellowLight2 = ForegroundColorSpan(context.resources.getColor(R.color.yellowLight2))
//                Log.i("LENGTH", " === " + itemCell[position].namaUser!!.length)
//                spannableString.setSpan(yellowLight2, 0, itemCell[position].namaUser!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            } else if (itemCell[position].leveManagement == "9") {
//                val yellowLight = ForegroundColorSpan(context.resources.getColor(R.color.yellowLight))
//                Log.i("LENGTH", " === " + itemCell[position].namaUser!!.length)
//                spannableString.setSpan(yellowLight, 0, itemCell[position].namaUser!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            } else if (itemCell[position].leveManagement == "10") {
//                val darkMainBlue = ForegroundColorSpan(context.resources.getColor(R.color.darkMainBlueColor))
//                Log.i("LENGTH", " === " + itemCell[position].namaUser!!.length)
//                spannableString.setSpan(darkMainBlue, 0, itemCell[position].namaUser!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            }
//
//            holder.itemView.tvListNamaUserChatRoom.visibility = View.VISIBLE
//            holder.itemView.tvListNamaUserChatRoom.text =  "$spannableString : " + decodeEmoji.unescapeJava(itemCell[position].message!!)
//        } else {
//            holder.itemView.tvListNamaUserChatRoom.visibility = View.GONE
//            holder.itemView.ivUserFriendme.visibility = View.GONE
//        }

        var decodeEmoji = EmojiconUtils()
        val spannableString = SpannableString(itemCell[position].namaUser)

        if (itemCell[position].leveManagement == "1") {
            val mainOrange = ForegroundColorSpan(context.resources.getColor(R.color.mainOrangeColor))
            Log.i("LENGTH", " === " + itemCell[position].namaUser!!.length)
            spannableString.setSpan(mainOrange, 0, itemCell[position].namaUser!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else if (itemCell[position].leveManagement == "2") {
            val purple700 = ForegroundColorSpan(context.resources.getColor(R.color.purple_700))
            Log.i("LENGTH", " === " + itemCell[position].namaUser!!.length)
            spannableString.setSpan(purple700, 0, itemCell[position].namaUser!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else if (itemCell[position].leveManagement == "3") {
            val redLight = ForegroundColorSpan(context.resources.getColor(R.color.redLight))
            Log.i("LENGTH", " === " + itemCell[position].namaUser!!.length)
            spannableString.setSpan(redLight, 0, itemCell[position].namaUser!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else if (itemCell[position].leveManagement == "5") {
            val mainBlueColor = ForegroundColorSpan(context.resources.getColor(R.color.mainBlueColor))
            Log.i("LENGTH", " === " + itemCell[position].namaUser!!.length)
            spannableString.setSpan(mainBlueColor, 0, itemCell[position].namaUser!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else if (itemCell[position].leveManagement == "6") {
            val brown = ForegroundColorSpan(context.resources.getColor(R.color.brown))
            Log.i("LENGTH", " === " + itemCell[position].namaUser!!.length)
            spannableString.setSpan(brown, 0, itemCell[position].namaUser!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else if (itemCell[position].leveManagement == "7") {
            holder.itemView.tvListNamaUserChatRoom.text = " : " + decodeEmoji.unescapeJava(itemCell[position].message!!)
            holder.itemView.ivUserFriendme.visibility = View.VISIBLE
        } else if (itemCell[position].leveManagement == "8") {
            val yellowLight2 = ForegroundColorSpan(context.resources.getColor(R.color.yellowLight2))
            Log.i("LENGTH", " === " + itemCell[position].namaUser!!.length)
            spannableString.setSpan(yellowLight2, 0, itemCell[position].namaUser!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else if (itemCell[position].leveManagement == "9") {
            val yellowLight = ForegroundColorSpan(context.resources.getColor(R.color.yellowLight))
            Log.i("LENGTH", " === " + itemCell[position].namaUser!!.length)
            spannableString.setSpan(yellowLight, 0, itemCell[position].namaUser!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else if (itemCell[position].leveManagement == "10") {
            val darkMainBlue = ForegroundColorSpan(context.resources.getColor(R.color.darkMainBlueColor))
            Log.i("LENGTH", " === " + itemCell[position].namaUser!!.length)
            spannableString.setSpan(darkMainBlue, 0, itemCell[position].namaUser!!.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        holder.itemView.tvListNamaUserChatRoom.text =  "$spannableString : " + decodeEmoji.unescapeJava(itemCell[position].message!!)
    }

    override fun getItemCount(): Int {
        return itemCell.size
    }

    fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("yyyy-MM-dd h:m:s")
        return df.parse(date).time
    }
}