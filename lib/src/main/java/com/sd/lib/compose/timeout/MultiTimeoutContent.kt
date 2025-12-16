package com.sd.lib.compose.timeout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue

/**
 * 当[keys]变化时，会触发[getItem]获取项，
 * 如果是初次组合且[getItem]返回null，则显示[initItem]项
 */
@Composable
fun <T> MultiTimeoutContent(
  vararg keys: Any?,
  initItem: TimeoutContentItem<T>?,
  getItem: () -> TimeoutContentItem<T>?,
  content: @Composable (T?) -> Unit,
) {
  val state = rememberTimeoutContentState<T>()
  TimeoutContent(state, content)

  var isFirst by remember { mutableStateOf(true) }
  val getItemUpdated by rememberUpdatedState(getItem)

  if (state.itemFlowSubscriptionCount > 0) {
    LaunchedEffect(keys = keys) {
      val item = getItemUpdated()
      when {
        item != null -> state.setItem(item)
        isFirst && initItem != null -> state.setItem(initItem)
        else -> state.setItem(null)
      }
      isFirst = false
    }
  }
}