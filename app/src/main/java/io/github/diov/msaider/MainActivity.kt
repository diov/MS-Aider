package io.github.diov.msaider

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MSAider
 *
 * Created by Dio_V on 2019-03-23.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                showDialog()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun showDialog() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = """line://msg/text/戰友募集中！
讓我們一起並肩作戰，齊心協力突破難關！
「暗動的生命危機迷宮(美顏！福笑怪物)」
https://static.tw.monster-strike.com/sns/?pass_code=MjQzMTgyMjU1&f=line
↑點擊這個網址馬上一起連線作戰！""".trimIndent().toUri()
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
