package com.peto.onboarding.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peto.onboarding.R
import com.peto.onboarding.model.OnBoardingIntent
import com.peto.onboarding.profile.component.NameTextField
import com.peto.onboarding.profile.component.OnBoardingTopbar
import com.peto.onboarding.vm.OnBoardingViewModel
import com.twix.designsystem.components.button.AppButton
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.SystemColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle
import org.koin.androidx.compose.koinViewModel
import com.twix.designsystem.R as DesR

@Composable
fun ProfileRoute(
    onBack: () -> Unit,
    onCompleted: () -> Unit,
    viewModel: OnBoardingViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreen(
        uiModel = uiState.profile,
        onBack = onBack,
        onCompleted = onCompleted,
        onChangeNickName = { viewModel.dispatch(OnBoardingIntent.WriteNickName(it)) },
    )
}

@Composable
private fun ProfileScreen(
    uiModel: ProfileUiModel,
    onBack: () -> Unit,
    onCompleted: () -> Unit,
    onChangeNickName: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = CommonColor.White)
                .imePadding(),
    ) {
        OnBoardingTopbar { onBack() }

        Spacer(modifier = Modifier.height(8.dp))

        AppText(
            text = stringResource(R.string.onboarding_profile_title),
            style = AppTextStyle.H3,
            color = GrayColor.C500,
            modifier = Modifier.padding(start = 24.dp),
        )

        Spacer(modifier = Modifier.height(32.dp))

        NameTextField(
            value = uiModel.nickname,
            onValueChange = { onChangeNickName(it) },
            modifier =
                Modifier
                    .focusRequester(focusRequester)
                    .padding(horizontal = 20.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(DesR.drawable.ic_check_success),
                contentDescription = null,
                tint = if (uiModel.isValid) SystemColor.Success else GrayColor.C300,
            )

            Spacer(modifier = Modifier.width(4.dp))

            AppText(
                style = AppTextStyle.C2,
                color = if (uiModel.isValid) SystemColor.Success else GrayColor.C300,
                text = stringResource(id = R.string.onboarding_name_helper),
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        AppButton(
            text = stringResource(R.string.onboarding_profile_button_title),
            onClick = { onCompleted() },
            enabled = uiModel.isValid,
            backgroundColor = if (uiModel.isValid) GrayColor.C500 else GrayColor.C100,
            textColor = if (uiModel.isValid) CommonColor.White else GrayColor.C300,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UnValidProfileScreenPreview() {
    TwixTheme {
        Row {
            ProfileScreen(
                onBack = {},
                onCompleted = {},
                onChangeNickName = {},
                uiModel =
                    ProfileUiModel(
                        nickname = "",
                        isValid = false,
                    ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ValidProfileScreenPreview() {
    TwixTheme {
        ProfileScreen(
            onBack = {},
            onCompleted = {},
            onChangeNickName = {},
            uiModel =
                ProfileUiModel(
                    nickname = "",
                    isValid = true,
                ),
        )
    }
}
