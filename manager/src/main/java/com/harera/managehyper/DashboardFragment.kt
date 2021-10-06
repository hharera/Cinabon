package com.harera.managehyper

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.harera.common.base.BaseFragment
import com.harera.common.utils.navigation.Arguments
import com.harera.common.utils.navigation.Destinations
import com.harera.common.utils.navigation.NavigationUtils
import com.harera.managehyper.databinding.FragmentDashboardBinding

class DashboardFragment : BaseFragment() {

    private lateinit var bind: FragmentDashboardBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentDashboardBinding.inflate(inflater)
        navController = findNavController()
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        bind.addCategory.setOnClickListener {
            navController.navigate(R.id.navigation_addCategory)
        }

        bind.showProducts.setOnClickListener {
            navController.navigate(Uri.parse(NavigationUtils.getUriNavigation(Arguments.HYPER_PANDA_DOMAIN, Destinations.CATEGORIES, null)))
        }

        bind.addProduct.setOnClickListener {

        }

        bind.addDeliveryMan.setOnClickListener {

        }

        bind.addOffer.setOnClickListener {

        }

        bind.addOfferCollection.setOnClickListener {

        }

        bind.orders.setOnClickListener {

        }
    }
}