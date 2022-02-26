package com.friendme.ui.privatemessage

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.database.FirebaseListAdapter
import com.friendme.R
import com.friendme.contract.GetProfileContract
import com.friendme.contract.PrivateMessageContract
import com.friendme.presenter.GetProfilePresenter
import com.friendme.presenter.PrivateMessagePresenter
import com.friendme.ui.dashboard.DashboardActivity
import com.friendme.ui.privatemessage.adapter.AdapterPrivateMessage
import com.friendme.ui.privatemessage.model.PrivateMessageModel
import com.friendme.ui.profile.model.ProfileModel
import com.friendme.utils.CustomToast
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.activity_detail_room.*
import kotlinx.android.synthetic.main.activity_private_message.*
import com.firebase.ui.database.FirebaseListOptions
import com.friendme.presenter.DetailRoomPresenter
import com.friendme.ui.detailroom.adapter.AdapterListChatRoom
import com.friendme.ui.detailroom.model.ListChatRoomModel
import com.friendme.utils.EmojiconUtils
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.new_frag_detail_room.*

class PrivateMessageActivity : AppCompatActivity(), GetProfileContract.getProfileView {

    private var getProfile : GetProfileContract.getProfilePresenter? = null
    private var customToast = CustomToast()
    private var messagePM : String? = ""
    private var namaPengirim : String? = ""
    private var namaPenerima : String? = ""
    private var fotoPenerima : String? = ""
    private var listChatPM = mutableListOf<PrivateMessageModel>()

    private var root: DatabaseReference? = null
    private var temp_key: String? = ""
    private var chat_msg: String? = ""
    private var chat_user_name: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_message)

        FirebaseApp.initializeApp(this)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")

        namaPenerima = intent.getStringExtra("namaPenerima")
        fotoPenerima = intent.getStringExtra("fotoPenerima")

        getProfile = GetProfilePresenter(this)
        getProfile?.sendGetProfile(idUser!!)

        root = FirebaseDatabase.getInstance().getReference().child(namaPenerima!!)

        tvUsernamePrivateMessage.text = namaPenerima
        Glide.with(this).load("https://idfriendme.com/imageapps/"+fotoPenerima).into(ivFotoProfilePrivateMessage)

        ivBackPrivateMessage.setOnClickListener {
            this.onBackPressed()
        }

        ivSendPM.setOnClickListener {

            if (!onCheckMessage()) {
                customToast.customToast(this, "Cek kembali")
            } else {
                val map: Map<String, Any> = HashMap()
                temp_key = root!!.push().key
                root!!.updateChildren(map)

                val message_root = root!!.child(temp_key!!)
                val map2: MutableMap<String, Any> = HashMap()
                Log.i("USERNAME", " === " + chat_user_name)
                Log.i("MESSAGE", " === " + messagePM)
                map2["name"] = namaPengirim!!
                map2["msg"] = messagePM!!

                message_root.updateChildren(map2)

                // Clear the input
                edtMessagePM.setText("")
            }
        }

        root!!.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                append_chat_conversatin(dataSnapshot)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                append_chat_conversatin(dataSnapshot)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun append_chat_conversatin(dataSnapshot: DataSnapshot) {
        val i: Iterator<*> = dataSnapshot.children.iterator()
        while (i.hasNext()) {
            chat_msg = (i.next() as DataSnapshot).value as String?
            chat_user_name = (i.next() as DataSnapshot).value as String?
            listChatPM.add(PrivateMessageModel(chat_msg, chat_user_name))
            rvListMessagePrivateMessage.apply {
                rvListMessagePrivateMessage.layoutManager = LinearLayoutManager(this@PrivateMessageActivity)
                rvListMessagePrivateMessage.adapter = AdapterPrivateMessage(listChatPM)
            }
        }
    }

    fun onCheckMessage() : Boolean {
        var encodeEmoji = EmojiconUtils()
        messagePM = encodeEmoji.escapeJavaStringEmoji(edtMessagePM.text.toString())

        if (messagePM!!.isEmpty()) {
            customToast.customToast(this, "Pastikan Anda menuliskan pesan terlebih dahulu")
            return false
        }

        return true
    }

    override fun showLoadingProfile() {

    }

    override fun hideLoadingProfile() {

    }

    override fun showToastProfile(message: String) {

    }

    override fun getData(profile: ProfileModel) {
        namaPengirim = profile.username
    }
}