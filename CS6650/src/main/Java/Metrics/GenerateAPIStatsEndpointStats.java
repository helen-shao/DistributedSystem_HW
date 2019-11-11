package Metrics;

import Model.APIStatsEndpointStats;
import Model.APISupported;

public class GenerateAPIStatsEndpointStats {
    public static APIStatsEndpointStats generate(APISupported apiSupported, RuntimeStatistics runtimeStatistics) {
        APIStatsEndpointStats apiStatsEndpointStats = new APIStatsEndpointStats();
        apiStatsEndpointStats.setMax((int) runtimeStatistics.getMax());
        apiStatsEndpointStats.setMean((int) runtimeStatistics.getMean());
        apiStatsEndpointStats.setOperation(apiSupported.toString());
        apiStatsEndpointStats.setURL(apiSupported.getUrl());
        return apiStatsEndpointStats;
    }
}
