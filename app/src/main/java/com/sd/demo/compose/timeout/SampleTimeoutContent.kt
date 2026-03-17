package com.sd.demo.compose.timeout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sd.demo.compose.timeout.theme.AppTheme
import com.sd.lib.compose.timeout.TimeoutContent
import com.sd.lib.compose.timeout.rememberTimeoutContentState

class SampleTimeoutContent : ComponentActivity() {
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
  val state = rememberTimeoutContentState<String>()

  Column(
    modifier = modifier
      .fillMaxSize()
      .safeContentPadding(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Button(onClick = { state.setContent("1", 3000) }) {
      Text(text = "1(3000)")
    }

    Button(onClick = { state.setContent("2", 3000) }) {
      Text(text = "2(3000)")
    }

    Button(onClick = { state.setContent("3", 0) }) {
      Text(text = "3(0)")
    }

    Button(onClick = { state.clearOrSetFirstContent("first", 3000) }) {
      Text(text = "clearOrSetFirstContent")
    }

    TimeoutContent(state) { content ->
      Text(text = content)
    }
  }
}