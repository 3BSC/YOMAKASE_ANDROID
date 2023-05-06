package com.example.yomakase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.yomakase.databinding.ActivityStoreDetailBinding
import com.example.yomakase.dialog.ReservationDialog

class StoreDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoreDetailBinding
    private var reservationDialog = ReservationDialog()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_store_detail)

        binding.btnReservate.setOnClickListener {
            reservationDialog.show(this.supportFragmentManager, "reservation")
        }
    }
}