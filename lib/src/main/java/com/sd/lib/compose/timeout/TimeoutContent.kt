package com.sd.lib.compose.timeout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
inline fun <T> TimeoutContent(
  state: TimeoutContentState<T>,
  content: @Composable (T) -> Unit,
) {
  val timeoutContent by state.contentFlow.collectAsStateWithLifecycle()
  timeoutContent?.also { content(it) }
}

@Composable
fun <T> rememberTimeoutContentState(
  init: TimeoutContentState<T>.() -> Unit = {},
): TimeoutContentState<T> {
  return remember { TimeoutContentState<T>().apply(init) }.also { it.Init() }
}

class TimeoutContentState<T> {
  val contentFlow = MutableStateFlow<T?>(null)

  private lateinit var _coroutineScope: CoroutineScope
  private var _itemJob: Job? = null

  @Composable
  internal fun Init() {
    _coroutineScope = rememberCoroutineScope()
  }

  /** 设置内容 */
  fun setContent(content: T, timeout: Long) {
    setContentInternal(content, timeout)
  }

  /** 清空内容 */
  fun clearContent() {
    setContentInternal(null, 0)
  }

  private fun setContentInternal(content: T?, timeout: Long) {
    _itemJob?.cancel()
    contentFlow.value = content
    if (timeout > 0) {
      _itemJob = _coroutineScope.launch {
        delay(timeout)
        contentFlow.value = null
      }
    }
  }
}