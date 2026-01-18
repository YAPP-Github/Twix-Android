package com.twix.main.model

import com.twix.designsystem.R


enum class MainTab(
    val selectedIcon: Int,
    val unselectedIcon: Int,
) {
    HOME(
        selectedIcon = R.drawable.ic_home_selected,
        unselectedIcon = R.drawable.ic_home_unselected
    ),
    STATS(
        selectedIcon = R.drawable.ic_stats_selected,
        unselectedIcon = R.drawable.ic_stats_unselected
    ),
    COUPLE(
        selectedIcon = R.drawable.ic_couple_selected,
        unselectedIcon = R.drawable.ic_couple_unselected
    )
}