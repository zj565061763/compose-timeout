package com.sd.lib.compose.timeout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <T> TimeoutContent(
  state: TimeoutContentState<T>,
  content: @Composable (T?) -> Unit,
) {
  var data by remember { mutableStateOf<T?>(null) }

  val lifecycleOwner = LocalLifecycleOwner.current
  LaunchedEffect(state, lifecycleOwner) {
    state.itemFlow.flowWithLifecycle(lifecycleOwner.lifecycle)
      .collectLatest { item ->
        data = item?.data
        if (item != null) {
          val timeout = item.timeout
          if (timeout > 0) {
            delay(timeout)
            data = null
          }
        }
      }
  }

  content(data)
}

@Composable
fun <T> rememberTimeoutContentState(): TimeoutContentState<T> {
  return remember { TimeoutContentState<T>() }.also { it.Init() }
}

class TimeoutContentState<T> {
  internal val itemFlow = MutableSharedFlow<TimeoutContentItem<T>?>(
    extraBufferCapacity = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST,
  )

  internal var itemFlowSubscriptionCount by mutableIntStateOf(0)
    private set

  @Composable
  internal fun Init() {
    LaunchedEffect(Unit) {
      itemFlow.subscriptionCount.collect { count ->
        itemFlowSubscriptionCount = count
      }
    }
  }

  /** 设置内容项 */
  fun setItem(item: TimeoutContentItem<T>?) {
    itemFlow.tryEmit(item)
  }
}

/** 设置内容项 */
fun <T> TimeoutContentState<T>.setItem(data: T, timeout: Long) {
  setItem(TimeoutContentItem(data, timeout))
}