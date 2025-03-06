package com.litongjava.searchapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchapiParameters {
  private String engine;
  private String q;
  private String device;
  private String google_domain;
  private String hl;
  private String gl;
}
