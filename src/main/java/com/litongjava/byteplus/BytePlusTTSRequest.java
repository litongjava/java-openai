package com.litongjava.byteplus;

public class BytePlusTTSRequest {
  private BytePlusUser user;
  private BytePlusReqParams req_params;

  public BytePlusUser getUser() {
    return user;
  }

  public void setUser(BytePlusUser user) {
    this.user = user; 
  }

  public BytePlusReqParams getReq_params() {
    return req_params;
  }

  public void setReq_params(BytePlusReqParams req_params) {
    this.req_params = req_params;
  }
}
