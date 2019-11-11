package io.swagger.client;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class GenerateStatistics {

  public static void generateStatistics(BlockingQueue<RequestData> blockingQueue, int numRequestSuccessful, int numRequestFail, long wallTime) {

    DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
    Iterator<RequestData> iterator = blockingQueue.iterator();
    while (iterator.hasNext()) {
      RequestData cur = iterator.next();
      descriptiveStatistics.addValue((double) cur.getLatency());
    }
    double mean = descriptiveStatistics.getMean();
    double median = descriptiveStatistics.getPercentile(50);
    double percentile99 = descriptiveStatistics.getPercentile(99);
    double standardDeviation = descriptiveStatistics.getStandardDeviation();
    double max = descriptiveStatistics.getMax();

    System.out.println("Latency mean = " + mean);
    System.out.println("Latency median = " + median);
    System.out.println("Latency percentile99 = " + percentile99);
    System.out.println("Latency max = " + max);
    System.out.println("Latency standardDeviation = " + standardDeviation);

  }
}
