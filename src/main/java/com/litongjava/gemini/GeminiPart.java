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
public class GeminiPart {
  private String text;
  private GeminiInlineDataVo inlineData;
  private GeminiFileDataVo fileData;
  private GeminiFunctionCallVo functionCall;

  public GeminiPart(String text) {
    this.text = text;
  }

  public GeminiPart(GeminiInlineDataVo inlineData) {
    this.inlineData = inlineData;
  }

  public GeminiPart(GeminiFileDataVo fileData) {
    this.fileData = fileData;
  }

  public GeminiPart(ChatImageFile chatFile) {
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
