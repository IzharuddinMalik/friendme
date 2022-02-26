package com.friendme.ui.detailroom.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.friendme.R
import com.friendme.contract.DetailRoomContract
import com.friendme.presenter.DetailRoomPresenter
import com.friendme.presenter.SendMessageChatRoomPresenter
import com.friendme.ui.detailroom.adapter.AdapterListChatRoom
import com.friendme.ui.detailroom.model.ListChatRoomModel
import com.google.firebase.FirebaseApp
import com.friendme.contract.SendMessageChatRoomContract
import com.friendme.ui.detailroom.NewDetailRoomActivity
import com.friendme.ui.detailroom.adapter.AdapterListAnggota
import com.friendme.utils.CustomToast
import com.friendme.utils.EmojiconUtils

import kotlinx.android.synthetic.main.new_frag_detail_room.*
import java.util.*
import kotlin.collections.HashMap
import android.os.Environment

import org.alicebot.ab.Chat

import org.alicebot.ab.MagicStrings

import org.alicebot.ab.Bot

import org.alicebot.ab.PCAIMLProcessorExtension

import org.alicebot.ab.AIMLProcessor

import com.friendme.ui.detailroom.adapter.AdapterSms
import com.friendme.ui.detailroom.model.DataSms
import java.io.*
import java.text.SimpleDateFormat

import android.text.TextUtils
import com.friendme.ui.detailroom.adapter.ChatMessageAdapter
import com.friendme.ui.detailroom.model.ChatMessage
import com.google.firebase.database.*
import org.alicebot.ab.Graphmaster

import org.alicebot.ab.MagicBooleans
import com.google.type.LatLng

import com.google.firebase.database.DataSnapshot

class NewFragmentDetailRoom(var idRoom: String, var namaRoom: String?) : Fragment(), DetailRoomContract.detailRoomView, SendMessageChatRoomContract.sendMessageChatRoomView {

    private var presenterDetailRoom : DetailRoomPresenter? = null
    private var presenterSendMessage : SendMessageChatRoomPresenter? = null
    private var root: DatabaseReference? = null
    private var temp_key: String? = ""
    private var chat_msg: String? = ""
    private var chat_user_name: String? = ""
    private var chat_user_level : String? = ""
    private var chat_user_leavedate : String? = ""
    private var chat_user_joindate : String? = ""
    private var userManagement : String? = ""
    private var username : String? = ""
    private var idUserExist : String? = ""
    private var strMessage : String? = ""
    private var strLeaveDate : String? = ""
    private var strJoinDate : String? = ""
    private var strJoinDateFirebase : String? = ""
    private var strLeaveDateFirebase : String? = ""

    private var listChatRoom = mutableListOf<ListChatRoomModel>()

    private var dialogPreview : Dialog? = null
    private var customToast = CustomToast()

    // lowercard
    var listViewType: MutableList<Int>? = null
    var listDataSms: MutableList<DataSms>? = null
    var adapterSms: AdapterSms? = null
    var simpleDateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss")

    private var bot: Bot? = null
    private var chat: Chat? = null

    private var mAdapter : ChatMessageAdapter? = null

