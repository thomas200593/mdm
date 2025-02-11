package com.thomas200593.mdm.app.main.ui.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberStateApp(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
) : StateApp =
    remember(
        coroutineScope,
        navController
    ) {
        StateApp(
            coroutineScope = coroutineScope,
            navController = navController
        )
    }

@Stable
class StateApp(
    coroutineScope: CoroutineScope,
    navController: NavHostController
) {

}