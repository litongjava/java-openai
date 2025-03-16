package com.litongjava.supadata;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 成功响应的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubTitleResponse {
  /**
   * 视频字幕的语言
   */
  private String lang;

  /**
   * 可用的语言列表
   */
  private List<String> availableLangs;

  /**
   * 字幕内容列表
   */
  private List<SubTitleContent> content;
}
