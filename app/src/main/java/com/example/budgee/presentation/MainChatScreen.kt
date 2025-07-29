package com.example.budgee.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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

    // Navigation logic remains the same
    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val isAtBottom = lastVisibleItem != null && lastVisibleItem.index == listState.layoutInfo.totalItemsCount - 1
            if (isAtBottom) {
                navController.navigate("action_screen")
            }
        }
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
            // 1. SET OVERALL SPACING BETWEEN ITEMS HERE
            verticalArrangement = Arrangement.spacedBy(12.dp),
            // 2. ADD PADDING TO THE TOP AND BOTTOM OF THE WHOLE LIST
            contentPadding = PaddingValues(top = 20.dp, bottom = 40.dp)
        ) {

            // Item 1: The Row with the chat bubble and robot
            // The initial Spacer is no longer needed because of contentPadding
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Chat Bubble
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFA9D1F7))
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Nasıl yardımcı olabilirim?",
                            color = Color(0xFF40668B),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    // Robot Image
                    Image(
                        painter = painterResource(id = R.drawable.robot_character),
                        contentDescription = "Robot character waving",
                        modifier = Modifier.size(70.dp)
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
                    // 3. REMOVED THE LARGE PADDING. The Arrangement handles the spacing now.
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item {
                Text(
                    "Devam etmek için aşağı kaydırın",
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