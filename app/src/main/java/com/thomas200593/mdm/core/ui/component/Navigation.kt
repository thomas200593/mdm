package com.thomas200593.mdm.core.ui.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.NavBarItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    selected: Boolean,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
) = NavigationBarItem(
    selected = selected,
    onClick = onClick,
    icon = if (selected) selectedIcon else icon,
    modifier = modifier,
    enabled = enabled,
    label = label,
    alwaysShowLabel = alwaysShowLabel,
    colors = NavigationBarItemDefaults.colors(
        selectedIconColor = NavDefaults.navSelectedItemColor(),
        unselectedIconColor = NavDefaults.navContentColor(),
        selectedTextColor = NavDefaults.navSelectedItemColor(),
        unselectedTextColor = NavDefaults.navContentColor(),
        indicatorColor = NavDefaults.navIndicatorColor()
    )
)

@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) = NavigationBar(
    modifier = modifier,
    contentColor = NavDefaults.navContentColor(),
    tonalElevation = 0.dp,
    content = content
)

@Composable
fun NavRailItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    selected: Boolean,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
) = NavigationRailItem(
    selected = selected,
    onClick = onClick,
    icon = if (selected) selectedIcon else icon,
    modifier = modifier,
    enabled = enabled,
    label = label,
    alwaysShowLabel = alwaysShowLabel,
    colors = NavigationRailItemDefaults.colors(
        selectedIconColor = NavDefaults.navSelectedItemColor(),
        unselectedIconColor = NavDefaults.navContentColor(),
        selectedTextColor = NavDefaults.navSelectedItemColor(),
        unselectedTextColor = NavDefaults.navContentColor(),
        indicatorColor = NavDefaults.navIndicatorColor()
    )
)

@Composable
fun NavRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) = NavigationRail(
    modifier = modifier,
    containerColor = Color.Transparent,
    contentColor = NavDefaults.navContentColor(),
    header = header,
    content = content
)

@Composable
fun AppNavSuiteScaffold(
    modifier: Modifier = Modifier,
    navSuiteItems: NavSuiteScope.() -> Unit,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    content: @Composable () -> Unit
) {
    val layoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(windowAdaptiveInfo)
    val navSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = NavDefaults.navSelectedItemColor(),
            unselectedIconColor = NavDefaults.navContentColor(),
            selectedTextColor = NavDefaults.navSelectedItemColor(),
            unselectedTextColor = NavDefaults.navContentColor(),
            indicatorColor = NavDefaults.navIndicatorColor()
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = NavDefaults.navSelectedItemColor(),
            unselectedIconColor = NavDefaults.navContentColor(),
            selectedTextColor = NavDefaults.navSelectedItemColor(),
            unselectedTextColor = NavDefaults.navContentColor(),
            indicatorColor = NavDefaults.navIndicatorColor(),
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = NavDefaults.navSelectedItemColor(),
            unselectedIconColor = NavDefaults.navContentColor(),
            selectedTextColor = NavDefaults.navSelectedItemColor(),
            unselectedTextColor = NavDefaults.navContentColor(),
        )
    )
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            NavSuiteScope(
                navigationSuiteScope = this,
                navSuiteItemColors = navSuiteItemColors
            ).run(navSuiteItems)
        },
        layoutType = layoutType,
        containerColor = Color.Transparent,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContentColor = NavDefaults.navContentColor(),
            navigationRailContainerColor = Color.Transparent
        ),
        modifier = modifier,
        content = content
    )
}

class NavSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navSuiteItemColors: NavigationSuiteItemColors
) {
    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit = icon,
        label: @Composable (() -> Unit)? = null
    ) = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        icon = { if (selected) selectedIcon() else icon() },
        label = label,
        colors = navSuiteItemColors,
        modifier = modifier
    )
}

object NavDefaults {
    @Composable
    fun navContentColor() = MaterialTheme.colorScheme.onSurfaceVariant
    @Composable
    fun navSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer
    @Composable
    fun navIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}