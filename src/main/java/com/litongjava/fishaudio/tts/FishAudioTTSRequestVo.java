package com.litongjava.fishaudio.tts;

import java.util.List;

import org.msgpack.annotation.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 请求 Fish.audio TTS 接口时使用的参数对象。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Message
public class FishAudioTTSRequestVo {
  // 合成的文本内容
  private String text;

  // 音频分片长度（范围 100 ~ 300），默认 200
  private Integer chunk_length = 200;

  // 输出音频格式，可选 "wav"、"pcm"、"mp3"，默认 "mp3"
  private String format = "mp3";

  // 当 format 为 mp3 时使用的比特率，可选 64, 128, 192，默认 128
  private Integer mp3_bitrate = 128;

  // 参考音频列表，用于 in-context 学习，可传入多个
  private List<FishAudioReferenceAudio> references;

  // 直接指定参考音频的在线资源 id（例如 "7f92f8afb8ec43bf81429cc1c9199cb1"），可选
  private String reference_id;

  // 是否对文本进行标准化处理，默认为 true
  private Boolean normalize = true;

  // 延迟模式，"normal"（稳定模式）或 "balanced"（低延迟模式，约 300ms），默认 "normal"
  private String latency = "normal";
}
