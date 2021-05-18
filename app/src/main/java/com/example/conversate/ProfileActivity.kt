package com.example.conversate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.conversate.model.User
import com.example.conversate.utils.Constants
import com.example.conversate.utils.Firestore
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val userId = sharedPref.getString(Constants.LOGGED_IN_ID, "uidHash")

        Firestore().getUserInfoById(this, userId!!)

        profile_appbar.setNavigationOnClickListener {
            val intent = Intent(this, ConversationsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun setUserInfo(userInfo: User){
        profile_name.text = userInfo.name
        profile_email.text = userInfo.email
        profile_number.text = userInfo.phone
    }
}