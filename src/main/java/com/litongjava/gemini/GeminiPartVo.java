package com.litongjava.gemini;

import com.litongjava.chat.ChatImageFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对话的一段内容
 * 既可以是 text，也可以是 image 等
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiPartVo {
  private String text;
  private GeminiInlineDataVo inlineData;
  private GeminiFileDataVo fileData;
  private GeminiFunctionCallVo functionCall;

  public GeminiPartVo(String text) {
    this.text = text;
  }

  public GeminiPartVo(GeminiInlineDataVo inlineData) {
    this.inlineData = inlineData;
  }

  public GeminiPartVo(GeminiFileDataVo fileData) {
    this.fileData = fileData;
  }

  public GeminiPartVo(ChatImageFile chatFile) {
    String type = chatFile.getMimeType();
    String data = chatFile.getData();
    String url = chatFile.getUrl();
    if (data != null) {
      this.inlineData = new GeminiInlineDataVo(type, data);
    } else if (url != null) {
      this.fileData = new GeminiFileDataVo(type, url);
    }
  }
}
