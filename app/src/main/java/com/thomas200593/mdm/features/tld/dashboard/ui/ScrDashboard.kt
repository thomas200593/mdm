package com.thomas200593.mdm.features.tld.dashboard.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.thomas200593.mdm.app.main.nav.ScrGraphs

@Composable
fun ScrDashboard(scrGraph: ScrGraphs.DestTopLevel.Dashboard) {
    Text("Screen : ${stringResource(scrGraph.title)}")
}