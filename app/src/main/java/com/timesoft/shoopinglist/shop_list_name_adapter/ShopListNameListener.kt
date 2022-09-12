package com.timesoft.shoopinglist.shop_list_name_adapter

import com.timesoft.shoopinglist.entities.ShopListNameItem

interface ShopListNameListener {

    fun deleteItem(id: Int)

    fun editItem(shoppListName: ShopListNameItem)

    fun onClickItem(shoppListName: ShopListNameItem)

}