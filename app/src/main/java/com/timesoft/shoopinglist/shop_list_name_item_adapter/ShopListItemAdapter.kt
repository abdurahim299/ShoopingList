package com.timesoft.shoopinglist.shop_list_name_item_adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.timesoft.shoopinglist.activities.ShoppingListActivity
import com.timesoft.shoopinglist.entities.ShopListItem

class ShopListItemAdapter(private val shopListItemListener: ShoppingListActivity) :
    ListAdapter<ShopListItem, ShopListItemHolder>(
        ShopListItemComparator()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListItemHolder {
        return if (viewType == 0)
            ShopListItemHolder.createShopItem(parent)
        else ShopListItemHolder.createLibraryItem(parent)
    }

    override fun onBindViewHolder(holder: ShopListItemHolder, position: Int) {
        if (getItem(position).itemType == 0){
            holder.setItemData(getItem(position), shopListItemListener)
        } else{
            holder.setLibraryData(getItem(position), shopListItemListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }

}