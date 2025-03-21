package com.litongjava.linux;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessResult {
  private int exitCode;
  private String stdOut;
  private String stdErr;
  private List<String> images;
}