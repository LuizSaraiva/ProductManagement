package com.project.productmanagement

import android.app.Application

class ApplicationApp : Application() {

    var helper: DBHelper? = null
        private set

    companion object {
        lateinit var instance: ApplicationApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        helper = DBHelper(this)
    }

}