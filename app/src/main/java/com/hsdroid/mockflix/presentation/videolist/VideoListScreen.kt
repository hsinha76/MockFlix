package com.hsdroid.mockflix.presentation.videolist

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hsdroid.mockflix.domain.model.VideoResponse
import com.hsdroid.mockflix.presentation.ui.theme.customBackground
import com.hsdroid.mockflix.presentation.ui.theme.customGray
import com.hsdroid.mockflix.presentation.ui.theme.customGray_2
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VideoListScreen(videoViewModel: VideoViewModel) {

    val context = LocalContext.current

    var responseList = remember {
        mutableStateOf(emptyList<VideoResponse>())
    }

    LaunchedEffect(key1 = Unit) {
        videoViewModel.response.collectLatest {
            if (it.isLoading) {
                //do nothing
            } else if (it.error?.message?.isNotBlank() == true) {
                Toast.makeText(context, it.error.message, Toast.LENGTH_SHORT).show()
            } else {
                responseList.value = it.videoList
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(
                text = "For HARISH",
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Black),
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            })
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(customBackground)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 18.dp, horizontal = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 60.dp, start = 4.dp, end = 4.dp)
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                createChip()
            }

            if (responseList.value.isNotEmpty()) {
                frontVideoSection(responseList.value)
                continueWatchingSection(responseList)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun createChip() {
    val options = listOf("TV Shows", "Movies", "Categories")

    options.forEach {
        AssistChip(shape = RoundedCornerShape(40.dp),
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .height(40.dp),
            onClick = { /*TODO*/ },
            label = { Text(text = it, color = Color.White) },
            colors = AssistChipDefaults.assistChipColors(Color.Black),
            trailingIcon = {
                if (it == "Categories") {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.White
                    )
                }
            })

    }
}

@Composable
fun frontVideoSection(responseList: List<VideoResponse>) {
    val context = LocalContext.current
    var addDelay by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        delay(1200)
        addDelay = true
    }

    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        ConstraintLayout(
            Modifier
                .fillMaxWidth()
                .height(460.dp)
                .background(Color.Black)
        ) {
            val (title, tags, bottomOptions, videoPreview) = createRefs()

            if (addDelay && responseList.isNotEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize(),
                        model = ImageRequest.Builder(context).data(responseList[0].thumbnailUrl)
                            .build(),
                        contentDescription = null
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .align(Alignment.BottomCenter)
                            .blur(radiusY = 5.dp, radiusX = 100.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxWidth(),
                            model = ImageRequest.Builder(context).data(responseList[0].thumbnailUrl)
                                .build(),
                            contentDescription = null
                        )
                    }
                }
            }

            Text(
                text = if (responseList.isNotEmpty()) responseList[0].title else "",
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .constrainAs(title) {
                        bottom.linkTo(tags.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                color = Color.White,
                fontSize = 64.sp
            )

            Text(
                text = "Offbeat • Thriller • Corruption • Ominous",
                modifier = Modifier
                    .padding(bottom = 14.dp)
                    .constrainAs(tags) {
                        bottom.linkTo(bottomOptions.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                color = Color.White,
                fontSize = 18.sp
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .constrainAs(bottomOptions) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }) {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .padding(start = 16.dp, end = 4.dp)
                        .weight(0.5f),
                    onClick = { /* TODO: Handle Play button click */ },
                    shape = RectangleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Text(
                        text = "Play",
                        modifier = Modifier.padding(horizontal = 4.dp),
                        color = Color.Black
                    )
                }

                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = customGray),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .padding(end = 16.dp, start = 4.dp)
                        .weight(0.5f),
                    onClick = { /* TODO: Handle Add to My List button click */ },
                    shape = RectangleShape
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Text(text = "My List", modifier = Modifier.padding(horizontal = 4.dp))
                }
            }

        }
    }


}

@Composable
fun continueWatchingSection(responseList: MutableState<List<VideoResponse>>) {
    Text(
        text = "Continue Watching for HARISH",
        fontSize = 18.sp,
        color = Color.White,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp)
    )
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        val uniqueItems = responseList.value.toSet().toList()
        items(uniqueItems) {
            continueWatchingCard(it)
        }
    }
}

@Composable
fun continueWatchingCard(responseList: VideoResponse) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .width(140.dp)
            .padding(4.dp)
            .height(180.dp),
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(modifier = Modifier.wrapContentSize()) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {

                val (imageView, bottomView) = createRefs()

                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(imageView) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    contentScale = ContentScale.FillBounds,
                    model = ImageRequest.Builder(context).data(responseList.thumbnailUrl).build(),
                    contentDescription = null
                )

                Row(modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(customGray_2)
                    .constrainAs(bottomView) {
                        bottom.linkTo(parent.bottom)
                    }) {

                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                        val (info, options) = createRefs()

                        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(info) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                        IconButton(onClick = { /*TODO*/ },
                            modifier = Modifier.constrainAs(options) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }

            }
            Icon(imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .zIndex(1f)
                    .clickable {

                    }
                    .size(60.dp),
                tint = Color.White)
        }
    }
}