package com.litongjava.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniChatFile {
  private String type;
  private ChatImageFile image_url;
}
