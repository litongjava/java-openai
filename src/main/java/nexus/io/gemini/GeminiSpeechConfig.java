package nexus.io.gemini;

/**
 * Gemini TTS speechConfig
 * 单说话人: { "voiceConfig": { "prebuiltVoiceConfig": { "voiceName": "Kore" } } }
 * 多说话人: { "multiSpeakerVoiceConfig": { "speakerVoiceConfigs": [...] } }
 * 可选: languageCode, 例如 "en-US", "zh-CN"
 */
public class GeminiSpeechConfig {
  private GeminiVoiceConfig voiceConfig;
  private GeminiMultiSpeakerVoiceConfig multiSpeakerVoiceConfig;
  private String languageCode;

  public GeminiSpeechConfig() {
  }

  public GeminiSpeechConfig(GeminiVoiceConfig voiceConfig) {
    this.voiceConfig = voiceConfig;
  }

  public GeminiSpeechConfig(GeminiMultiSpeakerVoiceConfig multiSpeakerVoiceConfig) {
    this.multiSpeakerVoiceConfig = multiSpeakerVoiceConfig;
  }

  /**
   * 使用预置语音名快速构造单说话人配置
   */
  public static GeminiSpeechConfig prebuilt(String voiceName) {
    return new GeminiSpeechConfig(new GeminiVoiceConfig(voiceName));
  }

  public GeminiVoiceConfig getVoiceConfig() {
    return voiceConfig;
  }

  public void setVoiceConfig(GeminiVoiceConfig voiceConfig) {
    this.voiceConfig = voiceConfig;
  }

  public GeminiMultiSpeakerVoiceConfig getMultiSpeakerVoiceConfig() {
    return multiSpeakerVoiceConfig;
  }

  public void setMultiSpeakerVoiceConfig(GeminiMultiSpeakerVoiceConfig multiSpeakerVoiceConfig) {
    this.multiSpeakerVoiceConfig = multiSpeakerVoiceConfig;
  }

  public String getLanguageCode() {
    return languageCode;
  }

  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }
}
