package com.litongjava.fishaudio.tts;

import java.util.List;

/**
 * 请求 Fish.audio TTS 接口时使用的参数对象。
 */

public class FishAudioTTSRequest {
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

  public FishAudioTTSRequest() {
    super();
    // TODO Auto-generated constructor stub
  }

  public FishAudioTTSRequest(String text, Integer chunk_length, String format, Integer mp3_bitrate,
      List<FishAudioReferenceAudio> references, String reference_id, Boolean normalize, String latency) {
    super();
    this.text = text;
    this.chunk_length = chunk_length;
    this.format = format;
    this.mp3_bitrate = mp3_bitrate;
    this.references = references;
    this.reference_id = reference_id;
    this.normalize = normalize;
    this.latency = latency;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Integer getChunk_length() {
    return chunk_length;
  }

  public void setChunk_length(Integer chunk_length) {
    this.chunk_length = chunk_length;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public Integer getMp3_bitrate() {
    return mp3_bitrate;
  }

  public void setMp3_bitrate(Integer mp3_bitrate) {
    this.mp3_bitrate = mp3_bitrate;
  }

  public List<FishAudioReferenceAudio> getReferences() {
    return references;
  }

  public void setReferences(List<FishAudioReferenceAudio> references) {
    this.references = references;
  }

  public String getReference_id() {
    return reference_id;
  }

  public void setReference_id(String reference_id) {
    this.reference_id = reference_id;
  }

  public Boolean getNormalize() {
    return normalize;
  }

  public void setNormalize(Boolean normalize) {
    this.normalize = normalize;
  }

  public String getLatency() {
    return latency;
  }

  public void setLatency(String latency) {
    this.latency = latency;
  }

}
