package com.example.newssolapplication.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.newssolapplication.R
import com.example.newssolapplication.common.dto.ItemDTO
import com.example.newssolapplication.common.room.LikeContact

class ItemAdapter(val itemClick: (ItemDTO) -> Unit) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private var itemList: List<ItemDTO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textFolderTitle = itemView.findViewById<TextView>(R.id.text_folder_title)
        private val viewFolder = itemView.findViewById<View>(R.id.view_folder)
        fun bind(item : ItemDTO){
            textFolderTitle.apply {
                text = if(item.title!!.isEmpty()){
                    "추가하기"
                } else item.title
            }
            viewFolder.setOnClickListener {
                itemClick(item)
            }
        }
    }

    fun setItemList(_itemList:List<ItemDTO>){
        this.itemList = _itemList
        notifyDataSetChanged()
    }
}