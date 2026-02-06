package com.twix.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twix.designsystem.components.toast.ToastManager
import com.twix.designsystem.components.toast.model.ToastData
import com.twix.designsystem.components.toast.model.ToastType
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.login.LoginType
import com.twix.login.LoginProviderFactory
import com.twix.login.component.LoginButton
import com.twix.login.model.LoginIntent
import com.twix.login.model.LoginSideEffect
import com.twix.ui.base.ObserveAsEvents
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun LoginRoute(
    navigateToHome: () -> Unit,
    navigateToOnBoarding: () -> Unit,
    toastManager: ToastManager = koinInject(),
    loginProvider: LoginProviderFactory = koinInject(),
    viewModel: LoginViewModel = koinViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()

    val loginFailMessage = stringResource(R.string.login_fail_message)
    ObserveAsEvents(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            LoginSideEffect.NavigateToHome -> navigateToHome()
            LoginSideEffect.NavigateToOnBoarding -> navigateToOnBoarding()
            LoginSideEffect.ShowLoginFailToast -> {
                toastManager.tryShow(ToastData(loginFailMessage, ToastType.ERROR))
            }
        }
    }

    LoginScreen { type ->
        coroutineScope.launch {
            viewModel.dispatch(LoginIntent.Login(loginProvider[type].login()))
        }
    }
}

@Composable
private fun LoginScreen(onClickLogin: (LoginType) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // LoginType.entries.forEach { type ->
            LoginButton(type = LoginType.GOOGLE, onClickLogin = onClickLogin)
            // }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    TwixTheme {
        LoginScreen(onClickLogin = {})
    }
}
