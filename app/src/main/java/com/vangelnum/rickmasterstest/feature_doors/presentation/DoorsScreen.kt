package com.vangelnum.rickmasterstest.feature_doors.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.vangelnum.rickmasterstest.R
import com.vangelnum.rickmasterstest.feature_core.helpers.Resource
import com.vangelnum.rickmasterstest.feature_doors.data.model.DoorsData
import com.vangelnum.rickmasterstest.ui.theme.SecondFontColor
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoorsScreen() {
    val doorsViewModel = viewModel<DoorsViewModel>()
    val doorsState by doorsViewModel.doorsState.observeAsState(initial = Resource.Loading)
    val revealedDoorsIds by doorsViewModel.revealedDoorsIdsList.observeAsState(emptyList())
    val density = LocalDensity.current.density
    when (val currentState = doorsState) {
        is Resource.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${currentState.message}")
            }
        }
        Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is Resource.Success -> {
            val refreshState = rememberPullToRefreshState()

            if (refreshState.isRefreshing) {
                LaunchedEffect(Unit) {
                    doorsViewModel.refreshDoors()
                    refreshState.endRefresh()
                }
            }

            Box(Modifier.nestedScroll(refreshState.nestedScrollConnection)) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(11.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (!refreshState.isRefreshing) {
                        items(currentState.resourceData) { door ->
                            if (door.snapshot == null) {
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(72.dp)
                                ) {
                                    ActionDoorRow(
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    DraggableDoorItem(
                                        door = door,
                                        modifier = Modifier.fillMaxSize(),
                                        isRevealed = revealedDoorsIds.contains(door.id),
                                        onExpand = { doorsViewModel.onItemExpanded(door.id) },
                                        onCollapse = { doorsViewModel.onItemCollapsed(door.id) },
                                        cardOffset = 72.dp.value * density + 18.dp.value * density //first for icons, second for padding
                                    )
                                }
                            } else {
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(279.dp)
                                ) {
                                    ActionDoorRow(
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    DraggableDoorItemWithImage(
                                        door = door,
                                        modifier = Modifier.fillMaxSize(),
                                        isRevealed = revealedDoorsIds.contains(door.id),
                                        onExpand = { doorsViewModel.onItemExpanded(door.id) },
                                        onCollapse = { doorsViewModel.onItemCollapsed(door.id) },
                                        cardOffset = 72.dp.value * density + 18.dp.value * density //first for icons, second for padding
                                    )
                                }
                            }
                        }
                    }
                }
                PullToRefreshContainer(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = -TabRowDefaults.ScrollableTabRowEdgeStartPadding),
                    state = refreshState,
                )
            }
        }
    }
}

@Composable
fun ActionDoorRow(modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(9.dp, Alignment.End),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.edit),
            contentDescription = stringResource(id = R.string.edit)
        )
        Image(
            painter = painterResource(R.drawable.staroutline),
            contentDescription = stringResource(id = R.string.star)
        )
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggableDoorItem(
    door: DoorsData,
    modifier: Modifier = Modifier,
    cardOffset: Float,
    isRevealed: Boolean,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
) {
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, "cardTransition")
    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = 500) },
        targetValueByState = { if (isRevealed) -cardOffset else 0F },
    )
    Card(
        modifier = modifier
            .offset { IntOffset(offsetTransition.roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    when {
                        dragAmount < 6 -> onExpand()
                        dragAmount >= -6 -> onCollapse()
                    }
                }
            },
        elevation = CardDefaults.elevatedCardElevation(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = door.name,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = SecondFontColor
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Image(
                painter = painterResource(id = R.drawable.lockoff),
                contentDescription = stringResource(id = R.string.lock_off)
            )
        }
    }
}


@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggableDoorItemWithImage(
    door: DoorsData,
    modifier: Modifier = Modifier,
    cardOffset: Float,
    isRevealed: Boolean,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
) {
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, "cardTransition")
    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = 500) },
        targetValueByState = { if (isRevealed) -cardOffset else 0F },
    )
    Card(
        modifier = modifier
            .offset { IntOffset(offsetTransition.roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    when {
                        dragAmount < 6 -> onExpand()
                        dragAmount >= -6 -> onCollapse()
                    }
                }
            },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(207.dp),
        ) {
            //Should be, but certification is not working
            //AsyncImage(model = camera.snapshot, contentDescription = null)

            AsyncImage(
                model = "https://images.unsplash.com/photo-1518893494013-481c1d8ed3fd?q=80&w=500&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                contentDescription = stringResource(id = R.string.camera),
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Image(
                painter = painterResource(id = R.drawable.playbutton),
                contentDescription = stringResource(id = R.string.play),
                modifier = Modifier
                    .align(
                        Alignment.Center
                    )
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = door.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium.copy(color = SecondFontColor)
                    )
                    Text(
                        text = stringResource(id = R.string.online),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    painter = painterResource(id = R.drawable.lockoff),
                    contentDescription = stringResource(
                        id = R.string.lock_off
                    )
                )
            }
        }
    }
}


