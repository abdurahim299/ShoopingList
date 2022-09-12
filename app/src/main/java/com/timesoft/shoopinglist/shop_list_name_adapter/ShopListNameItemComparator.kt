package com.timesoft.shoopinglist.shop_list_name_adapter

import androidx.recyclerview.widget.DiffUtil
import com.timesoft.shoopinglist.entities.ShopListNameItem

class ShopListNameItemComparator : DiffUtil.ItemCallback<ShopListNameItem>() {
    override fun areItemsTheSame(oldItem: ShopListNameItem, newItem: ShopListNameItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopListNameItem, newItem: ShopListNameItem): Boolean {
        return oldItem == newItem
    }
}