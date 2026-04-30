package nexus.io.gemini;

/**
 * Gemini TTS 语音配置
 * 例如: { "prebuiltVoiceConfig": { "voiceName": "Kore" } }
 */
public class GeminiVoiceConfig {
  private GeminiPrebuiltVoiceConfig prebuiltVoiceConfig;

  public GeminiVoiceConfig() {
  }

  public GeminiVoiceConfig(GeminiPrebuiltVoiceConfig prebuiltVoiceConfig) {
    this.prebuiltVoiceConfig = prebuiltVoiceConfig;
  }

  public GeminiVoiceConfig(String voiceName) {
    this.prebuiltVoiceConfig = new GeminiPrebuiltVoiceConfig(voiceName);
  }

  public GeminiPrebuiltVoiceConfig getPrebuiltVoiceConfig() {
    return prebuiltVoiceConfig;
  }

  public void setPrebuiltVoiceConfig(GeminiPrebuiltVoiceConfig prebuiltVoiceConfig) {
    this.prebuiltVoiceConfig = prebuiltVoiceConfig;
  }
}
