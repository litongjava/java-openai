package com.litongjava.openai.whisper;

public interface WhisperResponseFormat {
  String text = "text";
  String json = "json";
  String verbose_json = "verbose_json";
  String srt = "srt";
  String vtt = "vtt";
}
