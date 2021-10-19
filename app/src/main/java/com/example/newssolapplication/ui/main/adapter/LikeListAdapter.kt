package com.example.newssolapplication.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.newssolapplication.R
import com.example.newssolapplication.common.dto.LikeDTO
import com.example.newssolapplication.common.room.LikeContact

class LikeListAdapter(val likeListItemClick: (LikeContact) -> Unit) : RecyclerView.Adapter<LikeListAdapter.ViewHolder>() {
    private var likeList: List<LikeContact> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_like_contact,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LikeListAdapter.ViewHolder, position: Int) {
        holder.bind(likeList[position])
    }

    override fun getItemCount(): Int {
        return likeList.size
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val textFolderTitle = itemView.findViewById<TextView>(R.id.text_folder_title)
        private val viewFolder = itemView.findViewById<ConstraintLayout>(R.id.view_folder)
        fun bind(likeList: LikeContact){
            textFolderTitle.apply {
                text = likeList.title
            }
            viewFolder.setOnClickListener {
                likeListItemClick(likeList)
            }
        }
    }

    fun setLikeList(_likeList: List<LikeContact>){
        this.likeList = _likeList
        notifyDataSetChanged()
    }
}