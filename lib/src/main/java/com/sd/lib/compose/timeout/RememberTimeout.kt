package com.sd.lib.compose.timeout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay

@Composable
inline fun rememberTimeoutContent(
  timeout: Long,
  content: @Composable () -> Unit,
): State<Boolean> {
  return rememberTimeout(timeout).also {
    if (it.value) {
      // 超时
    } else {
      content()
    }
  }
}

@Composable
fun rememberTimeout(timeout: Long): State<Boolean> {
  val isTimeout = remember { mutableStateOf(false) }
  LaunchedEffect(Unit) {
    delay(timeout)
    isTimeout.value = true
  }
  return isTimeout
}