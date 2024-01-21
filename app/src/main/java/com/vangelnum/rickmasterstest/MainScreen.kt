package com.vangelnum.rickmasterstest

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vangelnum.rickmasterstest.feature_camera.presentation.CameraScreen
import com.vangelnum.rickmasterstest.feature_doors.presentation.DoorsScreen
import com.vangelnum.rickmasterstest.ui.theme.MainFontColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = {
        2
    })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            contentColor = MainFontColor,
            modifier = Modifier.height(44.dp),
            indicator = { tabPositions ->
                SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = Color(0xFF03A9F4)
                )
            }
        ) {
            Tab(selected = pagerState.currentPage == 0, text = {
                Text(
                    text = stringResource(id = R.string.cameras),
                    style = MaterialTheme.typography.titleMedium
                )
            }, onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(0)
                }
            })
            Tab(selected = pagerState.currentPage == 1, text = {
                Text(
                    text = stringResource(id = R.string.doors),
                    style = MaterialTheme.typography.titleMedium
                )
            }, onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(1)
                }
            })
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalAlignment = Alignment.Top,
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> {
                    CameraScreen()
                }

                1 -> {
                    DoorsScreen()
                }
            }
        }
    }
}