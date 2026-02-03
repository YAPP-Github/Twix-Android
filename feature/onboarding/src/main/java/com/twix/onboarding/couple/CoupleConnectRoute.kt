package com.twix.onboarding.couple

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle
import com.twix.onboarding.R
import com.twix.onboarding.couple.component.ConnectButton
import com.twix.onboarding.couple.component.InvitationButton
import com.twix.onboarding.vm.OnBoardingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoupleConnectRoute(
    onNext: () -> Unit,
    viewModel: OnBoardingViewModel = koinViewModel(),
) {
    CoupleConnectScreen(
        onClickSend = { },
        onClickConnect = { onNext() },
    )
}

@Composable
fun CoupleConnectScreen(
    onClickSend: () -> Unit,
    onClickConnect: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = CommonColor.White)
                .imePadding(),
    ) {
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
        InvitationButton(onClick = onClickSend)

        Spacer(modifier = Modifier.height(20.dp))

        ConnectButton(onClickConnect = onClickConnect)
    }
}

@Preview(showBackground = true)
@Composable
fun CoupleConnectScreenPreview() {
    TwixTheme {
        CoupleConnectScreen(
            onClickConnect = {},
            onClickSend = {},
        )
    }
}
