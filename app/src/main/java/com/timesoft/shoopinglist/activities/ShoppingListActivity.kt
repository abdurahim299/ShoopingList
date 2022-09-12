package com.timesoft.shoopinglist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.timesoft.shoopinglist.R
import com.timesoft.shoopinglist.databinding.ActivityShoppingListBinding
import com.timesoft.shoopinglist.db.MainViewModel
import com.timesoft.shoopinglist.db.MainViewModelFactory
import com.timesoft.shoopinglist.dialogs.EditListItemDialog
import com.timesoft.shoopinglist.entities.LibraryItem
import com.timesoft.shoopinglist.entities.ShopListItem
import com.timesoft.shoopinglist.entities.ShopListNameItem
import com.timesoft.shoopinglist.shop_list_name_item_adapter.ShopListItemAdapter
import com.timesoft.shoopinglist.shop_list_name_item_adapter.ShopListItemComparator
import com.timesoft.shoopinglist.utils.ShareHelper

class ShoppingListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingListBinding
    private var shopListNameItem: ShopListNameItem? = null
    private lateinit var saveItem: MenuItem
    private var edItem: EditText? = null
    private var adapter: ShopListItemAdapter? = null
    private lateinit var textWatcher: TextWatcher

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        listItemObserver()
        initRcView()
    }

    private fun init(){
        shopListNameItem = intent.getSerializableExtra(SHOP_LIST_NAME) as ShopListNameItem
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.shop_list_menu, menu)
        saveItem = menu.findItem(R.id.save_item)!!
        val newItem = menu.findItem(R.id.new_item)
        edItem = newItem.actionView.findViewById(R.id.edNewShopItem) as EditText
        newItem.setOnActionExpandListener(expandActionView())
        saveItem.isVisible = false
        textWatcher = textWatcher()
        return true
    }

    private fun textWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                mainViewModel.getAllLibraryItems("%$s%")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_item -> {
                addNewShopItem(edItem?.text.toString())
            }
            R.id.delete_list -> {
                mainViewModel.deleteShopList(shopListNameItem?.id!!, true)
                finish()
            }
            R.id.clear_list -> {
                mainViewModel.deleteShopList(shopListNameItem?.id!!, false)
            }
            R.id.share_list -> {
                startActivity(
                    Intent.createChooser(
                    ShareHelper.shareShopList(adapter?.currentList!!, shopListNameItem?.name!!),
                    "Share by"
                ))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addNewShopItem(name: String){
        if (name.isEmpty()) return
        val item = ShopListItem(
            null,
            name,
            "",
            false,
            shopListNameItem?.id!!,
            0
        )
        edItem?.setText("")
        mainViewModel.insertShopItem(item)
    }

    private fun listItemObserver(){
        mainViewModel.getAllItemsFromList(shopListNameItem?.id!!).observe(this) {
            adapter?.submitList(it)
            binding.tvEmpty.visibility = if (it.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun libraryItemObserver(){
        mainViewModel.libraryItems.observe(this) {
            val tempShopList = ArrayList<ShopListItem>()
            it.forEach { item ->
                val shopItem = ShopListItem(
                    item.id,
                    item.name,
                    "",
                    false,
                    0,
                    1
                )
                tempShopList.add(shopItem)
            }
            adapter?.submitList(tempShopList)
            binding.tvEmpty.visibility = if (it.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun initRcView() = with(binding){
        adapter = ShopListItemAdapter(this@ShoppingListActivity)
        rcView.layoutManager = LinearLayoutManager(this@ShoppingListActivity)
        rcView.adapter = adapter
    }

    private fun expandActionView(): MenuItem.OnActionExpandListener{
        return object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                saveItem.isVisible = true
                edItem?.addTextChangedListener(textWatcher)
                libraryItemObserver()
                mainViewModel.getAllItemsFromList(shopListNameItem?.id!!).removeObservers(this@ShoppingListActivity)
                mainViewModel.getAllLibraryItems("%%")
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                saveItem.isVisible = false
                edItem?.removeTextChangedListener(textWatcher)
                invalidateOptionsMenu()
                mainViewModel.libraryItems.removeObservers(this@ShoppingListActivity)
                edItem?.setText("")
                listItemObserver()
                return true
            }

        }
    }

    companion object{
        const val SHOP_LIST_NAME = "shop_list_name"
    }

    override fun onClickItem(shoppListItem: ShopListItem, state: Int){
        when (state){
            ShopListItemComparator.CHECK_BOX -> mainViewModel.updateListItem(shoppListItem)
            ShopListItemComparator.EDIT ->{
                editListItem(shoppListItem)
            }
            ShopListItemComparator.EDIT_LIBRARY_ITEM ->{
                editLibraryItem(shoppListItem)
            }
            ShopListItemComparator.ADD ->{
                addNewShopItem(shoppListItem.name)
            }
            ShopListItemComparator.DELETE_LIBRARY_ITEM ->{
                mainViewModel.deleteLibraryItem(shoppListItem.id!!)
                mainViewModel.getAllLibraryItems("%${edItem?.text.toString()}%")
            }
        }

    }

    private fun editListItem(item: ShopListItem){
        EditListItemDialog.showDialog(this, item, object : EditListItemDialog.ShopListItemListenerDialog{
            override fun onClick(item: ShopListItem) {
                mainViewModel.updateListItem(item)
            }

        })
    }

    private fun editLibraryItem(item: ShopListItem){
        EditListItemDialog.showDialog(this, item, object : EditListItemDialog.ShopListItemListenerDialog{
            override fun onClick(item: ShopListItem) {
                mainViewModel.updateLibraryItem(LibraryItem(item.id, item.name, ""))
                mainViewModel.getAllLibraryItems("%${edItem?.text.toString()}%")
            }
        })
    }

    private fun saveItemCount(){
        var checkedItemCounter = 0
        adapter?.currentList?.forEach {
            if (it.itemChecked) checkedItemCounter++
        }
        val tempShopListNameItem = shopListNameItem?.copy(
            allItemCounter = adapter?.itemCount!!,
            checkedItemCounter = checkedItemCounter
        )
        mainViewModel.updateListName(tempShopListNameItem!!)
    }

    override fun onBackPressed() {
        saveItemCount()
        super.onBackPressed()
    }
}