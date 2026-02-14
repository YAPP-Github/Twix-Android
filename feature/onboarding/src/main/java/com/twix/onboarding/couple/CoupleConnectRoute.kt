package com.twix.onboarding.couple

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twix.designsystem.components.bottomsheet.CommonBottomSheet
import com.twix.designsystem.components.bottomsheet.model.CommonBottomSheetConfig
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.components.toast.ToastManager
import com.twix.designsystem.components.toast.model.ToastData
import com.twix.designsystem.components.toast.model.ToastType
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle
import com.twix.onboarding.R
import com.twix.onboarding.couple.component.ConnectButton
import com.twix.onboarding.couple.component.RestoreCoupleBottomSheetContent
import com.twix.onboarding.model.OnBoardingSideEffect
import com.twix.onboarding.vm.OnBoardingViewModel
import com.twix.ui.base.ObserveAsEvents
import com.twix.ui.extension.noRippleClickable
import org.koin.compose.koinInject

@Composable
fun CoupleConnectRoute(
    viewModel: OnBoardingViewModel,
    toastManager: ToastManager = koinInject(),
    navigateToNext: () -> Unit,
) {
    var showRestoreSheet by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchMyInviteCode()
    }

    val fetchMyInviteCodeFailMessage =
        stringResource(R.string.onboarding_couple_fetch_my_invite_code_fail)
    ObserveAsEvents(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            OnBoardingSideEffect.CoupleConnection.ShowFetchMyInviteCodeFailToast -> {
                toastManager.tryShow(
                    ToastData(
                        message = fetchMyInviteCodeFailMessage,
                        type = ToastType.ERROR,
                    ),
                )
            }

            else -> Unit
        }
    }

    CoupleConnectScreen(
        showRestoreSheet = showRestoreSheet,
        onClickSend = { },
        onClickConnect = navigateToNext,
        onClickRestore = { showRestoreSheet = true },
        onDismissSheet = { showRestoreSheet = false },
    )
}

@Composable
fun CoupleConnectScreen(
    showRestoreSheet: Boolean,
    onClickSend: () -> Unit,
    onClickConnect: () -> Unit,
    onClickRestore: () -> Unit,
    onDismissSheet: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = CommonColor.White),
    ) {
        Column {
            Spacer(modifier = Modifier.height(80.24.dp))

            AppText(
                text = stringResource(R.string.onboarding_couple_connect_description),
                style = AppTextStyle.H3,
                color = GrayColor.C500,
                modifier = Modifier.padding(start = 24.dp),
            )

            Spacer(modifier = Modifier.height(11.76.dp))

            Image(
                imageVector = ImageVector.vectorResource(R.drawable.img_couple_connect),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.height(47.dp))
            // InvitationButton(onClick = onClickSend)

            Spacer(modifier = Modifier.height(20.dp))

            ConnectButton(onClickConnect = onClickConnect)

            Spacer(modifier = Modifier.height(20.dp))

            AppText(
                text = stringResource(R.string.onboarding_couple_restore),
                style = AppTextStyle.B1,
                color = GrayColor.C400,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .noRippleClickable(onClick = onClickRestore),
            )
        }

        CommonBottomSheet(
            visible = showRestoreSheet,
            config = CommonBottomSheetConfig(showHandle = false),
            onDismissRequest = onDismissSheet,
            content = { RestoreCoupleBottomSheetContent() },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CoupleConnectScreenPreview() {
    TwixTheme {
        CoupleConnectScreen(
            showRestoreSheet = false,
            onClickSend = {},
            onClickConnect = {},
            onClickRestore = {},
            onDismissSheet = {},
        )
    }
}
