package com.sd.lib.compose.timeout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun <T> rememberTimeoutContentState(): TimeoutContentState<T> {
  val coroutineScope = rememberCoroutineScope()
  return remember { TimeoutContentState(coroutineScope) }
}

class TimeoutContentState<T>(
  private val coroutineScope: CoroutineScope,
) {
  private var _isFirst = true
  private var _contentJob: Job? = null

  /** 要显示的内容 */
  val contentFlow = MutableStateFlow<T?>(null)

  /** 设置内容 */
  fun setContent(content: T, timeout: Long) {
    setContentInternal(content, timeout)
  }

  /** 如果还未设置内容则显示首次内容[content]，如果已经设置过内容则清空内容 */
  fun clearOrSetFirstContent(content: T?, timeout: Long) {
    if (_isFirst) {
      setContentInternal(content, timeout)
    } else {
      setContentInternal(null, 0)
    }
  }

  private fun setContentInternal(content: T?, timeout: Long) {
    _isFirst = false
    _contentJob?.cancel()
    contentFlow.value = content
    if (timeout > 0) {
      _contentJob = coroutineScope.launch {
        delay(timeout)
        contentFlow.value = null
      }
    }
  }
}