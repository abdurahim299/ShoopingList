package com.timesoft.shoopinglist.shop_list_name_adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.timesoft.shoopinglist.R
import com.timesoft.shoopinglist.databinding.ListNameItemBinding
import com.timesoft.shoopinglist.entities.ShopListNameItem

class ShopListNameItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ListNameItemBinding.bind(view)

    fun setData(shopListNameItem: ShopListNameItem, shopListNameListener: ShopListNameListener) =
        with(binding) {
            tvListName.text = shopListNameItem.name
            tvTime.text = shopListNameItem.time
            pBar.max = shopListNameItem.allItemCounter
            pBar.progress = shopListNameItem.checkedItemCounter
            val colorState = ColorStateList.valueOf(getProgressColorState(shopListNameItem, binding.root.context))
            pBar.progressTintList = colorState
            counterCard.backgroundTintList = colorState
            val counterText = "${shopListNameItem.checkedItemCounter}/${shopListNameItem.allItemCounter}"
            tvCounter.text = counterText
            imDelete.setOnClickListener {
                shopListNameListener.deleteItem(shopListNameItem.id!!)
            }
            imEdit.setOnClickListener {
                shopListNameListener.editItem(shopListNameItem)
            }
            itemView.setOnClickListener {
                shopListNameListener.onClickItem(shopListNameItem)
            }
        }

    private fun getProgressColorState(item: ShopListNameItem, context: Context): Int{
        return if (item.checkedItemCounter == item.allItemCounter){
            ContextCompat.getColor(context, R.color.green_main)
        } else{
            ContextCompat.getColor(context, R.color.red_main)
        }
    }

    companion object {
        fun create(parent: ViewGroup): ShopListNameItemHolder {
            return ShopListNameItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_name_item, parent, false)
            )
        }
    }

}