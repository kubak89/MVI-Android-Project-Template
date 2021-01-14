package com.example.appName.nav

import androidx.navigation.NavController
import com.example.appName.R
import com.example.base.Navigation
import javax.inject.Inject

class NavigationImpl @Inject constructor(
        private val navController: NavController
): Navigation {
    override fun navigate(direction: Navigation.Direction) {
        when(direction) {
            is Navigation.Direction.toDemo -> {
                navController.navigate(R.id.action_login_to_demo)
            }
        }
    }
}
