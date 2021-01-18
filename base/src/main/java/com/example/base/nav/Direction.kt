package com.example.base.nav

import androidx.annotation.StringRes

sealed class Direction {
    data class toHomeScreen(@StringRes val textRes: Int): Direction()
}
