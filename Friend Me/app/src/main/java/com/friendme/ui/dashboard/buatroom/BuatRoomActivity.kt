package com.friendme.ui.dashboard.buatroom

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.friendme.R
import com.friendme.contract.CreateRoomContract
import com.friendme.presenter.CreateRoomPresenter
import com.friendme.ui.dashboard.DashboardActivity
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_buat_room.*
import kotlinx.android.synthetic.main.frag_home.*
import com.google.firebase.database.FirebaseDatabase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener

class BuatRoomActivity : AppCompatActivity(), CreateRoomContract.createRoomView {

    private var presenterCreateRoom : CreateRoomPresenter? = null
    private var progressDialog = CustomProgressDialog()
    private var customToast = CustomToast()
    private var strNamaRoom : String? = ""
    private var strDescRoom : String? = ""
    private var idCategoryRoom : String? = ""
    private val root = FirebaseDatabase.getInstance().reference.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buat_room)

        presenterCreateRoom = CreateRoomPresenter(this)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")
        var levelManagement = sharedPreferences.getString("levelManagement", "")

        if (levelManagement == "1") {
            presenterCreateRoom?.getCategoryRoom(idUser!!)
            spinCategoryRoom.visibility = View.VISIBLE
            tvPilihKategoriRoom.visibility = View.VISIBLE
        } else {
            spinCategoryRoom.visibility = View.GONE
            tvPilihKategoriRoom.visibility = View.GONE
        }

        cvBuatRoom.setOnClickListener {
            if (!checkNamaRoom() || !checkDescRoom()) {
                customToast.customToast(this, "Cek kembali data Anda!")
            } else {
                if (levelManagement == "1") {
                    presenterCreateRoom?.sendCreateRoom(idUser!!, strNamaRoom!!, strDescRoom!!, idCategoryRoom!!)

                    val map: MutableMap<String, Any> = HashMap()
                    map[strNamaRoom!!] = ""
                    root.updateChildren(map)
                } else {
                    presenterCreateRoom?.sendCreateRoom(idUser!!, strNamaRoom!!, strDescRoom!!, "")

                    val map: MutableMap<String, Any> = HashMap()
                    map[strNamaRoom!!] = ""
                    root.updateChildren(map)
                }
            }
        }

        root.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val set: MutableSet<String?> = HashSet()
                val i: Iterator<*> = dataSnapshot.children.iterator()
                while (i.hasNext()) {
                    set.add((i.next() as DataSnapshot).key)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        initLiveData()
    }

    fun initLiveData() {
        presenterCreateRoom?.onRefreshCatRoom()!!.observe(this) {

            var idCatRoom = arrayOfNulls<String>(it.size+1)
            var categoryRoom = arrayOfNulls<String>(it.size+1)

            idCatRoom[0] = "0"
            categoryRoom[0] = "Pilih Kategori Room"

            for (i in 0 until it.size) {
                val data = it[i]

                idCatRoom[i+1] = data.idCategoryRoom
                categoryRoom[i+1] = data.categoryRoom
            }

            var adapter = this?.let { ArrayAdapter(it, R.layout.inflater_textview, categoryRoom) }
            adapter?.setDropDownViewResource(R.layout.inflater_textview)
            spinCategoryRoom.setAdapter(adapter)
            spinCategoryRoom.setSelection(0, false)
            spinCategoryRoom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    idCategoryRoom = idCatRoom[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }
    }

    fun checkNamaRoom() : Boolean {
        strNamaRoom = edtNamaRoomBuatRoom.text.toString()

        if (strNamaRoom!!.isEmpty()) {
            customToast.customToast(this, "Nama room tidak boleh kosong!")
            return false
        }

        return true
    }

    fun checkDescRoom() : Boolean {
        strDescRoom = edtDescRoomBuatRoom.text.toString()

        if (strDescRoom!!.isEmpty()) {
            customToast.customToast(this, "Description room tidak boleh kosong!")
            return false
        }

        return true
    }

    override fun showLoadingCreateRoom() {
        progressDialog.show(this, "Harap menunggu...")
    }

    override fun hideLoadingCreateRoom() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastCreateRoom(message: String) {
        customToast.customToast(this, message)
    }

    override fun successCreateRoom() {
        startActivity(Intent(this, DashboardActivity::class.java))
    }
}