package io.swagger.client;

import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class PostThread implements Runnable {
  ApiClient client;
  SkiersApi apiInstance;
  CountDownLatch countDownLatch;
  CountDownLatch countDownLatchOverall;
  int numRuns;
  int numSkiers;
  double percentOfRequest;
  double percentofMaxThreads;
  BlockingQueue<RequestData> blockingQueue;
  int addSuccessful;
  int addFail;


  public PostThread(CountDownLatch countDownLatchOverall, CountDownLatch countDownLatch, String basePath, int numRuns, int numSkiers, double percentOfRequest, double percenofMaxThreads, BlockingQueue<RequestData> blockingQueue) {
    SkiersApi apiInstance = new SkiersApi();
    client = apiInstance.getApiClient();
    client.setBasePath(basePath);
    client.setReadTimeout(60000);
    client.setConnectTimeout(60000);
    this.countDownLatchOverall = countDownLatchOverall;
    this.apiInstance = apiInstance;
    this.countDownLatch = countDownLatch;
    this.numRuns = numRuns;
    this.numSkiers = numSkiers;
    this.percentOfRequest = percentOfRequest;
    this.percentofMaxThreads = percenofMaxThreads;
    this.blockingQueue = blockingQueue;
  }

  @Override
  public void run() {
//    String threadID  = Thread.currentThread().getName();
//    System.out.println("start: " + threadID);

    int postNum = (int) Math.ceil(numRuns * percentOfRequest * (numSkiers / (SkierApiMain.numThreads * percentofMaxThreads)));
    for (int i = 0; i < postNum; i++) {
      int skierID = ThreadLocalRandom.current().nextInt(1, numSkiers);
      int resortID = 1;
      String seasonID = "2019";
      String dayID = "1";
      LiftRide liftRide = new LiftRide();
      ApiResponse apiResponse = null;
      long startTime = System.currentTimeMillis();
      try {
        apiResponse = apiInstance.writeNewLiftRideWithHttpInfo(liftRide, resortID, seasonID, dayID, skierID);
        long endTime = System.currentTimeMillis();
        long latency = endTime - startTime;
        RequestData requestData = new RequestData(startTime, latency, apiResponse.getStatusCode(), "writeNewLiftRide");
        if (SkierApiMain.enableBlockingQueue) blockingQueue.add(requestData);
        if (apiResponse.getStatusCode() == 200 || apiResponse.getStatusCode() == 201) addSuccessful++;
        else addFail++;
      } catch (ApiException e) {
        e.printStackTrace();
        addFail++;
      }
    }
//    System.out.println("end: " + threadID);
    incrementSuccessful(addSuccessful);
    incrementFail(addFail);
    countDownLatch.countDown();
    countDownLatchOverall.countDown();
  }

  private synchronized void incrementSuccessful(int add) {
    SkierApiMain.numRequestSuccessful += add;
  }
  private synchronized void incrementFail(int add) {
    SkierApiMain.numRequestFail += add;
  }
}

