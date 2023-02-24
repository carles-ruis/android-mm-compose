package com.carles.common.ui.component

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.carles.common.Navigate

interface HasToolbar {

    fun initDefaultToolbar(toolbar: Toolbar, activity: AppCompatActivity, navigate: Navigate) {
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            navigate.up()
        }
    }
}