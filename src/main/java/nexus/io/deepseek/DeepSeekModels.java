package nexus.io.deepseek;

public interface DeepSeekModels {
  // 默认最大输出长度为 4K。请调整 max_tokens 以支持更长的输出
  // DeepSeek-V3
  String DEEPSEEK_CHAT = "deepseek-chat";
  // DeepSeek-R1。
  String DEEPSEEK_REASONER = "deepseek-reasoner";

  String deepseek_v4_flash = "deepseek-v4-flash";
  String deepseek_v4_flash_vision_exp = "deepseek-v4-flash-vision-exp";
  String deepseek_v4_pro = "deepseek-v4-pro";
}
