package com.sd.lib.compose.timeout

data class TimeoutContentItem<T>(
  /** 要显示的数据 */
  val data: T,

  /** 显示时长，小于等于0则一直显示 */
  val timeout: Long,
)