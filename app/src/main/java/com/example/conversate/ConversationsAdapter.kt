package com.example.conversate

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.conversate.model.Memo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.conversate.model.User
import com.example.conversate.utils.Constants
import com.example.conversate.utils.Constants.LOGGED_IN_NAME
import kotlinx.android.synthetic.main.chat_message.view.*
import kotlinx.android.synthetic.main.convo_entry.view.*

class ConversationsAdapter(val convoList: MutableList<User>): RecyclerView.Adapter<ConversationsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.convo_entry, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ConversationsAdapter.ViewHolder, position: Int) {
        val currentConvo = convoList[position]

        holder.itemView.convo_contact.apply {
            text = currentConvo.name
        }

        holder.bindItems(convoList[position])
    }

    override fun getItemCount(): Int {
        return convoList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindItems(convo: User){
            val convoContact = itemView.findViewById<TextView>(R.id.convo_contact)
            convoContact.text = convo.name
        }
    }
}