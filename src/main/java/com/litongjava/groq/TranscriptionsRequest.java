package com.litongjava.groq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TranscriptionsRequest {
  private String model = "whisper-large-v3-turbo";
  private Float temperature;
  private String response_format = "json";
  private String language;
}
