package com.example.newssolapplication.ui.main.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newssolapplication.R
import com.example.newssolapplication.common.dto.CategoryVO

class CategoryAdapter(val categoryItemClick: (CategoryVO) -> Unit):
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private var category: List<CategoryVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category_contact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        Log.d("TESTDD", category[position].name.toString())
        holder.bind(category[position])
    }

    override fun getItemCount(): Int {
        return category.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val categoryName = itemView.findViewById<TextView>(R.id.category_name)
        fun bind(category: CategoryVO){
            categoryName.apply {
                text = category.name
                elevation = 5f
            }
            categoryName.text = category.name

            itemView.setOnClickListener {
                categoryItemClick(category)
            }
        }
    }

    fun setCategory(categoryList: List<CategoryVO>){
        this.category = categoryList.sortedBy { it.count }.reversed()
        notifyDataSetChanged()
    }

}