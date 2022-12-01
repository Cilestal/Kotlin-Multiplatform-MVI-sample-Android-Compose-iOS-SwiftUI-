package com.cilestal.mvi_mpp.android.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cilestal.mvi_mpp.android.theme.Background
import com.cilestal.mvi_mpp.android.theme.Gray90
import com.cilestal.mvi_mpp.android.theme.GreenDark
import com.cilestal.mvi_mpp.android.theme.defaultMargin

val TOOLBAR_SIMPLE_HEIGHT = 56.dp

@Composable
fun ToolbarSimple(
    onClick: () -> Unit,
    title: String,
    enabled: Boolean = true
) {
    TopAppBar(
        modifier = Modifier
            .height(TOOLBAR_SIMPLE_HEIGHT)
            .fillMaxWidth(),
        backgroundColor = Background,
        elevation = 4.dp,
        content = {
            IconButton(
                enabled = enabled,
                modifier = Modifier.size(TOOLBAR_SIMPLE_HEIGHT),
                onClick = onClick
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, tint = GreenDark, contentDescription = "Menu Btn")
            }
            Text(
                title,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .padding(end = defaultMargin),
                color = Gray90,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    )
}

@Composable
@Preview
fun ToolbarSimplePreview() {
    ToolbarSimple(
        onClick = {},
        title = "Simple Toolbar"
    )
}