package com.example.appName.nav

import androidx.navigation.NavController
import com.example.appName.R
import com.example.base.nav.Navigation
import com.example.home.HomeFragment
import javax.inject.Inject

class NavigationImpl @Inject constructor(
        private val navController: NavController
): Navigation {
    override fun navigate(direction: Navigation.Direction) {
        when(direction) {
            is Navigation.Direction.toHomeScreen -> {
                navController.navigate(R.id.action_login_to_home, HomeFragment.bundle(direction.text))
            }
        }
    }
}
