package com.example.ynion.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.ynion.R
import com.example.ynion.dataclass.CollabDataClass

class CollabAdapterClass(private val dataList: ArrayList<CollabDataClass>): RecyclerView.Adapter<CollabAdapterClass.ViewHolderlass>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolderlass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_notelist, parent, false)
        return ViewHolderlass(itemView)
    }

    override fun onBindViewHolder(
        holder: ViewHolderlass,
        position: Int,
    ) {
        val currentItem = dataList[position]

        holder.rvCollabImage.setImageResource(currentItem.imageHolder)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolderlass(itemView: View): RecyclerView.ViewHolder(itemView){
        val rvCollabImage: ImageView = itemView.findViewById(R.id.collabImageHolder)
    }
}