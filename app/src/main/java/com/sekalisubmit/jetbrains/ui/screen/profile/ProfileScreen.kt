package com.sekalisubmit.jetbrains.ui.screen.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.sekalisubmit.jetbrains.R
import com.sekalisubmit.jetbrains.ui.theme.jetFont

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val intentLinkedIn = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/agusardi/"))
    val intentGithub = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/letdummy"))
    val context = LocalContext.current
    val email = stringResource(R.string.profile_email)

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("profileScreen"),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            Text(
                text = stringResource(R.string.profile_hook),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontFamily = jetFont
                ),
                modifier = modifier
                    .width(250.dp)
                    .padding(vertical = 16.dp)
            )
            Image(
                painter = painterResource(R.drawable.avatar),
                contentDescription = "about_page",
                modifier = modifier
                    .clip(CircleShape)
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(R.string.profile_name),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontFamily = jetFont
                ),
                modifier = modifier
                    .width(250.dp)
                    .padding(top = 16.dp)
            )
            Text(
                text = stringResource(R.string.profile_email),
                style = TextStyle(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = jetFont,
                    color = Color(0xFF0C8CE9),
                ),
                modifier = modifier
                    .width(250.dp)
                    .padding(top = 4.dp)
                    .clickable(onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO)
                        intent.data = Uri.parse("mailto:$email")
                        startActivity(context, intent, null)
                    })
                    .testTag("email")
            )
            Row (
                modifier = modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                IconButton(
                    onClick = { startActivity(context, intentLinkedIn, null) },
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_linkedin),
                        contentDescription = "LinkedIn",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier
                            .size(30.dp)
                            .testTag("linkedin")
                    )
                }
                IconButton(
                    onClick = { startActivity(context, intentGithub, null) },
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_github),
                        contentDescription = "GitHub",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier
                            .size(30.dp)
                            .testTag("github")
                    )
                }
            }
        }
    }
}