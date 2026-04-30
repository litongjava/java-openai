package nexus.io.gemini;

/**
 * Gemini TTS 预置语音配置
 * 例如: { "voiceName": "Kore" }
 */
public class GeminiPrebuiltVoiceConfig {
  private String voiceName;

  public GeminiPrebuiltVoiceConfig() {
  }

  public GeminiPrebuiltVoiceConfig(String voiceName) {
    this.voiceName = voiceName;
  }

  public String getVoiceName() {
    return voiceName;
  }

  public void setVoiceName(String voiceName) {
    this.voiceName = voiceName;
  }
}
