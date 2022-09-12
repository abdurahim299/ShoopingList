package com.timesoft.shoopinglist.shop_list_name_adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.timesoft.shoopinglist.entities.ShopListNameItem
import com.timesoft.shoopinglist.fragments.ShoppingListNamesFragment

class ShopListNameAdapter(private val shopListNameListener: ShoppingListNamesFragment) :
    ListAdapter<ShopListNameItem, ShopListNameItemHolder>(
        ShopListNameItemComparator()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListNameItemHolder {
        return ShopListNameItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ShopListNameItemHolder, position: Int) {
        holder.setData(getItem(position), shopListNameListener)
    }
}