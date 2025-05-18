package com.litongjava.claude;

import com.litongjava.chat.ChatFile;
import com.litongjava.tio.utils.hutool.StrUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaudeMessageContent {
  private String type;
  private String text;
  private ClaudeCacheControl cache_control;
  private ClaudeChatSource source;

  public ClaudeMessageContent(String type, String text) {
    this.type = type;
    this.text = text;
  }

  public ClaudeMessageContent(ChatFile file) {
    this.type = "image";
    if (StrUtil.isNotBlank(file.getUrl())) {
      this.source = ClaudeChatSource.url(file.getUrl());

    } else if (StrUtil.isNotBlank(file.getData())) {
      this.source = ClaudeChatSource.base64(file.getMimeType(), file.getData());
    }

  }
}
