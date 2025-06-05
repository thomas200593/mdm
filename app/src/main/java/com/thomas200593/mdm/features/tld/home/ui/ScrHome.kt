package com.thomas200593.mdm.features.tld.home.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.SubcomposeAsyncImage
import com.thomas200593.mdm.app.main.nav.ScrGraphs
import com.thomas200593.mdm.core.design_system.state_app.LocalStateApp
import com.thomas200593.mdm.core.design_system.state_app.StateApp
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.text.TextType
import com.thomas200593.mdm.core.ui.component.text.UiText
import kotlinx.coroutines.CoroutineScope

@Composable fun ScrHome(
    scrGraph: ScrGraphs.DestTopLevel.Home,
    stateApp: StateApp = LocalStateApp.current, coroutineScope: CoroutineScope = rememberCoroutineScope()
) { ScrHome() }
@Composable private fun ScrHome() { ScreenContent() }
@Composable private fun ScreenContent() { SectionContent() }
@Composable private fun SectionContent() = Surface(
    modifier = Modifier.fillMaxSize(),
    content = {
        LazyColumn (
            modifier = Modifier.fillMaxSize(),
            content = {
                item { SectionHeadline() }
            }
        )
    }
)
@OptIn(ExperimentalMaterial3ExpressiveApi::class) @Composable private fun SectionHeadline() {
    val imgModel = null //temporary
    Card(
        modifier = Modifier.fillMaxWidth().padding(Constants.Dimens.dp8),
        shape = MaterialTheme.shapes.extraSmall,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        content = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(Constants.Dimens.dp8),
                horizontalArrangement = Arrangement.spacedBy(Constants.Dimens.dp8, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Surface(
                        shape = RoundedCornerShape(Constants.Dimens.dp8),
                        border = BorderStroke(width = Constants.Dimens.dp1, color = MaterialTheme.colorScheme.outline),
                        tonalElevation = Constants.Dimens.dp2,
                        modifier = Modifier.size(Constants.Dimens.dp48),
                        content = {
                            SubcomposeAsyncImage(
                                model = imgModel, contentDescription = null,
                                loading = { CircularProgressIndicator(modifier = Modifier.padding(Constants.Dimens.dp8)) },
                                contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()
                            )
                        }
                    )
                    UiText(
                        text = buildString { append("Hi, ").append("First").append(" + ").append("Last name") },
                        type = TextType.TITLE_MD, modifier = Modifier.weight(1f),
                        maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                    IconButton (onClick = { /*TODO*/ }, content = { Icon(Icons.Outlined.AccountCircle, null) })
                }
            )
        }
    )
}