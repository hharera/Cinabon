package com.whiteside.cafe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity

class StartingAppPage : AppCompatActivity() {
    lateinit var viewFlipper: ViewFlipper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_view)
        viewFlipper = findViewById(R.id.view_flipper)
        viewFlipper.isAutoStart = true
        viewFlipper.startFlipping()
        hide()
    }

    private fun hide() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }

    fun startClicked(view: View?) {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}