    companion object{
        fun newInstance() : NewFragmentDetailRoom {
            val fragment = NewFragmentDetailRoom("", "")
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.new_frag_detail_room, container, false)
        presenterDetailRoom = DetailRoomPresenter(this)
        presenterSendMessage = SendMessageChatRoomPresenter(this)
        root = FirebaseDatabase.getInstance().getReference().child(namaRoom!!)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var sharedPreferences = requireActivity().getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")
        userManagement = sharedPreferences.getString("levelManagement", "")
        idUserExist = idUser

        presenterDetailRoom?.sendDetailRoom(idUser!!, idRoom)
        presenterDetailRoom?.onDataRoom()!!.observe(requireActivity()) {
            tvNamaRoomWelcomeNew.text = it.namaRoom + " : " + "Welcome to ${it.namaRoom}"
            tvNamaRoomManagedNew.text = it.namaRoom + " : " + "This room is managed by ${it.managedRoom}"

            var namaUser = ""
            for (i in 0 until it.listUser!!.size) {
                var username = it.listUser!![i]
                namaUser = namaUser + username.username + ","
            }

            username = it.usernameRoom
            strLeaveDate = it.leaveDate
            strJoinDate = it.joinDate

            tvNamaRoomCurrentlyNew.text = it.namaRoom + " : " + "Currently in room " + namaUser

            tvNamaRoomWelcomeLowerCard.text = it.namaRoom + " : " + "Welcome to ${it.namaRoom}"
            tvNamaRoomManagedLowerCard.text = it.namaRoom + " : " + "This room is managed by ${it.managedRoom}"
            tvNamaRoomCurrentlyLowerCard.text = it.namaRoom + " : " + "Currently in room " + namaUser

            if (it.categoryRoom == "3") {
                llGameLowerCard.visibility = View.VISIBLE
                llContentChat.visibility = View.GONE
            } else {
                llGameLowerCard.visibility = View.GONE
                llContentChat.visibility = View.VISIBLE
            }
        }

        FirebaseApp.initializeApp(requireContext())

        ivOptionNewRoom.setOnClickListener {
            optionRoom()
        }

        ivSendNewFragChatRoom.setOnClickListener{
            if (!checkMessage()) {
                customToast.customToast(requireContext(), "Cek data Anda")
            } else {
                val map: Map<String, Any> = HashMap()
                temp_key = root!!.push().key
                root!!.updateChildren(map)

                val message_root = root!!.child(temp_key!!)
                val map2: MutableMap<String, Any> = HashMap()
                Log.i("USERNAME", " === " + chat_user_name)
                Log.i("MESSAGE", " === " + strMessage)
                map2["name"] = username!!
                map2["msg"] = strMessage!!
                map2["managementlevel"] = userManagement!!
                map2["leavedate"] = strLeaveDate!!
                map2["joindate"] = strJoinDate!!

                message_root.updateChildren(map2)

                presenterSendMessage?.sendMessageChatRoom(idUserExist!!, idRoom, strMessage!!)

                // Clear the input
                edtMessageNewFragChatRoom.setText("")
            }
        }

        var queryJoinDate = root!!.orderByChild("joindate").limitToLast(1)
        var queryLeaveDate = root!!.orderByChild("leavedate").limitToLast(1)

        queryJoinDate.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.getChildren()) {
                    strJoinDateFirebase = ds.child("joindate").value as String?
                    Log.i("JOINDATEFIREBASE", " === " + strJoinDateFirebase)
                }
            }
        })

        queryLeaveDate.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    strLeaveDateFirebase = ds.child("leavedate").value as String?
                    Log.i("LEAVEDATEFIREBASE", " === " + strLeaveDateFirebase)
                }
            }
        })

        root!!.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                Log.i("COMPAREFIREBASE", " === " + strJoinDateFirebase!!.compareTo(strLeaveDateFirebase!!))
                if (strJoinDateFirebase!!.compareTo(strLeaveDateFirebase!!) > 0){
                    append_chat_conversatin(dataSnapshot)
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                if (strJoinDateFirebase!!.compareTo(strLeaveDateFirebase!!) > 0) {
                    append_chat_conversatin(dataSnapshot)
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        //bot
        ivSendNewFragChatLowerCard.setOnClickListener {
            kirimPesan()
        }

        mAdapter = ChatMessageAdapter(context, ArrayList<ChatMessage>())
        rvListNewFragBotChatLowerCard.setAdapter(mAdapter)
        loadData()
    }

    private fun append_chat_conversatin(dataSnapshot: DataSnapshot) {
        val i: Iterator<*> = dataSnapshot.children.iterator()
        while (i.hasNext()) {
            chat_user_joindate = (i.next() as DataSnapshot).value as String?
            chat_user_leavedate = (i.next() as DataSnapshot).value as String?
            chat_user_level = (i.next() as DataSnapshot).value as String?
            chat_msg = (i.next() as DataSnapshot).value as String?
            chat_user_name = (i.next() as DataSnapshot).value as String?
            listChatRoom.add(ListChatRoomModel(chat_user_name.toString(), chat_msg, chat_user_level, chat_user_joindate, chat_user_leavedate))
            rvListNewFragChatRoom.apply {
                if (isAdded) {
                    rvListNewFragChatRoom.layoutManager = LinearLayoutManager(requireActivity())
                    rvListNewFragChatRoom.adapter = AdapterListChatRoom(listChatRoom)
                    rvListNewFragChatRoom.scrollToPosition(listChatRoom.size - 1)
                }
            }
        }
    }

    fun checkMessage() : Boolean {
        var encodeEmoji = EmojiconUtils()
        strMessage = encodeEmoji.escapeJavaStringEmoji(edtMessageNewFragChatRoom!!.text.toString())

        if (strMessage!!.isEmpty()) {
            customToast.customToast(requireContext(), "Harap isi pesan anda")
            return false
        }

        return true
    }

    override fun showToastDetailRoom(message: String) {

    }

    override fun getMessageLeave(message: String) {
        popupLeaveRoom(message)
    }

    fun popupLeaveRoom(message: String){
        dialogPreview = Dialog(requireContext())
        dialogPreview!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogPreview!!.setContentView(R.layout.dialog_message_logout)
        dialogPreview!!.setCancelable(false)
        val window = dialogPreview!!.window
        window!!.setGravity(Gravity.CENTER)
        window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        window!!.setBackgroundDrawable(resources.getDrawable(R.drawable.background_transparent))

        val tvMessage = dialogPreview!!.findViewById<TextView>(R.id.tvMessageLogout)
        val tvLogout = dialogPreview!!.findViewById<TextView>(R.id.tvLogout)

        tvMessage.text = message
        tvLogout.setOnClickListener {
            presenterDetailRoom?.onKickUser()!!.observe(requireActivity()) {
                if (it == true) {
                    startActivity(Intent(requireContext(), NewDetailRoomActivity::class.java))
                }
            }
        }

        dialogPreview!!.show()
    }

    fun optionRoom(){
        dialogPreview = Dialog(requireContext())
        dialogPreview!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogPreview!!.setContentView(R.layout.dialog_listoption_detailroom)
        dialogPreview!!.setCancelable(true)
        val window = dialogPreview!!.window
        window!!.setGravity(Gravity.LEFT)
        window!!.setLayout(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        window!!.setBackgroundDrawable(resources.getDrawable(R.drawable.background_transparent))

        val leaveRoom = dialogPreview!!.findViewById<CardView>(R.id.cvLeaveRoom)
        val rvListAnggota = dialogPreview!!.findViewById<RecyclerView>(R.id.rvListAnggotaDetailRoom)
        val tvNamaRoom = dialogPreview!!.findViewById<TextView>(R.id.tvNamaRoomDetailRoom)
        val tvJumlahUser = dialogPreview!!.findViewById<TextView>(R.id.tvJumlahUserOnlineDetailRoom)

        leaveRoom.setOnClickListener {
            presenterDetailRoom?.kickRoom(idUserExist!!, idRoom)
        }

        tvNamaRoom.text = namaRoom

        presenterDetailRoom?.listAnggotaRoom(idUserExist!!, idRoom)
        presenterDetailRoom?.onListAnggota()!!.observe(requireActivity()) {
            rvListAnggota.apply {
                rvListAnggota.layoutManager = LinearLayoutManager(requireContext())
                rvListAnggota.adapter = AdapterListAnggota(it, idRoom)
            }

            tvJumlahUser.text = "User (${it.size.toInt()}/40)"
        }

        dialogPreview!!.show()
    }

    override fun showToastSendMessageChatRoom(message: String) {
        customToast.customToast(requireContext(), message)
    }

    override fun successSendMessageChatRoom() {

    }

    // game chatbot
    private fun loadData() {
        //checking SD card availablility
        val a: Boolean = isSdCardAvailable()
        //receiving the assets from the app directory
        val assets = resources.assets
        val jayDir = File(Environment.getExternalStorageDirectory().toString() + "/chatbot/bots/chatbot")
        val b = jayDir.mkdirs()
        if (jayDir.exists()) {
            //Reading the file
            try {
                for (dir in assets.list("chatbot")!!) {
                    val subdir = File(jayDir.path.toString() + "/" + dir)
                    val subdir_check = subdir.mkdirs()
                    for (file in assets.list("chatbot/$dir")!!) {
                        val f = File(jayDir.path.toString() + "/" + dir + "/" + file)
                        if (f.exists()) {
                            continue
                        }
                        var `in`: InputStream? = null
                        var out: OutputStream? = null
                        `in` = assets.open("chatbot/$dir/$file")
                        out = FileOutputStream(jayDir.path.toString() + "/" + dir + "/" + file)
                        //copy file from assets to the mobile's SD card or any secondary memory
                        copyFile(`in`, out)
                        `in`.close()
                        `in` = null
                        out.flush()
                        out.close()
                        out = null
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        val assetManager: AssetManager = resources.assets
        var files: Array<String>? = null
        try {
            files = assetManager.list("chatbot")

            if (files != null) for (filename in files) {
                var `in`: InputStream? = null
                var out: OutputStream? = null
                try {
                    `in` = assetManager.open(filename)
                    val outFile = File(requireContext().getExternalFilesDir(null), filename)
                    out = FileOutputStream(outFile)
                    copyFile(`in`, out)
                } catch (e: IOException) {
                    Log.e("tag", "Failed to copy asset file: $filename", e)
                } finally {
                    if (`in` != null) {
                        try {
                            `in`.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                    if (out != null) {
                        try {
                            out.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            }

        } catch (e: IOException) {
            Log.e("tag", "Failed to get asset file list.", e)
        }


        //get the working directory
        MagicStrings.root_path = Environment.getExternalStorageDirectory().toString() + "/chatbot"
        println("Working Directory = " + MagicStrings.root_path)
        AIMLProcessor.extension = PCAIMLProcessorExtension()
        //Assign the AIML files to bot for processing
        bot = Bot("chatbot", MagicStrings.root_path, "chat")
        chat = Chat(bot)
        val args: Array<String>? = null
        mainFunctions(args)
    }

    @Throws(IOException::class)
    private fun copyFile(`in`: InputStream, out: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int
        while (`in`.read(buffer).also { read = it } != -1) {
            out.write(buffer, 0, read)
        }
    }

    private fun isSdCardAvailable(): Boolean {
        return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) true else false
    }

    private fun sendMessage(message: String) {
        val chatMessage = ChatMessage(message, true, false)
        Log.i("CHATMESSAGE", " === " + message)
        mAdapter!!.add(chatMessage)
    }

    private fun mimicOtherMessage(message: String) {
        val chatMessage = ChatMessage(message, false, false)
        mAdapter!!.add(chatMessage)
    }

    //Request and response of user and the bot
    fun mainFunctions(args: Array<String>?) {
        MagicBooleans.trace_mode = false
        println("trace mode = " + MagicBooleans.trace_mode)
        Graphmaster.enableShortCuts = true
        val timer = Timer()
        val request = "Hello."
        val response = chat!!.multisentenceRespond(request)
        println("Human: $request")
        println("Robot: $response")
    }

    fun kirimPesan() {
        val message: String = edtMessageNewFragChatLowerCard.getText().toString()
        //bot
        //bot
        val response = chat!!.multisentenceRespond(edtMessageNewFragChatLowerCard.getText().toString())
        if (TextUtils.isEmpty(message)) {
            return
        }
        sendMessage(message)
        mimicOtherMessage(response)
        edtMessageNewFragChatLowerCard.setText("")
        rvListNewFragBotChatLowerCard.setSelection(mAdapter!!.count - 1)
    }
}