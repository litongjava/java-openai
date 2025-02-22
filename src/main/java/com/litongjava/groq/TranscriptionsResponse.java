package com.litongjava.groq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranscriptionsResponse {
  private String text;
  private XGroq x_groq;
}
