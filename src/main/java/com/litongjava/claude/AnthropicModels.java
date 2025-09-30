package com.litongjava.claude;

/**
 * https://docs.anthropic.com/en/docs/about-claude/models/overview
 * 
 * @author Tong Li
 *
 */
public interface AnthropicModels {
  String CLAUDE_SONNET_4_5 = "claude-sonnet-4-5";
  String CLAUDE_SONNET_4_5_20250929 = "claude-sonnet-4-5-20250929";

  // Claude 4.1
  String CLAUDE_OPUS_4_1 = "claude-opus-4-1";
  String CLAUDE_OPUS_4_1_20250805 = "claude-opus-4-1-20250805";

  // Claude 4.0
  String CLAUDE_SONNET_4_0 = "claude-sonnet-4-0";
  String CLAUDE_SONNET_4_20250514 = "claude-sonnet-4-20250514";
  String CLAUDE_OPUS_4_0 = "claude-opus-4-0";
  String CLAUDE_OPUS_4_20250514 = "claude-opus-4-20250514";
  // Claude 3.7
  String CLAUDE_3_7_SONNET_20250219 = "claude-3-7-sonnet-20250219";

  // Claude 3.5
  String CLAUDE_3_5_SONNET_20241022 = "claude-3-5-sonnet-20241022";
  String CLAUDE_3_5_HAIKU_20241022 = "claude-3-5-haiku-20241022";
  String CLAUDE_3_5_SONNET_20240620 = "claude-3-5-sonnet-20240620";

  // Claude 3
  String CLAUDE_3_HAIKU_20240307 = "claude-3-haiku-20240307";
  String CLAUDE_3_OPUS_20240229 = "claude-3-opus-20240229";
}