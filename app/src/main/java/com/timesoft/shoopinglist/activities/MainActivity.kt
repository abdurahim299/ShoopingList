package com.timesoft.shoopinglist.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.timesoft.shoopinglist.R
import com.timesoft.shoopinglist.billing.BillingManager
import com.timesoft.shoopinglist.databinding.ActivityMainBinding
import com.timesoft.shoopinglist.fragments.FragmentManager
import com.timesoft.shoopinglist.fragments.NoteFragment
import com.timesoft.shoopinglist.fragments.ShoppingListNamesFragment
import com.timesoft.shoopinglist.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentMenuItemId = R.id.shop_list
    private var currentTheme = ""
    private lateinit var defPref: SharedPreferences
    private var iAd: InterstitialAd? = null
    private var adShowCounter = 0
    private var adShowCounterMax = 3
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(getSelectedTheme())
        setBottomNavListener()
    }

    private fun setBottomNavListener(){
        binding.bNav.setOnItemSelectedListener {
            when (it.itemId){
                R.id.settings ->{
                    startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                }
                R.id.notes ->{
                    FragmentManager.setFragment(NoteFragment.newInstance(), this@MainActivity)
                }
                R.id.shop_list -> {
                    FragmentManager.setFragment(ShoppingListNamesFragment.newInstance(),this@MainActivity)
                }
                R.id.new_item -> {
                    FragmentManager.currentFragment?.onClickNew()
                }
            }
            true
        }
    }

    private fun loadInterAd(){
        val request = AdRequest.Builder().build()
        InterstitialAd.load(this, getString(R.string.inter_ad_id), request,
            object : InterstitialAdLoadCallback(){
                override fun onAdLoaded(ad: InterstitialAd) {
                    iAd = ad
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    iAd = null
                }
            })
    }

    private fun showInterAd(adListener: AdListener){
        if (iAd != null && adShowCounter > adShowCounterMax && !pref.getBoolean(BillingManager.REMOVE_ADS_KEY, false)){
            iAd?.fullScreenContentCallback = object : FullScreenContentCallback(){
                override fun onAdDismissedFullScreenContent() {
                    iAd = null
                    loadInterAd()
                    adListener.onFinish()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    iAd = null
                    loadInterAd()
                }

                override fun onAdShowedFullScreenContent() {
                    iAd = null
                    loadInterAd()
                }
            }
            adShowCounter = 0
            iAd?.show(this)
        } else{
            adShowCounter++
            adListener.onFinish()
        }
    }

    private fun getSelectedTheme(): Int{
        return if (defPref.getString("theme_key", "blue") == "blue"){
            R.style.Theme_ShoppingListNewBlue
        }
        else {
            R.style.Theme_ShoppingListNewBlack
        }
    }

    interface AdListener{
        fun onFinish()
    }

}