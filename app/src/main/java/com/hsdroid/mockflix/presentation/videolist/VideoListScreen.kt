package com.hsdroid.mockflix.presentation.videolist

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VideoListScreen(videoViewModel: VideoViewModel) {

    LaunchedEffect(key1 = Unit) {
        videoViewModel.response.collectLatest {
            if (it.isLoading) {

            } else if (it.error?.message?.isNotBlank() == true) {
                Log.d("harish", it.error.message.toString())
            } else {
                Log.d("harish", it.videoList.toString())
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            TopAppBar(
                title = { Text(text = "For HARISH", color = Color.White) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Black),
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 18.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 60.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                createChip()
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun createChip() {
    val options = listOf("TV Shows", "Movies", "Categories")

    options.forEach {
        AssistChip(
            modifier = Modifier.padding(horizontal = 4.dp),
            onClick = { /*TODO*/ },
            label = { Text(text = it, color = Color.White) },
            colors = AssistChipDefaults.assistChipColors(Color.Black)
        )

    }
}