package com.litongjava.groq;

public interface GropModel {
  /**
   * Whisper Large V3 Turbo
   * Multilingual
   * A fine-tuned version of a pruned Whisper Large V3 designed for fast, multilingual transcription tasks.
   */
  String WHISPER_LARGE_V3_TURBO = "whisper-large-v3-turbo";

  /**
   * Distil-Whisper English
   * English-only
   * A distilled, or compressed, version of OpenAI's Whisper model, designed to provide faster, lower cost English speech recognition while maintaining comparable accuracy.
   */
  String DISTIL_WHISPER_LARGE_V3_EN = "distil-whisper-large-v3-en";

  /**
   * Whisper large-v3
   * Multilingual
   *  Provides state-of-the-art performance with high accuracy for multilingual transcription and translation tasks.
   */
  String WHISPER_LARGE_V3 = "whisper_large_v3";
}
