package io.swagger.client;
import java.util.concurrent.*;


public class Phase {

  public static void Phase(CountDownLatch countDownLatchOverall,
                           CountDownLatch countDownLatch,
                           int threadsNumCurrent,
                           String basePath,
                           int numRuns,
                           int numSkiers,
                           double percentOfRequest,
                           double percenofMaxThreads,
                           BlockingQueue<RequestData> blockingQueue)
      throws ApiException, BrokenBarrierException, InterruptedException {

//    ExecutorService executor = Executors.newFixedThreadPool(threadsNumCurrent);
    for (int i = 0; i < threadsNumCurrent; i++) {
//      Thread thread = new Thread(new PostThread(countDownLatchOverall, countDownLatch, basePath, numRuns, numSkiers, percentOfRequest, percenofMaxThreads, blockingQueue));
//      executor.execute(thread);
      new Thread(new PostThread(countDownLatchOverall, countDownLatch, basePath, numRuns, numSkiers, percentOfRequest, percenofMaxThreads, blockingQueue)).start();
    }
//    executor.shutdown();
  }
}
