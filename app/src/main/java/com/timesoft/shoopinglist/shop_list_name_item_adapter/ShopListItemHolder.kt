package com.timesoft.shoopinglist.shop_list_name_item_adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.timesoft.shoopinglist.R
import com.timesoft.shoopinglist.databinding.ShopLibraryListItemBinding
import com.timesoft.shoopinglist.databinding.ShopListItemBinding
import com.timesoft.shoopinglist.entities.ShopListItem
import com.timesoft.shoopinglist.shop_list_name_item_adapter.ShopListItemComparator.Companion.ADD
import com.timesoft.shoopinglist.shop_list_name_item_adapter.ShopListItemComparator.Companion.CHECK_BOX
import com.timesoft.shoopinglist.shop_list_name_item_adapter.ShopListItemComparator.Companion.DELETE_LIBRARY_ITEM
import com.timesoft.shoopinglist.shop_list_name_item_adapter.ShopListItemComparator.Companion.EDIT
import com.timesoft.shoopinglist.shop_list_name_item_adapter.ShopListItemComparator.Companion.EDIT_LIBRARY_ITEM

class ShopListItemHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun setItemData(shopListItem: ShopListItem, shopListItemListener: ShopListItemListener) {
        val binding = ShopListItemBinding.bind(view)
        binding.apply {
            tvName.text = shopListItem.name
            tvInfo.text = shopListItem.itemInfo
            tvInfo.visibility = infoVisibility(shopListItem)
            chBox.isChecked = shopListItem.itemChecked
            setPaintFlagAndColor(binding)
            chBox.setOnClickListener {
                shopListItemListener.onClickItem(shopListItem.copy(itemChecked = chBox.isChecked), CHECK_BOX)
            }
            imEdit.setOnClickListener {
                shopListItemListener.onClickItem(shopListItem, EDIT)
            }
        }
    }

    fun setLibraryData(shopListItem: ShopListItem, shopListItemListener: ShopListItemListener) {
        val binding = ShopLibraryListItemBinding.bind(view)
        binding.apply {
            tvName.text = shopListItem.name
            imEdit.setOnClickListener {
                shopListItemListener.onClickItem(shopListItem, EDIT_LIBRARY_ITEM)
            }
            imDelete.setOnClickListener {
                shopListItemListener.onClickItem(shopListItem, DELETE_LIBRARY_ITEM)
            }
            itemView.setOnClickListener {
                shopListItemListener.onClickItem(shopListItem, ADD)
            }
        }
    }

    private fun setPaintFlagAndColor(binding: ShopListItemBinding){
        binding.apply {
            if (chBox.isChecked){
                tvName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                tvInfo.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                tvName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.grey_light))
                tvInfo.setTextColor(ContextCompat.getColor(binding.root.context, R.color.grey_light))
            } else{
                tvName.paintFlags = Paint.ANTI_ALIAS_FLAG
                tvInfo.paintFlags = Paint.ANTI_ALIAS_FLAG
                tvName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                tvInfo.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
            }
        }
    }

    private fun infoVisibility(shopListItem: ShopListItem): Int{
        return if (shopListItem.itemInfo.isEmpty()){
            View.GONE
        }else{
            View.VISIBLE
        }
    }

    companion object {
        fun createShopItem(parent: ViewGroup): ShopListItemHolder {
            return ShopListItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.shop_list_item, parent, false)
            )
        }

        fun createLibraryItem(parent: ViewGroup): ShopListItemHolder {
            return ShopListItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.shop_library_list_item, parent, false)
            )
        }
    }

}