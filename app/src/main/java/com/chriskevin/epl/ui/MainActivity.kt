package com.chriskevin.epl.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.chriskevin.epl.R
import com.chriskevin.epl.databinding.ActivityMainBinding
import com.chriskevin.epl.util.hide
import com.chriskevin.epl.util.setupWithNavController
import com.chriskevin.epl.util.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_EPL_DayNight)
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(R.navigation.standings, R.navigation.stats, R.navigation.favorite)
        val bottomNav = binding.bottomNavigation

        val controller = bottomNav.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )

        controller.observe(this, { navController ->
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.standingsFragment, R.id.scorersFragment, R.id.favoriteFragment -> bottomNav.show()
                    else -> bottomNav.hide()
                }
            }
        })
    }
}