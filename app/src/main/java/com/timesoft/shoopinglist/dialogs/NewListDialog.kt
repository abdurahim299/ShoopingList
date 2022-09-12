package com.timesoft.shoopinglist.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.timesoft.shoopinglist.R
import com.timesoft.shoopinglist.databinding.NewListDialogBinding
import com.timesoft.shoopinglist.db.ListenerDialog

object NewListDialog {

    fun showDialog(context: Context, listenerDialog: ListenerDialog, name: String) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = NewListDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            edNewListName.setText(name)
            if (name.isNotEmpty()) bCreate.text = context.getString(R.string.update)
            bCreate.setOnClickListener {
                val listName = edNewListName.text.toString()
                if (listName.isNotEmpty()) {
                    listenerDialog.onClick(listName)
                }
                dialog?.dismiss()
            }
        }

        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()

    }

}