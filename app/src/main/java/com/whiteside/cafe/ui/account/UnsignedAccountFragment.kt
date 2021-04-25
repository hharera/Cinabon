package com.whiteside.cafe.ui.account

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.whiteside.cafe.R
import com.whiteside.cafe.api.repository.AuthManager
import com.whiteside.cafe.databinding.FragmentUnsignedAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class UnsignedAccountFragment : Fragment() {

    @Inject
    lateinit var authManager: AuthManager

    lateinit var bind: ViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = FragmentUnsignedAccountBinding.inflate(layoutInflater)

        val inflater1 = TransitionInflater.from(context)
        exitTransition = inflater1.inflateTransition(R.transition.fragment_in)
        enterTransition = inflater1.inflateTransition(R.transition.fragment_out)
        return bind.root
    }
}