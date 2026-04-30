package nexus.io.gemini;

/**
 * Gemini TTS 多说话人模式下的单个说话人配置
 * 例如: { "speaker": "Joe", "voiceConfig": { "prebuiltVoiceConfig": { "voiceName": "Kore" } } }
 */
public class GeminiSpeakerVoiceConfig {
  private String speaker;
  private GeminiVoiceConfig voiceConfig;

  public GeminiSpeakerVoiceConfig() {
  }

  public GeminiSpeakerVoiceConfig(String speaker, GeminiVoiceConfig voiceConfig) {
    this.speaker = speaker;
    this.voiceConfig = voiceConfig;
  }

  public GeminiSpeakerVoiceConfig(String speaker, String voiceName) {
    this.speaker = speaker;
    this.voiceConfig = new GeminiVoiceConfig(voiceName);
  }

  public String getSpeaker() {
    return speaker;
  }

  public void setSpeaker(String speaker) {
    this.speaker = speaker;
  }

  public GeminiVoiceConfig getVoiceConfig() {
    return voiceConfig;
  }

  public void setVoiceConfig(GeminiVoiceConfig voiceConfig) {
    this.voiceConfig = voiceConfig;
  }
}
