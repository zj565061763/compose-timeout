package com.sd.lib.compose.timeout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

/**
 * 1. 显示[content]
 * 2. 延迟[timeout]
 * 3. 移除[content]
 */
@Composable
fun SingleTimeoutContent(
  timeout: Long,
  content: @Composable () -> Unit,
) {
  var show by remember { mutableStateOf(true) }

  LaunchedEffect(Unit) {
    delay(timeout)
    show = false
  }

  if (show) {
    content()
  }
}