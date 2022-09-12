package com.timesoft.shoopinglist.activities

import android.app.Application
import com.timesoft.shoopinglist.db.MainDatabase

class MainApp : Application() {

    val database by lazy {
        MainDatabase.getDataBase(this)
    }

}