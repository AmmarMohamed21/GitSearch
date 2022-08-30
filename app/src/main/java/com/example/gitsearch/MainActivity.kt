package com.example.gitsearch

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gitsearch.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun isInternetConnected():Boolean {
        val connectionManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiConnection:Boolean = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isConnected == true
        val mobileDataConnection:Boolean = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.isConnected == true

        return wifiConnection || mobileDataConnection
    }
}