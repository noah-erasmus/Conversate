package com.example.conversate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_conversations.*

class ConversationsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversations)
        setSupportActionBar(conversate_toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.conversations_app_bar, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this, "blah", Toast.LENGTH_SHORT)
        val id = item.itemId
        if(id == R.id.profile_btn){
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
            return true
        }
        if(id == R.id.logout_btn){
            logoutUser()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun logoutUser(){
        Firebase.auth.signOut()

        val intent = Intent(this, AuthenticationActivity::class.java)
        startActivity(intent)
        finish()
    }
}