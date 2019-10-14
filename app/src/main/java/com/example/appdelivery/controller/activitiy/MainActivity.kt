package com.example.appdelivery.controller.activitiy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appdelivery.R
import com.example.appdelivery.controller.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

}
