package com.twix.home

import com.twix.ui.base.SideEffect

interface HomeSideEffect : SideEffect {
    data object ShowMonthPickerBottomSheet : HomeSideEffect
}
