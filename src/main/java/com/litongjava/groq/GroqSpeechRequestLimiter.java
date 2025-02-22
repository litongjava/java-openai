package com.litongjava.groq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class GroqSpeechRequestLimiter {
  private final int allowedRequestsPerMinute;
  private int currentRequestCount = 0;
  private long minuteStart = System.currentTimeMillis();
  private final ByteArrayOutputStream mergedAudio = new ByteArrayOutputStream();
  private ScheduledFuture<?> scheduledFlush = null;
  private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

  public GroqSpeechRequestLimiter(int allowedRequestsPerMinute) {
    this.allowedRequestsPerMinute = allowedRequestsPerMinute;
  }

  /**
   * 判断当前是否可以发送新的请求（自动按分钟重置计数）
   */
  public boolean canSendRequest() {
    long now = System.currentTimeMillis();
    if (now - minuteStart >= 60000) {
      minuteStart = now;
      currentRequestCount = 0;
    }
    return currentRequestCount < allowedRequestsPerMinute;
  }

  public void recordRequest() {
    currentRequestCount++;
  }

  public void appendAudio(byte[] audioData) throws IOException {
    mergedAudio.write(audioData);
  }

  public boolean hasMergedAudio() {
    return mergedAudio.size() > 0;
  }

  public byte[] flushMergedAudio() {
    byte[] data = mergedAudio.toByteArray();
    mergedAudio.reset();
    return data;
  }

  /**
   * 如果超出限制，则调度一个任务，在当前分钟结束后发送合并的音频数据
   */
  public void scheduleFlush(String model, String audioMimeType, String audioFileName, Consumer<String> callback) {
    if (scheduledFlush == null || scheduledFlush.isDone()) {
      long delay = 60000 - (System.currentTimeMillis() - minuteStart);
      if (delay < 0) {
        delay = 0;
      }
      scheduledFlush = scheduler.schedule(() -> {
        synchronized (this) {
          if (hasMergedAudio() && canSendRequest()) {
            byte[] merged = flushMergedAudio();
            GroqSpeechClient.sendMergedRequest(model, merged, audioMimeType,audioFileName,callback);
            recordRequest();
          }
        }
      }, delay, TimeUnit.MILLISECONDS);
    }
  }
}
