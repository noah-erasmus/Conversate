package com.example.conversate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.conversate.model.User
import com.example.conversate.utils.Constants
import com.example.conversate.utils.Firestore
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val userId = sharedPref.getString(Constants.LOGGED_IN_ID, "uidHash")

        onboarding_proceed.setOnClickListener{
            val username = setUsername_btn.text.toString()

            Firestore().setUsername(this, userId!!, username)

            val intent = Intent(this, ConversationsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}