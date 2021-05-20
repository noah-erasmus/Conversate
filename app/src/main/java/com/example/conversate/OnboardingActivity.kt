package com.example.conversate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.conversate.model.User
import com.example.conversate.utils.Constants
import com.example.conversate.utils.Firestore
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        //Prepare SharedPrefences
        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        //Get active user ID from SharedPrefs
        val userId = sharedPref.getString(Constants.LOGGED_IN_ID, "uidHash")

        //Proceed after completing onboarding
        onboarding_proceed.setOnClickListener{
            //Get value from text input
            val username = setUsername_btn.text.toString()

            //Run firestore add user method
            Firestore().setUsername(this, userId!!, username)

            //Redirect to conversations page
            val intent = Intent(this, ConversationsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}