package nexus.io.gemini;

import java.util.List;

/**
 * Gemini TTS 多说话人语音配置
 * 例如: { "speakerVoiceConfigs": [ { "speaker": "Joe", "voiceConfig": {...} }, ... ] }
 */
public class GeminiMultiSpeakerVoiceConfig {
  private List<GeminiSpeakerVoiceConfig> speakerVoiceConfigs;

  public GeminiMultiSpeakerVoiceConfig() {
  }

  public GeminiMultiSpeakerVoiceConfig(List<GeminiSpeakerVoiceConfig> speakerVoiceConfigs) {
    this.speakerVoiceConfigs = speakerVoiceConfigs;
  }

  public List<GeminiSpeakerVoiceConfig> getSpeakerVoiceConfigs() {
    return speakerVoiceConfigs;
  }

  public void setSpeakerVoiceConfigs(List<GeminiSpeakerVoiceConfig> speakerVoiceConfigs) {
    this.speakerVoiceConfigs = speakerVoiceConfigs;
  }
}
