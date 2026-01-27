package com.twix.ui.toast.model

import androidx.compose.runtime.Immutable

@Immutable
data class ToastAction(
    val label: String,
    val onClick: () -> Unit,
)
