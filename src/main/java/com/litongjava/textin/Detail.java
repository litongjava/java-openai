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
public class Detail {
  private int outline_level;
  private String image_url;
  private String text;
  private long pageId;
  private int content;
  private long paragraph_id;
  private String type;
  private List<Long> position;
}
