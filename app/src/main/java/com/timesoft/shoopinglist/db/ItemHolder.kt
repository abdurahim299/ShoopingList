package com.timesoft.shoopinglist.db

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timesoft.shoopinglist.R
import com.timesoft.shoopinglist.databinding.NoteListItemBinding
import com.timesoft.shoopinglist.entities.NoteItem
import com.timesoft.shoopinglist.utils.HtmlManager
import com.timesoft.shoopinglist.utils.TimeManager

class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = NoteListItemBinding.bind(view)

    fun setData(noteItem: NoteItem, listener: Listener, defPref: SharedPreferences) = with(binding) {
        tvTitle.text = noteItem.title
        tvDescription.text = HtmlManager.getFromHtml(noteItem.content).trim()
        tvTime.text = TimeManager.getTimeFormat(noteItem.time, defPref)
        imDelete.setOnClickListener {
            listener.deleteItem(noteItem.id!!)
        }
        itemView.setOnClickListener {
            listener.onClickItem(noteItem)
        }
        imDelete.setOnClickListener {
            listener.deleteItem(noteItem.id!!)
        }
    }

    companion object {
        fun create(parent: ViewGroup): ItemHolder {
            return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
            )
        }
    }

}