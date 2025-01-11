package com.litongjava.textin;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PdfToMarkdownResponse {
  private int code;
  private String message;
  private TextInResult result;
  private String version;
  private long duration;
  private List<Metric> metrics;
}