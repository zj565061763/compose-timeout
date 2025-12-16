package com.sd.demo.compose.timeout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sd.demo.compose.timeout.theme.AppTheme
import com.sd.lib.compose.timeout.MultiTimeoutContent
import com.sd.lib.compose.timeout.TimeoutContentItem

class SampleMultiTimeoutContent : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AppTheme {
        Content()
      }
    }
  }
}

@Composable
private fun Content(
  modifier: Modifier = Modifier,
) {
  var type by remember { mutableStateOf<MultiType?>(null) }
  Column(
    modifier = modifier
      .fillMaxSize()
      .safeContentPadding(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Button(onClick = { type = MultiType.T1 }) {
      Text(text = "T1")
    }
    Button(onClick = { type = MultiType.T2 }) {
      Text(text = "T2")
    }
    Button(onClick = { type = null }) {
      Text(text = "null")
    }
    MultiTimeoutContent(
      type,
      initItem = TimeoutContentItem("initItem", 3000),
      getItem = {
        when (type) {
          MultiType.T1 -> TimeoutContentItem("T1", 0)
          MultiType.T2 -> TimeoutContentItem("T2", 0)
          null -> null
        }
      },
    ) { data ->
      if (data != null) {
        Text(text = data)
      }
    }
  }
}

enum class MultiType {
  T1,
  T2,
}