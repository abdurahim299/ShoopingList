package com.timesoft.shoopinglist.shop_list_name_item_adapter

import androidx.recyclerview.widget.DiffUtil
import com.timesoft.shoopinglist.entities.ShopListItem

class ShopListItemComparator : DiffUtil.ItemCallback<ShopListItem>() {
    override fun areItemsTheSame(oldItem: ShopListItem, newItem: ShopListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopListItem, newItem: ShopListItem): Boolean {
        return oldItem == newItem
    }

    companion object{
        const val EDIT = 0
        const val CHECK_BOX = 1
        const val EDIT_LIBRARY_ITEM = 2
        const val DELETE_LIBRARY_ITEM = 3
        const val ADD = 4
    }

}