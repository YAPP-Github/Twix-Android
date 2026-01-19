package com.twix.designsystem.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import com.twix.designsystem.theme.LocalAppTypography
import com.twix.designsystem.theme.toTextStyle
import com.twix.domain.model.enums.AppTextStyle

@Composable
fun AppText(
    modifier: Modifier = Modifier,
    text: String,
    style: AppTextStyle,
    color: Color,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    val typo = LocalAppTypography.current
    val baseStyle = style.toTextStyle(typo)

    CompositionLocalProvider(LocalDensity provides Density(LocalDensity.current.density, fontScale = 1f)) {
        Text(
            modifier = modifier,
            text = text,
            style = baseStyle,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow,
        )
    }
}
