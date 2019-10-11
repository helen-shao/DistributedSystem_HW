package io.swagger.client;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueToCSV {

  public static void writeToCSV(String filePath, BlockingQueue<RequestData> blockingQueue) {
    try {
      FileWriter outputfile = new FileWriter(filePath);
      Iterator<RequestData> iterator = blockingQueue.iterator();
      while (iterator.hasNext()) {
        RequestData cur = iterator.next();
        outputfile.append(Long.toString(cur.getTimestamp()));
        outputfile.append(",");
        outputfile.append(Long.toString(cur.getLatency()));
        outputfile.append(",");
        outputfile.append(Integer.toString(cur.getResult()));
        outputfile.append(",");
        outputfile.append(cur.getRequestType());
        outputfile.append("\n");
      }
      outputfile.flush();
      outputfile.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
