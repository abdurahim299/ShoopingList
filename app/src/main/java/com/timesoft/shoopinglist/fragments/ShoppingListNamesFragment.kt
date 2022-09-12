package com.timesoft.shoopinglist.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.timesoft.shoopinglist.activities.MainApp
import com.timesoft.shoopinglist.activities.ShoppingListActivity
import com.timesoft.shoopinglist.databinding.FragmentShoppingListNamesBinding
import com.timesoft.shoopinglist.db.ListenerDialog
import com.timesoft.shoopinglist.db.MainViewModel
import com.timesoft.shoopinglist.db.MainViewModelFactory
import com.timesoft.shoopinglist.dialogs.DeleteDialog
import com.timesoft.shoopinglist.dialogs.NewListDialog
import com.timesoft.shoopinglist.entities.ShopListNameItem
import com.timesoft.shoopinglist.shop_list_name_adapter.ShopListNameAdapter
import com.timesoft.shoopinglist.shop_list_name_adapter.ShopListNameListenerDialog
import com.timesoft.shoopinglist.utils.TimeManager

class ShoppingListNamesFragment : BaseFragment() {
    private lateinit var binding: FragmentShoppingListNamesBinding
    private lateinit var adapter: ShopListNameAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew() {
        NewListDialog.showDialog(activity as AppCompatActivity, object : ListenerDialog {
            override fun onClick(name: String) {
                val shoppListName = ShopListNameItem(
                    null,
                    name,
                    TimeManager.getCurrentTime(),
                    0,
                    0,
                    ""
                )
                mainViewModel.insertShopListName(shoppListName)
            }
        }, "")
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListNamesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = ShopListNameAdapter(this@ShoppingListNamesFragment)
        rcView.adapter = adapter
    }

    private fun observer() {
        mainViewModel.allShopListNames.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShoppingListNamesFragment()
    }

    override fun deleteItem(id: Int) {
        DeleteDialog.showDialog(context as AppCompatActivity, object : ShopListNameListenerDialog {
            override fun onClick() {
                mainViewModel.deleteShopList(id, true)
            }

        })
    }

    override fun editItem(shoppListName: ShopListNameItem) {
        NewListDialog.showDialog(activity as AppCompatActivity, object : ListenerDialog {
            override fun onClick(name: String) {
                mainViewModel.updateListName(shoppListName.copy(name = name))
            }
        }, shoppListName.name)
    }

    override fun onClickItem(shoppListName: ShopListNameItem) {
        val intent = Intent(activity, ShoppingListActivity::class.java).apply {
            putExtra(ShoppingListActivity.SHOP_LIST_NAME, shoppListName)
        }
        startActivity(intent)
    }
}