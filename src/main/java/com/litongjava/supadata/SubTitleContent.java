package com.litongjava.supadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字幕内容的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubTitleContent {
  /**
   * 字幕对应的语言
   */
  private String lang;

  /**
   * 字幕文本
   */
  private String text;

  /**
   * 字幕的起始时间偏移（单位：毫秒）
   */
  private long offset;

  /**
   * 字幕显示时长（单位：毫秒）
   */
  private long duration;
}

