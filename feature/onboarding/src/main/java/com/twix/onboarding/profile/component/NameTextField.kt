package com.twix.onboarding.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle
import com.twix.onboarding.R
import com.twix.ui.extension.noRippleClickable
import com.twix.designsystem.R as DesR

// TODO : 디자인 시스템 UnderlineTextField로 수정

@Composable
fun NameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                AppText(
                    style = AppTextStyle.T2,
                    color = GrayColor.C200,
                    text = stringResource(id = R.string.onboarding_name_placeholder),
                )
            },
            singleLine = true,
            colors =
                TextFieldDefaults.colors(
                    focusedContainerColor = CommonColor.White,
                    unfocusedContainerColor = CommonColor.White,
                    disabledContainerColor = CommonColor.White,
                    focusedTextColor = GrayColor.C500,
                    unfocusedTextColor = GrayColor.C500,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = GrayColor.C500,
                    focusedPlaceholderColor = GrayColor.C300,
                    unfocusedPlaceholderColor = GrayColor.C300,
                ),
            trailingIcon = {
                Image(
                    imageVector = ImageVector.vectorResource(DesR.drawable.ic_clear_text),
                    contentDescription = null,
                    modifier = Modifier.noRippleClickable { onValueChange("") },
                )
            },
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = GrayColor.C500)
    }
}

@Preview(showBackground = true)
@Composable
private fun NameTextFieldPreview() {
    var text by remember { mutableStateOf("") }
    TwixTheme {
        NameTextField(
            modifier = Modifier,
            value = text,
            onValueChange = { text = it },
        )
    }
}
