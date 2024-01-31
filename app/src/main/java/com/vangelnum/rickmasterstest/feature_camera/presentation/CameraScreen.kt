package com.vangelnum.rickmasterstest.feature_camera.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.vangelnum.rickmasterstest.R
import com.vangelnum.rickmasterstest.feature_camera.data.model.Camera
import com.vangelnum.rickmasterstest.feature_core.helpers.Resource
import com.vangelnum.rickmasterstest.ui.theme.SecondFontColor
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen() {
    val cameraViewModel: CameraViewModel = viewModel()
    val cameraState by cameraViewModel.cameras.observeAsState(initial = Resource.Loading)
    val revealedCamerasIds by cameraViewModel.revealedCamerasIdsList.observeAsState(emptyList())
    val density = LocalDensity.current.density

    when (val currentState = cameraState) {
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

            val camerasByRoom by remember {
                mutableStateOf(currentState.resourceData.groupBy { it.room })
            }

            val refreshState = rememberPullToRefreshState()

            if (refreshState.isRefreshing) {
                LaunchedEffect(Unit) {
                    cameraViewModel.refreshCameras()
                    refreshState.endRefresh()
                }
            }

            Box(Modifier.nestedScroll(refreshState.nestedScrollConnection)) {
                LazyColumn(
                    contentPadding = PaddingValues(start = 21.dp, end = 21.dp, top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(11.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (!refreshState.isRefreshing) {
                        camerasByRoom.forEach { (roomNumber, camerasInRoom) ->
                            item {
                                if (!roomNumber.isNullOrBlank()) {
                                    Text(
                                        text = roomNumber,
                                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W300)
                                    )
                                } else {
                                    Text(
                                        text = "Another cameras",
                                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W300)
                                    )
                                }
                            }
                            items(camerasInRoom, key = { it.id }) { camera ->
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(279.dp)
                                ) {
                                    ActionRow(
                                        modifier = Modifier.fillMaxSize(),
                                        onFavorite = {}
                                    )
                                    DraggableCard(
                                        camera = camera,
                                        modifier = Modifier.fillMaxSize(),
                                        isRevealed = revealedCamerasIds.contains(camera.id),
                                        onExpand = { cameraViewModel.onItemExpanded(camera.id) },
                                        onCollapse = { cameraViewModel.onItemCollapsed(camera.id) },
                                        cardOffset = 36.dp.value * density + 9.dp.value * density //first for icon, second for padding
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

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggableCard(
    camera: Camera,
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
            if (camera.rec) {
                Box(modifier = Modifier.padding(8.dp)) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.rec),
                        contentDescription = stringResource(id = R.string.recording),
                        modifier = Modifier
                            .align(Alignment.TopStart),
                    )
                }
            }
            if (camera.favorites) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.star),
                        contentDescription = stringResource(id = R.string.star),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    )
                }
            }
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.playbutton),
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
                .height(72.dp), contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = camera.name,
                modifier = Modifier.padding(horizontal = 16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(color = SecondFontColor)
            )
        }
    }
}

@Composable
fun ActionRow(
    modifier: Modifier,
    onFavorite: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.staroutline),
            contentDescription = stringResource(id = R.string.star),
            modifier = Modifier
                .clickable {
                    onFavorite()
                }
        )
    }
}