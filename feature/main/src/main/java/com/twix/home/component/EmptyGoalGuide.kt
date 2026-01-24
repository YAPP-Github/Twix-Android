package com.twix.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twix.designsystem.R
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.theme.GrayColor
import com.twix.domain.model.enums.AppTextStyle

@Composable
fun EmptyGoalGuide(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_empty_face),
            contentDescription = "empty face",
            modifier = Modifier
                .padding(horizontal = 9.dp, vertical = 6.dp)
                .size(width = 34.dp, height = 40.dp)

        )

        Spacer(Modifier.height(10.dp))

        AppText(
            text = stringResource(R.string.homt_empty_goal_guide),
            style = AppTextStyle.T2,
            color = GrayColor.C200
        )

        Spacer(Modifier.height(12.dp))

        Image(
            painter = painterResource(R.drawable.ic_empty_goal_arrow),
            contentDescription = "empty goal arrow",
            modifier = Modifier
                .offset(x = 32.dp)
        )
    }
}
