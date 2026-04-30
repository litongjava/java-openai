package nexus.io.gemini;

/**
 * Gemini TTS 解析后的结果
 */
public class GeminiTTSResult {
  /** 原始 mimeType, 例如 "audio/L16;codec=pcm;rate=24000" */
  private String mimeType;
  /** base64 编码的原始 PCM 数据 (与 Gemini API 返回一致) */
  private String base64Data;
  /** 解码后的 PCM 二进制数据 */
  private byte[] pcmBytes;
  /** 采样率(从 mimeType 中解析,例如 24000) */
  private int sampleRate;
  /** 声道数(默认 1) */
  private int channels = 1;
  /** 样本位数(默认 16-bit) */
  private int bitsPerSample = 16;
  /** 原始 JSON 响应 */
  private String rawData;

  public GeminiTTSResult() {
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public String getBase64Data() {
    return base64Data;
  }

  public void setBase64Data(String base64Data) {
    this.base64Data = base64Data;
  }

  public byte[] getPcmBytes() {
    return pcmBytes;
  }

  public void setPcmBytes(byte[] pcmBytes) {
    this.pcmBytes = pcmBytes;
  }

  public int getSampleRate() {
    return sampleRate;
  }

  public void setSampleRate(int sampleRate) {
    this.sampleRate = sampleRate;
  }

  public int getChannels() {
    return channels;
  }

  public void setChannels(int channels) {
    this.channels = channels;
  }

  public int getBitsPerSample() {
    return bitsPerSample;
  }

  public void setBitsPerSample(int bitsPerSample) {
    this.bitsPerSample = bitsPerSample;
  }

  public String getRawData() {
    return rawData;
  }

  public void setRawData(String rawData) {
    this.rawData = rawData;
  }
}
