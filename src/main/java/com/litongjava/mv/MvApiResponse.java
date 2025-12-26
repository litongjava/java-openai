package com.litongjava.mv;

import lombok.Data;

@Data
public class MvApiResponse<T> {
  private int status;
  private T body;
}
