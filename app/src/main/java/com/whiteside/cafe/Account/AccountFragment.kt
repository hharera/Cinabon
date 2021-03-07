package com.whiteside.cafe.Account

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.whiteside.cafe.CafeLocation
import com.whiteside.cafe.R

class AccountFragment : Fragment() {
    private var linear_layout_map: FrameLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_account, container, false)
        val inflater1 = TransitionInflater.from(context)
        exitTransition = inflater1.inflateTransition(R.transition.fragment_in)
        enterTransition = inflater1.inflateTransition(R.transition.fragment_out)
        linear_layout_map = root.findViewById(R.id.linear_layout_map)
        setListeners()
        return root
    }

    private fun setListeners() {
        setMapListener()
    }

    private fun setMapListener() {
        linear_layout_map.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, CafeLocation::class.java)
            startActivity(intent)
        })
    }
}