package com.example.conversate

import androidx.recyclerview.widget.RecyclerView
import com.example.conversate.model.Memo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ChatAdapter(val memoList: MutableList<Memo>): RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_message, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
        holder.bindItems(memoList[position])
    }

    override fun getItemCount(): Int {
        return memoList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindItems(memo: Memo){
            val memoContents = itemView.findViewById<TextView>(R.id.message_contents)
            memoContents.text = memo.content
        }
    }
}