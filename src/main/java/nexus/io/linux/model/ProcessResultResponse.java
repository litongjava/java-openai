package nexus.io.linux.model;

import lombok.Data;

/** Nullable code distinguishes legacy process results from response envelopes. */
@Data
public class ProcessResultResponse {
  private Integer code;
  private Object data;
  private String msg;
}
