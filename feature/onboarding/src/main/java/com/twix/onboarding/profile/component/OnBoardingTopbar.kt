package com.twix.onboarding.profile.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.twix.ui.extension.noRippleClickable
import com.twix.designsystem.R as DesR

@Composable
fun OnBoardingTopbar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(72.dp),
    ) {
        Icon(
            painter = painterResource(id = DesR.drawable.ic_arrow1_m_left),
            contentDescription = "back",
            modifier =
                Modifier
                    .size(44.dp)
                    .padding(start = 10.dp)
                    .noRippleClickable { onBack() }
                    .align(Alignment.CenterStart),
        )
    }
}
