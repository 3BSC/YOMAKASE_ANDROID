package com.example.yomakase.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yomakase.R
import com.example.yomakase.databinding.ActivityJoinOptionBinding

class JoinOptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinOptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinOptionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun selectGeneralMember(){
         val intent = Intent(this, JoinGeneralMemberActivity::class.java)
        startActivity(intent)
    }

    fun selectOwnerMember(){
        val intent = Intent(this, JoinOwnerMemberActivity::class.java)
        startActivity(intent)
    }
}