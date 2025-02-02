package com.litongjava.search;

import java.util.List;

import com.litongjava.openai.chat.SearchReturnImage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchChatResponse {
  private String content;
  private List<WebPageSource> citations;
  private List<SearchReturnImage> images;
}
