package com.example.budgee.presentation

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.RemoteInput
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.items
import androidx.wear.compose.material.rememberScalingLazyListState
import androidx.wear.input.RemoteInputIntentHelper
import com.example.budgee.R

private const val TEXT_INPUT_KEY = "text_input"

@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel = viewModel()
) {
    val listState = rememberScalingLazyListState()
    val messages by chatViewModel.messages.collectAsState()
    val isLoading by chatViewModel.isLoading.collectAsState()

    var launchRemoteInput by remember { mutableStateOf(false) }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }

    }

    val textInputLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val results = result.data?.let { RemoteInput.getResultsFromIntent(it) }
            val newText = results?.getCharSequence(TEXT_INPUT_KEY)?.toString()
            if (!newText.isNullOrEmpty()) {
                chatViewModel.sendMessage(newText)
            }
        }
    }

    val textInputIntent = remember {
        RemoteInputIntentHelper.createActionRemoteInputIntent().apply {
            val remoteInputs: List<android.app.RemoteInput> = listOf(
                android.app.RemoteInput.Builder(TEXT_INPUT_KEY)
                    .setLabel("Your question")
                    .build()
            )
            RemoteInputIntentHelper.putRemoteInputsExtra(this, remoteInputs)
        }
    }

    LaunchedEffect(launchRemoteInput) {
        if (launchRemoteInput) {
            textInputLauncher.launch(textInputIntent)
            launchRemoteInput = false
        }
    }

    Scaffold(
        timeText = { TimeText(modifier = Modifier.padding(top = 8.dp)) },
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
    ) {
        ScalingLazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 60.dp)
        ) {
            items(messages) { message ->
                when (message.sender) {
                    Sender.USER -> UserMessageBubble(message.text)
                    Sender.BOT -> BotMessageBubble(message.text)
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Chip(
                label = { Text(if (isLoading) "Thinking..." else "Reply") },
                icon = {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(ChipDefaults.IconSize),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(painterResource(R.drawable.ic_microphone), "microphone")
                    }
                },
                enabled = !isLoading,
                onClick = { launchRemoteInput = true },
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
fun UserMessageBubble(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(0.9f),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.robot_character),
            contentDescription = "User avatar",
            modifier = Modifier.size(24.dp)
        )
        Row(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 4.dp,
                        topEnd = 20.dp,
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp
                    )
                )
                .background(Color(0xFFE0E0E0))
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_microphone),
                contentDescription = "Voice message icon",
                modifier = Modifier.size(16.dp),
                colorFilter = ColorFilter.tint(Color(0xFF558B2F))
            )
            Text(text = text, color = Color.Black, fontSize = 14.sp)
        }
    }
}

@Composable
fun BotMessageBubble(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clip(
                    RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 4.dp,
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp
                    )
                )
                .background(Color(0xFFE0E0E0))
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(
                text = text,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Preview(device = Devices.WEAR_OS_SQUARE, showSystemUi = true)
@Composable
fun ChatScreenPreview() {
    ChatScreen()
}