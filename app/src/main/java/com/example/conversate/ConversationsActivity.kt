package com.example.conversate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_conversations.*

class ConversationsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversations)

        logout_btn.setOnClickListener{
            logoutUser()
        }

        conversate_toolbar.setOnMenuItemClickListener{menuItem ->
            when(menuItem.itemId){
                R.id.more -> {
                    logoutUser()
                    true
                }else -> false
            }
        }
    }

    fun logoutUser(){
        Firebase.auth.signOut()

        val intent = Intent(this, AuthenticationActivity::class.java)
        startActivity(intent)
        finish()
    }
}