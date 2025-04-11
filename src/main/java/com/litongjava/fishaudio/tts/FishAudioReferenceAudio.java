package com.litongjava.fishaudio.tts;

import org.msgpack.annotation.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 表示参考音频，用于 in-context 学习。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Message
public class FishAudioReferenceAudio {
  // 二进制格式的参考音频数据
  private byte[] audio;
  // 参考音频对应的文字描述
  private String text;
}
