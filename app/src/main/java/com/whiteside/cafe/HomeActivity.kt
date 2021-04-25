package com.whiteside.cafe

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.*
import com.mancj.materialsearchbar.SimpleOnSearchActionListener
import com.whiteside.cafe.common.BaseActivity
import com.whiteside.cafe.databinding.ActivityHomeBinding
import com.whiteside.cafe.ui.search.SearchResults
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bind: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        setSupportActionBar(bind.toolBar)
        setupSearchListener()

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_shop,
                R.id.navigation_categories,
                R.id.navigation_account,
                R.id.navigation_cart,
                R.id.navigation_wishlist,
                R.id.navigation_unsigned_account,
                R.id.navigation_search
            )
        )

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            setUpWithDrawer()
        else
            setUpWithToolBar()

        setUpWithBottomNav()
        setUpWithSideNav()
    }

    private fun setUpWithDrawer() {
//        val appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setUpWithBottomNav() {
        bind.bottomNav?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    private fun setUpWithSideNav() {
        bind.navView?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    private fun setUpWithToolBar() {
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.tool_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun setupSearchListener() {
        bind.searchView!!.setOnSearchActionListener(object : SimpleOnSearchActionListener() {
            override fun onSearchConfirmed(text: CharSequence) {
                super.onSearchConfirmed(text)
                val intent = Intent(this@HomeActivity, SearchResults::class.java)
                intent.putExtra("text", text.toString())
                startActivity(intent)
            }
        })
    }
}