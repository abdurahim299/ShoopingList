package com.timesoft.shoopinglist.shop_list_name_item_adapter

import com.timesoft.shoopinglist.entities.ShopListItem

interface ShopListItemListener {

    fun onClickItem(shoppListItem: ShopListItem, state: Int)

}