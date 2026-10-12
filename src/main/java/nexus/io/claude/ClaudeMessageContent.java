package nexus.io.claude;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSON;
import lombok.Getter;
import lombok.Setter;
import nexus.io.chat.ChatImageFile;
import nexus.io.tio.utils.hutool.StrUtil;

public class ClaudeMessageContent {
  private String type;
  private String text;
  private ClaudeCacheControl cache_control;
  private ClaudeChatSource source;
  @Getter @Setter
  private String id;
  @Getter @Setter
  private String name;
  @Getter @Setter
  private Map<String, Object> input;
  @Getter @Setter
  private String tool_use_id;
  /** Array of search results on success; a single error object on failure. */
  @Getter @Setter
  private Object content;
  @Getter @Setter
  private List<ClaudeWebSearchCitation> citations;

  /** Not a bean getter: derived data must not be sent when replaying content blocks. */
  public List<ClaudeWebSearchResult> webSearchResults() {
    if (!"web_search_tool_result".equals(type) || !(content instanceof List)) {
      return Collections.emptyList();
    }
    List<ClaudeWebSearchResult> results = new ArrayList<>();
    for (Object item : (List<?>) content) {
      ClaudeWebSearchResult result = JSON.parseObject(JSON.toJSONString(item), ClaudeWebSearchResult.class);
      if (result != null && "web_search_result".equals(result.getType())) {
        results.add(result);
      }
    }
    return results;
  }

  /** Returns the embedded search error, or null for successful/other blocks. */
  public ClaudeWebSearchError webSearchError() {
    if (!"web_search_tool_result".equals(type) || content == null || content instanceof List) {
      return null;
    }
    ClaudeWebSearchError error = JSON.parseObject(JSON.toJSONString(content), ClaudeWebSearchError.class);
    return error != null && "web_search_tool_result_error".equals(error.getType()) ? error : null;
  }

  public ClaudeMessageContent(String type, String text) {
    this.type = type;
    this.text = text;
  }

  public ClaudeMessageContent(ChatImageFile file) {
    this.type = "image";
    if (StrUtil.isNotBlank(file.getUrl())) {
      this.source = ClaudeChatSource.url(file.getUrl());

    } else if (StrUtil.isNotBlank(file.getData())) {
      this.source = ClaudeChatSource.base64(file.getMimeType(), file.getData());
    }

    if (file.isCached()) {
      this.cache_control = new ClaudeCacheControl();
    }

  }

  public ClaudeMessageContent() {
    super();
  }

  public ClaudeMessageContent(String type, String text, ClaudeCacheControl cache_control, ClaudeChatSource source) {
    super();
    this.type = type;
    this.text = text;
    this.cache_control = cache_control;
    this.source = source;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public ClaudeCacheControl getCache_control() {
    return cache_control;
  }

  public void setCache_control(ClaudeCacheControl cache_control) {
    this.cache_control = cache_control;
  }

  public ClaudeChatSource getSource() {
    return source;
  }

  public void setSource(ClaudeChatSource source) {
    this.source = source;
  }

}
