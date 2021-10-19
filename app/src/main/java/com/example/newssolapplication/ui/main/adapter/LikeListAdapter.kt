package com.example.newssolapplication.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.newssolapplication.R
import com.example.newssolapplication.common.dto.LikeDTO
import com.example.newssolapplication.common.room.LikeContact

class LikeListAdapter(val likeListItemClick: (LikeContact) -> Unit) :
    RecyclerView.Adapter<LikeListAdapter.ViewHolder>() {
    private var likeList: List<LikeContact> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeListAdapter.ViewHolder {
        val layout = if(viewType == 0) {
            parent.context.resources.getLayout(R.layout.item_folder_add)
        }else{
            parent.context.resources.getLayout(R.layout.item_like_contact)
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LikeListAdapter.ViewHolder, position: Int) {
        holder.bind(likeList[position])
    }

    override fun getItemCount(): Int {
        return likeList.size
    }

    override fun getItemViewType(position: Int): Int {
        Log.d("testSize",likeList.size.toString())
        Log.d("testPosition",position.toString())
        return if (likeList.size == position+1) {
            0
        } else
            1
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textFolderTitle = itemView.findViewById<TextView>(R.id.text_folder_title)
        private val viewFolder = itemView.findViewById<ConstraintLayout>(R.id.view_folder)
        fun bind(likeList: LikeContact) {
                textFolderTitle.apply {
                    text = if(likeList.title!!.isEmpty()){
                        "추가하기"
                    } else likeList.title
                }
                viewFolder.setOnClickListener {
                    likeListItemClick(likeList)
                }
        }
    }

    fun setLikeList(_likeList: MutableList<LikeContact>) {
        _likeList.add(LikeContact(0, "", "", "", ""))
        this.likeList = _likeList
        notifyDataSetChanged()
    }
}