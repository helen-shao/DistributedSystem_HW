package Metrics;

import Model.LiftRideQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostCache {
    private static List<LiftRideQuery> cacheList = Collections.synchronizedList(new ArrayList<LiftRideQuery>());

    public synchronized static void addToCache(LiftRideQuery liftRideQuery) {
        cacheList.add(liftRideQuery);
    }

    public synchronized static List<LiftRideQuery> getCacheList() {
        List<LiftRideQuery> res = cacheList;
        res = Collections.synchronizedList(new ArrayList<LiftRideQuery>());
        return cacheList;
    }

    public synchronized static int getSize() {
        return cacheList.size();
    }

    public synchronized static void cleanCache() {
        cacheList = Collections.synchronizedList(new ArrayList<LiftRideQuery>());
    }
}
