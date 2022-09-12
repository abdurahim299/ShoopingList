package com.timesoft.shoopinglist.fragments

import androidx.appcompat.app.AppCompatActivity
import com.timesoft.shoopinglist.R

object FragmentManager {

    var currentFragment: BaseFragment? = null

    fun setFragment(newFragment: BaseFragment, activity: AppCompatActivity){
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeHolder, newFragment)
        transaction.commit()
        currentFragment = newFragment
    }


}