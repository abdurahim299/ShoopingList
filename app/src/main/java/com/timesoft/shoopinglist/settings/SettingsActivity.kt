package com.timesoft.shoopinglist.settings

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.timesoft.shoopinglist.R

class SettingsActivity : AppCompatActivity() {
    private lateinit var defPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.placeHolder, SettingsFragment()).commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getSelectedTheme(): Int{
        return if (defPref.getString("theme_key", "blue") == "blue"){
            R.style.Theme_ShoopingList
        }
        else {
            R.style.Theme_ShoppingListNewBlack
        }
    }

}