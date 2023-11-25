package com.sekalisubmit.jetbrains.ui.screen.setting

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.sekalisubmit.jetbrains.R
import com.sekalisubmit.jetbrains.ui.theme.jetFont

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier
) {
    val intentGithub = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/letdummy"))
    val intentSetting = Intent(Settings.ACTION_DISPLAY_SETTINGS)
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("settingScreen"),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column {
            Text(
                text = "Settings",
                fontFamily = jetFont,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(
                            Color(0xFF121314),
                            RoundedCornerShape(8.dp)
                        )
                        .testTag("themeSetting"),
                ) {
                    Column{
                        Text(
                            text = "Theme",
                            color = Color(0xFFCFCFCF),
                            fontFamily = jetFont,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            modifier = modifier
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        )
                        Text(
                            text = "Don't like current theme? Change it",
                            color = Color(0xFFBEBEBE),
                            fontFamily = jetFont,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Start,
                            modifier = modifier
                                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                                .width(250.dp)
                        )
                    }

                    Box(
                        modifier = modifier
                            .align(Alignment.CenterVertically)
                            .padding(24.dp)
                            .background(
                                Color(0xFF0C8CE9),
                                RoundedCornerShape(8.dp)
                            )
                    ){
                        IconButton(
                            onClick = { ContextCompat.startActivity(context, intentSetting, null) },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_theme),
                                contentDescription = "Change theme",
                                tint = Color(0xFF121314),
                                modifier = modifier
                                    .size(30.dp)
                            )
                        }
                    }
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 100.dp)
                        .testTag("contributeSetting"),
                ) {
                    Column{
                        Text(
                            text = "Want more features?",
                            fontFamily = jetFont,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        )
                        Text(
                            text = "Contribute to the project on GitHub",
                            fontFamily = jetFont,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF0C8CE9),
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                                .clickable(
                                    onClick = {
                                        ContextCompat.startActivity(context, intentGithub, null)
                                    }
                                )
                        )
                    }
                }
            }
        }
    }
}