package com.example.conversate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import com.example.conversate.model.Memo
import com.example.conversate.utils.Firestore
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        send_card.setOnClickListener {
            val content = message_content.text.toString()
            val memo = Memo( content, "Noah", false)
            Firestore().sendMessage(this, memo)
        }
    }
}