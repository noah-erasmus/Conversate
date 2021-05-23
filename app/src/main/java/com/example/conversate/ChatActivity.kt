package com.example.conversate

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.conversate.model.Memo
import com.example.conversate.utils.Constants
import com.example.conversate.utils.Firestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private val messagesdb = Firebase.firestore.collection(Constants.MESSAGES).orderBy("user")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        //Prepare SharedPreferences
        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)

        //Get active userID from SharedPrefs
        val userId = sharedPref.getString(Constants.LOGGED_IN_ID, "uidHash")

        send_card.setOnClickListener {
            val content = message_content.text.toString()
            val memo = Memo( content, userId!!, false)
            Firestore().sendMessage(this, memo)
        }

        subcribeToMemoUpdates()
    }

    fun subcribeToMemoUpdates(){
        messagesdb.addSnapshotListener{querySnapshot: QuerySnapshot?, error: FirebaseFirestoreException? ->

            error?.let {
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            }

            val memoList = mutableListOf<Memo>()

            querySnapshot?.let{
                for (document in it){
                    val memo = document.toObject<Memo>()

                    memoList.add(memo)
                }

                val recyclerView = findViewById<RecyclerView>(R.id.rvMessages)
                recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                val adapter = ChatAdapter(memoList)
                recyclerView.adapter = adapter
            }
        }
    }
}