package com.timesoft.shoopinglist.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.timesoft.shoopinglist.databinding.DeleteDialogBinding
import com.timesoft.shoopinglist.shop_list_name_adapter.ShopListNameListenerDialog

object DeleteDialog {

    fun showDialog(context: Context, listenerDialog: ShopListNameListenerDialog){
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = DeleteDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            bDelete.setOnClickListener {
                listenerDialog.onClick()
                dialog?.dismiss()
            }
            bCancel.setOnClickListener {
                dialog?.dismiss()
            }
        }

        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()

    }
    }