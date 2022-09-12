package com.timesoft.shoopinglist.db

import com.timesoft.shoopinglist.entities.NoteItem

interface Listener {

    fun deleteItem(id: Int)

    fun onClickItem(noteItem: NoteItem)

}