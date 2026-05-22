package nexus.io.volcengine;

public class VolcTtsResult {
  private String reqid;
  private String encoding;
  private String base64Data;
  private byte[] audioBytes;
  private VolcTtsResponse response;
  private String rawData;

  public String getReqid() {
    return reqid;
  }

  public void setReqid(String reqid) {
    this.reqid = reqid;
  }

  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  public String getBase64Data() {
    return base64Data;
  }

  public void setBase64Data(String base64Data) {
    this.base64Data = base64Data;
  }

  public byte[] getAudioBytes() {
    return audioBytes;
  }

  public void setAudioBytes(byte[] audioBytes) {
    this.audioBytes = audioBytes;
  }

  public VolcTtsResponse getResponse() {
    return response;
  }

  public void setResponse(VolcTtsResponse response) {
    this.response = response;
  }

  public String getRawData() {
    return rawData;
  }

  public void setRawData(String rawData) {
    this.rawData = rawData;
  }
}
