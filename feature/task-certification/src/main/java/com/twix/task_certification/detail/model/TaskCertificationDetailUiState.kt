package com.twix.task_certification.detail.model

import androidx.compose.runtime.Immutable
import com.twix.domain.model.enums.BetweenUs
import com.twix.domain.model.photolog.PhotoLogs
import com.twix.ui.base.State

@Immutable
data class TaskCertificationDetailUiState(
    val goalTitle: String = "",
    val currentShow: BetweenUs = BetweenUs.PARTNER,
    val photoLogs: PhotoLogs = PhotoLogs.EMPTY,
) : State {
    val canModify: Boolean
        get() = currentShow == BetweenUs.ME && photoLogs.isMyCertificated
}
