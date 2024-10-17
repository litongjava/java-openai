package com.litongjava.textin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Metric {
  private String status;
  private String image_id;
  private double durations;
  private double pageId;
}