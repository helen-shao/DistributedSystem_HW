package io.swagger.client;

import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import java.util.LinkedList;
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
  BlockingQueue<RequestData> blockingQueuePost;
  BlockingQueue<RequestData> blockingQueueGet;
  LinkedList<RequestData> localQueuePost;
  LinkedList<RequestData> localQueueGet;
  int addSuccessfulPost;
  int addFailPost;
  int addSuccessfulGet;
  int addFailGet;
  int[] timeRange;

  public PostThread(CountDownLatch countDownLatchOverall, CountDownLatch countDownLatch,
      String basePath, int numRuns, int numSkiers,
      double percentOfRequest, double percenofMaxThreads,
      BlockingQueue<RequestData> blockingQueuePost,
      BlockingQueue<RequestData> blockingQueueGet,
      int[] timeRange) {
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
    this.blockingQueuePost = blockingQueuePost;
    this.blockingQueueGet = blockingQueueGet;
    this.localQueuePost = new LinkedList<>();
    this.localQueueGet = new LinkedList<>();
    this.timeRange = timeRange;
  }

  @Override
  public void run() {
    String threadID  = Thread.currentThread().getName();
//    System.out.println("start: " + threadID);
    int postNum = (int) Math.ceil(numRuns * percentOfRequest * (numSkiers / (SkierApiMain.numThreads * percentofMaxThreads)));
    for (int i = 0; i < postNum; i++) {
      int skierID = ThreadLocalRandom.current().nextInt(1, numSkiers);
      int resortID = ThreadLocalRandom.current().nextInt(1,11);
      String seasonID = "" + ThreadLocalRandom.current().nextInt(2000,2020);
      String dayID = "" + ThreadLocalRandom.current().nextInt(1,367);
      LiftRide liftRide = new LiftRide();
      liftRide.setLiftID(ThreadLocalRandom.current().nextInt(1,41));
      liftRide.setTime(ThreadLocalRandom.current().nextInt(timeRange[0], timeRange[1]));
      ApiResponse apiResponse = null;
      try {
        long startTime = System.currentTimeMillis();
        apiResponse = apiInstance.writeNewLiftRideWithHttpInfo(liftRide, resortID, seasonID, dayID, skierID);
        long endTime = System.currentTimeMillis();
        long latency = endTime - startTime;
        RequestData requestDataPost = new RequestData(startTime, latency, apiResponse.getStatusCode(), "writeNewLiftRideWithHttpInfo");
        if (SkierApiMain.enableBlockingQueue) localQueuePost.add(requestDataPost);
        if (apiResponse.getStatusCode() == 200 || apiResponse.getStatusCode() == 201) addSuccessfulPost++;
        else addFailPost++;
        //=======================================
        // this is phase 3, needs to modify
        if (timeRange[0] == 361) {
          startTime = System.currentTimeMillis();
          apiResponse = apiInstance.getSkierDayVerticalWithHttpInfo(resortID, seasonID, dayID, skierID);
          endTime = System.currentTimeMillis();
          latency = endTime - startTime;
          RequestData requestDataGet = new RequestData(startTime, latency, apiResponse.getStatusCode(), "getSkierDayVerticalWithHttpInfo");
          if (SkierApiMain.enableBlockingQueue) localQueueGet.add(requestDataGet);
          if (apiResponse.getStatusCode()  == 200 || apiResponse.getStatusCode() == 201) addSuccessfulGet++;
          else addFailGet++;
        }
        //=======================================
      } catch (ApiException e) {
        e.printStackTrace();
        addFailPost++;
      }
    }
    blockingQueuePost.addAll(localQueuePost);
    blockingQueueGet.addAll(localQueueGet);
    incrementSuccessfulPost(addSuccessfulPost);
    incrementFailPost(addFailPost);
    incrementSuccessfulGet(addSuccessfulGet);
    incrementFailGet(addFailGet);
    countDownLatch.countDown();
    countDownLatchOverall.countDown();
//    System.out.println("end: " + threadID);

  }

  private synchronized void incrementSuccessfulPost(int add) {
    SkierApiMain.numRequestSuccessfulPost += add;
  }
  private synchronized void incrementFailPost(int add) {
    SkierApiMain.numRequestFailPost += add;
  }

  private synchronized void incrementSuccessfulGet(int add) {
    SkierApiMain.numRequestSuccessfulGet += add;
  }
  private synchronized void incrementFailGet(int add) {
    SkierApiMain.numRequestFailGet += add;
  }
}

