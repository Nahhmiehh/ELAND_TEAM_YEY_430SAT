package com.example.ynion.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ynion.R
import com.example.ynion.dataclass.StarredDataClass

class StarredAdapterClass(private val dataList: ArrayList<StarredDataClass>): RecyclerView.Adapter<StarredAdapterClass.ViewHolderlass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, ): ViewHolderlass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_starred_note_list, parent, false)
        return ViewHolderlass(itemView)
    }

    override fun onBindViewHolder(
        holder: ViewHolderlass,
        position: Int,
    ) {
        val currentItem = dataList[position]

        holder.rvNoteTitle.text = currentItem.dataTitle
        holder.rvStarred.setImageResource(currentItem.starredImage)
        holder.rvEdit.setImageResource(currentItem.editNoteImage)
        holder.rvDelete.setImageResource(currentItem.deleteImage)
//        holder.rvCollaboration.setImage = currentItem.recyclerView //for collaborators
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolderlass(itemView: View): RecyclerView.ViewHolder(itemView){
        val rvNoteTitle: TextView = itemView.findViewById(R.id.noteTitle)
        val rvEdit: ImageView = itemView.findViewById(R.id.editOption)
        val rvDelete: ImageView = itemView.findViewById(R.id.deleteOption)
        //        val rvCollaboration: RecyclerView = itemView.findViewById(R.id.collaboratorRecyclerView)
        val rvStarred: ImageView = itemView.findViewById(R.id.starredButton)
    }
}