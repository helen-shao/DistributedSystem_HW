package Metrics;

import Database.QueryStatements;
import Model.LiftRideQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Cacher {
    private static final int POST_CYCLE = 1000;
    private static final int BATCH_SIZE = 5000;
    private static long startTime = System.currentTimeMillis();

    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    public static void start() {
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    BatchPost();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, 0, POST_CYCLE, TimeUnit.MILLISECONDS);
    }

    private static void BatchPost() throws SQLException {
        long endTime = System.currentTimeMillis();
        long timePeriod = endTime - startTime;
        if (PostCache.getSize() > BATCH_SIZE || (timePeriod > POST_CYCLE && PostCache.getSize() != 0)) {
            List<LiftRideQuery> cachedPost = PostCache.getCacheList();
            QueryStatements.batchPostLiftRides(cachedPost);
            QueryStatements.batchPostSkierVerticals(cachedPost);
            PostCache.cleanCache();
            startTime = endTime;
        }
    }
}
