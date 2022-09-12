package com.timesoft.shoopinglist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timesoft.shoopinglist.R
import com.timesoft.shoopinglist.databinding.FragmentShoppingListNamesBinding

class ShoppingListNamesFragment : Fragment() {
    private lateinit var binding: FragmentShoppingListNamesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListNamesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShoppingListNamesFragment()
    }
}