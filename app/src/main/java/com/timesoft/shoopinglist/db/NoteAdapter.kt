package com.timesoft.shoopinglist.db


import android.content.SharedPreferences
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.timesoft.shoopinglist.entities.NoteItem
import com.timesoft.shoopinglist.fragments.NoteFragment

class NoteAdapter(private val listener: NoteFragment, private val defPref: SharedPreferences) : ListAdapter<NoteItem, ItemHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener, defPref)
    }
}