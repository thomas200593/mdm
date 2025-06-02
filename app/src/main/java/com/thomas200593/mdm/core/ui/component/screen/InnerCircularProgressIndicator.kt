package com.thomas200593.mdm.core.ui.component.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable fun InnerCircularProgressIndicator() = Column(
    horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,
    modifier = Modifier.fillMaxWidth(), content = { CircularProgressIndicator() }
)