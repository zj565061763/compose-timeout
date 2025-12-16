package com.sd.lib.compose.timeout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * 当[keys]变化时，会触发[getKeyItem]获取项，
 * 如果是初次组合且[getKeyItem]返回null，则显示[getInitItem]返回的项
 */
@Composable
fun <T> MultiTimeoutContent(
  vararg keys: Any?,
  getInitItem: () -> TimeoutContentItem<T>?,
  getKeyItem: () -> TimeoutContentItem<T>?,
  content: @Composable (T?) -> Unit,
) {
  var isFirst by remember { mutableStateOf(true) }

  val state = rememberTimeoutContentState<T>()
  TimeoutContent(state, content)

  if (state.hasTimeoutContentCollector) {
    LaunchedEffect(keys = keys) {
      val keyItem = getKeyItem()
      when {
        keyItem != null -> keyItem
        isFirst -> getInitItem()
        else -> null
      }.also {
        state.setItem(it)
        isFirst = false
      }
    }
  }
}