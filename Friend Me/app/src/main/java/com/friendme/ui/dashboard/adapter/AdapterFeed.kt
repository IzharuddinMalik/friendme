package com.friendme.ui.dashboard.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.friendme.R
import com.friendme.contract.LikeFeedContract
import com.friendme.presenter.LikeFeedPresenter
import com.friendme.ui.dashboard.fragment.FragFeed
import com.friendme.ui.dashboard.model.ListFeedModel
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import com.friendme.utils.EmojiconUtils
import kotlinx.android.synthetic.main.inflater_feed.view.*
import kotlinx.coroutines.currentCoroutineContext
import java.text.SimpleDateFormat

class AdapterFeed(private val itemCell : MutableList<ListFeedModel>) : RecyclerView.Adapter<AdapterFeed.FeedViewHolder>(),
LikeFeedContract.likeFeedView{

    private lateinit var context: Context
    private var presenterLikeFeed : LikeFeedContract.likeFeedPresenter? = null
    private var customToast = CustomToast()

    private var dialogPreview : Dialog? = null

    class FeedViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_feed, parent, false)
        val ksv = FeedViewHolder(view)
        context = parent.context

        presenterLikeFeed = LikeFeedPresenter(this)
        return ksv
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        var decodeEmoji = EmojiconUtils()

        var patternTTL = "h:m:s dd MMMM yyyy"
        var simpleDateFormat2 : SimpleDateFormat = SimpleDateFormat(patternTTL)
        var tanggalFromAPI2 = SimpleDateFormat("yyyy-MM-dd h:m:s")
        var tanggalFeed = simpleDateFormat2.format(tanggalFromAPI2.parse(itemCell[position].createOn))

        holder.itemView.tvMessageFeed.text = decodeEmoji.unescapeJava(itemCell[position].message!!)
        holder.itemView.tvCreatedOnFeed.text = tanggalFeed
        holder.itemView.tvJumlahLikesFeed.text = itemCell[position].likeCount
        holder.itemView.tvNamaUserFeed.text = itemCell[position].namaUser

        if (itemCell[position].imageFeed == "") {
            holder.itemView.ivFotoFeed.visibility = View.GONE
        } else {
            holder.itemView.ivFotoFeed.visibility = View.VISIBLE
        }

        Glide.with(context).load("https://idfriendme.com/imageapps/"+itemCell[position].imageFeed).into(holder.itemView.ivFotoFeed)
        Glide.with(context).load("https://idfriendme.com/imageapps/"+itemCell[position].fotoProfile).into(holder.itemView.civFotoUserFeed)

        holder.itemView.ivFotoFeed.setOnClickListener {
            popupDialog(itemCell[position].imageFeed)
        }

        var sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")

        holder.itemView.ivLikeFeed.setOnClickListener {
            presenterLikeFeed?.sendLikeFeed(idUser!!, itemCell[position].idFeed!!)
        }
    }

    override fun getItemCount(): Int {
        return itemCell.size
    }

    fun popupDialog(urlFoto : String?){
        dialogPreview = Dialog(context)
        dialogPreview!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogPreview!!.setContentView(R.layout.dialog_preview_fotofeed)
        dialogPreview!!.setCancelable(true)
        val window = dialogPreview!!.window
        window!!.setGravity(Gravity.CENTER)
        window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        window!!.setBackgroundDrawable(context.resources.getDrawable(R.drawable.background_transparent))

        val fotoFeed = dialogPreview!!.findViewById<ImageView>(R.id.ivFotoDialogFeed)

        Glide.with(context).load("https://idfriendme.com/imageapps/" + urlFoto).into(fotoFeed)

        dialogPreview!!.show()
    }

    override fun showToastLikeFeed(message: String) {
        customToast.customToast(context, message)
    }

    override fun updateFeed() {
        FragFeed::onRefreshFeed
    }
}