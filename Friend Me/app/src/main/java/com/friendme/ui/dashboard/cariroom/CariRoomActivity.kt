package com.friendme.ui.dashboard.cariroom

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.friendme.R
import com.friendme.contract.CariRoomContract
import com.friendme.presenter.CariRoomPresenter
import com.friendme.ui.dashboard.adapter.AdapterCariRoom
import com.friendme.ui.dashboard.model.ListCariRoomModel
import com.friendme.utils.CustomProgressDialog
import com.friendme.utils.CustomToast
import kotlinx.android.synthetic.main.activity_cari_room.*

class CariRoomActivity : AppCompatActivity(), CariRoomContract.cariRoomView {

    private var presenterCariRoom : CariRoomContract.cariRoomPresenter? = null
    private var progressDialog = CustomProgressDialog()
    private var customToast = CustomToast()
    private var namaRoom : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cari_room)

        presenterCariRoom = CariRoomPresenter(this)

        var sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idUser = sharedPreferences.getString("idUser", "")
        namaRoom = intent.getStringExtra("namaRoom")

        presenterCariRoom?.sendCariRoom(idUser!!, namaRoom!!)

        ivBackCariRoom.setOnClickListener {
            onBackPressed()
        }
    }

    override fun showLoadingCariRoom() {
        progressDialog.show(this, "Harap menunggu...")
    }

    override fun hideLoadingCariRoom() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastCariRoom(message: String) {
        customToast.customToast(this, message)
    }

    override fun getData(data: MutableList<ListCariRoomModel>) {
        rvListCariRoom.apply {
            rvListCariRoom.layoutManager = LinearLayoutManager(this@CariRoomActivity)
            rvListCariRoom.adapter = AdapterCariRoom(data)
        }
    }
}