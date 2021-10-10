package com.harera.account

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.harera.account.databinding.FragmentAccountBinding
import com.harera.common.base.BaseFragment
import com.harera.common.utils.navigation.Arguments
import com.harera.common.utils.navigation.Destinations
import com.harera.common.utils.navigation.NavigationUtils
import com.harera.market_location.MarketLocation
import com.opensooq.supernova.gligar.GligarPicker

class AccountFragment : BaseFragment() {
    private val IMAGE_REQ_CODE = 3004

    private lateinit var bind: FragmentAccountBinding
    private lateinit var navController: NavController
    private val accountViewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = FragmentAccountBinding.inflate(layoutInflater)
        navController = findNavController()

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
        accountViewModel.checkUser()
    }

    private fun setupObservers() {
        accountViewModel.userIsAnonymous.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        accountViewModel.loading.observe(viewLifecycleOwner) { state ->
            if (state) {
                handleLoading(state)
            }
        }

        accountViewModel.exception.observe(viewLifecycleOwner) { exception ->
            handleError(exception)
        }
    }

    private fun updateUI(loggedInAnonymously: Boolean) {
        if (loggedInAnonymously) {
            bind.login.visibility = View.VISIBLE
            bind.phoneNumberLayout.visibility = View.VISIBLE
        } else {
            bind.orders.visibility = View.VISIBLE
        }
    }

    private fun setupListeners() {
        bind.openInMap.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    MarketLocation::class.java
                )
            )
//            navController.navigate(
//                Uri.parse(
//                    NavigationUtils.getUriNavigation(
//                        Arguments.HYPER_PANDA_DOMAIN,
//                        Destinations.MAP,
//                        null
//                    )
//                )
//            )
        }

        bind.cart.setOnClickListener {
            navController.navigate(
                Uri.parse(
                    NavigationUtils.getUriNavigation(
                        Arguments.HYPER_PANDA_DOMAIN,
                        Destinations.CART,
                        null
                    )
                )
            )
        }

        bind.wishlist.setOnClickListener {
            navController.navigate(
                Uri.parse(
                    NavigationUtils.getUriNavigation(
                        Arguments.HYPER_PANDA_DOMAIN,
                        Destinations.WISH_LIST,
                        null
                    )
                )
            )
        }

        bind.orders.setOnClickListener {
            navController.navigate(
                Uri.parse(
                    NavigationUtils.getUriNavigation(
                        Arguments.HYPER_PANDA_DOMAIN,
                        Destinations.ORDERS,
                        null
                    )
                )
            )
        }

        bind.profileImage.setOnClickListener {
            GligarPicker()
                .requestCode(IMAGE_REQ_CODE)
                .limit(1)
                .withFragment(this)
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQ_CODE) {
            val imageBitmap = BitmapFactory.decodeFile(data.data!!.path)
            imageBitmap?.let {
                accountViewModel.setProfileImage(imageBitmap)
            }
        }
    }
}