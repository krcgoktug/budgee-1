/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.example.budgee.presentation

import WatchMenuDisplayOnly
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import androidx.wear.tooling.preview.devices.WearDevices
import com.example.budgee.presentation.theme.BudgeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    BudgeeTheme {
        val navController = rememberSwipeDismissableNavController()
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = "welcome"
        ) {
            composable("welcome") {
                BudgeeWelcomeScreen(navController = navController)
            }

            composable("main_chat") {
                MainChatScreen(
                    navController
                )
            }

            composable("action_screen") {
                ChatScreen()
            }

            composable("menu_screen") {
                WatchMenuDisplayOnly(
                    navController
                )
            }

            composable("budget_summary") {
                BudgetStatusScreen()
            }

            composable("budget_history") {
                DailySpendingScreenWear()
            }
            composable("notification"){
                NotificationsScreenWear()
            }

        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp()
}