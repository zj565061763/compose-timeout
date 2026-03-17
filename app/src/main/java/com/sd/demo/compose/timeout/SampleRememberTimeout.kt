package com.sd.demo.compose.timeout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sd.demo.compose.timeout.theme.AppTheme
import com.sd.lib.compose.timeout.TimeoutContent

class SampleRememberTimeout : ComponentActivity() {
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
  Column(
    modifier = modifier
      .fillMaxSize()
      .safeContentPadding(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    TimeoutContent(3000) {
      Text(text = "content")
    }
  }
}