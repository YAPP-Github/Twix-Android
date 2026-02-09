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
fun EmptyGoalGuide(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_empty_goal_home),
            contentDescription = "empty face",
            modifier =
                Modifier
                    .size(width = 181.dp, height = 111.dp),
        )

        Spacer(Modifier.height(21.5.dp))

        AppText(
            text = stringResource(R.string.home_empty_goal_guide),
            style = AppTextStyle.T2,
            color = GrayColor.C400,
        )

        Spacer(Modifier.height(5.dp))

        AppText(
            text = stringResource(R.string.home_empty_goal_content),
            style = AppTextStyle.C1,
            color = GrayColor.C300,
        )

        Spacer(Modifier.height(50.dp))

        Image(
            painter = painterResource(R.drawable.ic_empty_goal_arrow),
            contentDescription = "empty goal arrow",
            modifier =
                Modifier
                    .offset(x = 60.dp),
        )
    }
}
