package com.example.budgee.presentation

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.budgee.R

@Composable
fun MainChatScreen(navController: NavController, userName: String = "Azra") {
    val listState = rememberScalingLazyListState()

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val isAtBottom = lastVisibleItem != null && lastVisibleItem.index == listState.layoutInfo.totalItemsCount - 1
            if (isAtBottom) {
                navController.navigate("menu_screen")
            }
        }
    }

    LaunchedEffect(Unit) { // 'Unit' anahtarı, bu efektin sadece bir kere çalışmasını sağlar
        listState.animateScrollToItem(0) // En üste kaydır
    }

    Scaffold(
        timeText = {
            TimeText(modifier = Modifier.padding(top = 8.dp))
        },
        positionIndicator = {
            PositionIndicator(scalingLazyListState = listState)
        }
    ) {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 40.dp, bottom = 40.dp)
        ) {


            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xFFA9D1F7))
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .size(64.dp)
                    ) {
                        Text(
                            text = "Nasıl yardımcı olabilirim?",
                            color = Color(0xFF40668B),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.robot_character),
                        contentDescription = "Robot character waving",
                        modifier = Modifier.size(48.dp).clickable {
                            navController.navigate("action_screen")
                        }
                    )


                }
            }

            item {
                Text(
                    text = "Hoşgeldin $userName!",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item {
                Text(
                    "Menüye dönmek için kaydır",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}


@Preview(device = Devices.WEAR_OS_SQUARE, showSystemUi = true)
@Composable
fun MainChatScreenPreview() {
    MainChatScreen(navController = rememberSwipeDismissableNavController(), userName = "Azra")
}