package com.whiteside.cafe.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.whiteside.cafe.CafeLocation
import com.whiteside.cafe.R
import com.whiteside.cafe.api.repository.AuthManager
import com.whiteside.cafe.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class AccountFragment : Fragment() {

    @Inject
    lateinit var authManager: AuthManager

    lateinit var bind: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = FragmentAccountBinding.inflate(layoutInflater)

        if (authManager.getCurrentUser()!!.isAnonymous) {
            findNavController().navigate(R.id.navigation_unsigned_account)
//            onStop()
        }

//        val inflater1 = TransitionInflater.from(context)
//        exitTransition = inflater1.inflateTransition(R.transition.fragment_in)
//        enterTransition = inflater1.inflateTransition(R.transition.fragment_out)

        setupMapListener()
        return bind.root
    }

    private fun setupMapListener() {
        bind.openInMap.setOnClickListener {
            val intent = Intent(context, CafeLocation::class.java)
            startActivity(intent)
        }
    }
}