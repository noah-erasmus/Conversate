package com.example.conversate

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.conversate.model.Memo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.conversate.utils.Constants
import com.example.conversate.utils.Constants.LOGGED_IN_NAME
import kotlinx.android.synthetic.main.chat_message.view.*

class ChatAdapter(val memoList: MutableList<Memo>, val loggedInUser: String): RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_message, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
        val currentMemo = memoList[position]

        when(currentMemo.user){
            loggedInUser -> {
                holder.itemView.send_message_contents.apply {
                    text = currentMemo.content
                    visibility = View.VISIBLE
                }
                holder.itemView.message_time_sent.apply {
                    text = currentMemo.datetime.drop(11).dropLast(3)
                    visibility = View.VISIBLE
                }
                holder.itemView.received_message.visibility = View.GONE
            }

            else -> {
                holder.itemView.receive_message_contents.apply {
                    text = currentMemo.content
                    visibility = View.VISIBLE
                }
                holder.itemView.message_time_received.apply {
                    text = currentMemo.datetime.drop(11).dropLast(3)
                    visibility = View.VISIBLE
                }
                holder.itemView.sent_message.visibility = View.GONE

            }
        }
        holder.bindItems(memoList[position])
    }

    override fun getItemCount(): Int {
        return memoList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindItems(memo: Memo){
            val memoContents = itemView.findViewById<TextView>(R.id.receive_message_contents)
            memoContents.text = memo.content
        }
    }
}