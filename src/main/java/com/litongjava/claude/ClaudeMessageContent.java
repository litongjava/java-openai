package com.litongjava.claude;

import com.litongjava.chat.ChatImageFile;
import com.litongjava.tio.utils.hutool.StrUtil;

public class ClaudeMessageContent {
  private String type;
  private String text;
  private ClaudeCacheControl cache_control;
  private ClaudeChatSource source;

  public ClaudeMessageContent(String type, String text) {
    this.type = type;
    this.text = text;
  }

  public ClaudeMessageContent(ChatImageFile file) {
    this.type = "image";
    if (StrUtil.isNotBlank(file.getUrl())) {
      this.source = ClaudeChatSource.url(file.getUrl());

    } else if (StrUtil.isNotBlank(file.getData())) {
      this.source = ClaudeChatSource.base64(file.getMimeType(), file.getData());
    }

    if (file.isCached()) {
      this.cache_control = new ClaudeCacheControl();
    }

  }

  public ClaudeMessageContent() {
    super();
  }

  public ClaudeMessageContent(String type, String text, ClaudeCacheControl cache_control, ClaudeChatSource source) {
    super();
    this.type = type;
    this.text = text;
    this.cache_control = cache_control;
    this.source = source;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public ClaudeCacheControl getCache_control() {
    return cache_control;
  }

  public void setCache_control(ClaudeCacheControl cache_control) {
    this.cache_control = cache_control;
  }

  public ClaudeChatSource getSource() {
    return source;
  }

  public void setSource(ClaudeChatSource source) {
    this.source = source;
  }

}
