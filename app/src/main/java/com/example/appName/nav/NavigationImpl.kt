package com.example.appName.nav

import android.content.Context
import android.util.Log
import androidx.navigation.NavAction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.appName.MainActivity
import com.example.appName.R
import com.example.base.Navigation
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.android.synthetic.main.activity_main.view.*
import javax.inject.Inject

class NavigationImpl @Inject constructor(
        private val navController: NavController
): Navigation {
    override fun navigate(direction: Navigation.Direction) {
        when(direction) {
            is Navigation.Direction.toDemo -> {
                navController.navigate(R.id.demoFragment)
            }
        }
    }
}
