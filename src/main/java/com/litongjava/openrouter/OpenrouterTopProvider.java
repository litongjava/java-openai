package com.litongjava.openrouter;

public class OpenrouterTopProvider {

  private long context_length;
  private int max_completion_tokens;
  private boolean is_moderated;

  public OpenrouterTopProvider() {
  }

  public OpenrouterTopProvider(long context_length, int max_completion_tokens, boolean is_moderated) {
    this.context_length = context_length;
    this.max_completion_tokens = max_completion_tokens;
    this.is_moderated = is_moderated;
  }

  public long getContext_length() {
    return context_length;
  }

  public void setContext_length(long context_length) {
    this.context_length = context_length;
  }

  public int getMax_completion_tokens() {
    return max_completion_tokens;
  }

  public void setMax_completion_tokens(int max_completion_tokens) {
    this.max_completion_tokens = max_completion_tokens;
  }

  public boolean getIs_moderated() {
    return is_moderated;
  }

  public void setIs_moderated(boolean is_moderated) {
    this.is_moderated = is_moderated;
  }
}
