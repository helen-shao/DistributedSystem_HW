package io.swagger.client;

import java.util.ArrayList;
import java.util.List;

public class RequestData implements Comparable<RequestData> {
  private long timestamp;
  private long latency;
  private int result;
  private String requestType;

  public RequestData(long timestamp,long latency, int result, String requestType){
    this.timestamp = timestamp;
    this.latency = latency;
    this.result = result;
    this.requestType = requestType;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public long getLatency() {
    return latency;
  }

  public int getResult() {
    return result;
  }

  public String getRequestType() {
    return requestType;
  }

  @Override
  public int compareTo(RequestData o) {
    Long thisTimeStamp = this.timestamp;
    Long otherTimeStamp = ((RequestData) o).getTimestamp();
    return thisTimeStamp.compareTo(otherTimeStamp);
  }

  public List<String> toRowData() {
    List<String> list = new ArrayList<>();
    list.add(Long.toString(timestamp));
    list.add(requestType);
    list.add(Integer.toString(getResult()));
    list.add(Long.toString(latency));
    return list;
  }
}
