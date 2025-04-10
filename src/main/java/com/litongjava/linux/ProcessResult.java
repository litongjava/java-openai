package com.litongjava.linux;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessResult {
  private long taskId;
  private int exitCode;
  private String executeCode;
  private String stdOut;
  private String stdErr;
  private String output;
  private List<String> images;
  private List<String> videos;
}