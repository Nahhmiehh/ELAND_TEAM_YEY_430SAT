package com.example.ynion.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ynion.R
import com.example.ynion.models.ActivityLogEntry
import com.example.ynion.models.DisplayItem
import java.text.SimpleDateFormat
import java.util.Locale

class ActivityLogAdapter(private var items: List<DisplayItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val simpleDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) // For header if needed

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_LOG_ENTRY = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is DisplayItem.Header -> TYPE_HEADER
            is DisplayItem.Log -> TYPE_LOG_ENTRY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_log_header, parent, false)
                HeaderViewHolder(view)
            }
            TYPE_LOG_ENTRY -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_log_entry, parent, false)
                LogEntryViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val currentItem = items[position]) {
            is DisplayItem.Header -> {
                (holder as HeaderViewHolder).bind(currentItem)
            }
            is DisplayItem.Log -> {
                (holder as LogEntryViewHolder).bind(currentItem.logEntry)
            }
        }
    }

    override fun getItemCount(): Int { return items.size}

//    fun updateData(newItems: List<DisplayItem>) {
//        items = newItems
//        notifyDataSetChanged() // For simplicity. Use DiffUtil for better performance.
//    }

    // ViewHolder for Date Headers
    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val headerTextView: TextView = itemView.findViewById(R.id.tv_date_header)
        private val headerView: ImageView = itemView.findViewById(R.id.bar_view)
        fun bind(headerItem: DisplayItem.Header) {
            headerTextView.text = headerItem.title
            headerView.setImageResource(R.drawable.line_two)

        }
    }

    // ViewHolder for Log Entries
    class LogEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userIconImageView: ImageView = itemView.findViewById(R.id.userProfile)
        private val primaryTextView: TextView = itemView.findViewById(R.id.tvNoteTitle)
        private val secondaryTextView: TextView = itemView.findViewById(R.id.tvUserName)
        private val relativeTimeTextView: TextView = itemView.findViewById(R.id.tvTimestamp)

        fun bind(logEntry: ActivityLogEntry) {
            primaryTextView.text = logEntry.primaryText
            secondaryTextView.text = logEntry.secondaryText
            relativeTimeTextView.text = logEntry.timestamp as CharSequence?

            logEntry.userIconResId?.let {
                userIconImageView.setImageResource(it)
            } ?: userIconImageView.setImageResource(R.drawable.collab_icon) // Default
        }
    }
}