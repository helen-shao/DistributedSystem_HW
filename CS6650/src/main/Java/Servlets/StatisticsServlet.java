package Servlets;

import Metrics.GenerateAPIStatsEndpointStats;
import Metrics.RuntimeStatistics;
import Model.APIStats;
import Model.APIStatsEndpointStats;
import Model.APISupported;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static Servlets.SkiersServlet.statisticsMap;

@WebServlet(name = "StatisticsServlet")
public class StatisticsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        APIStats apiStats = new APIStats();
        for (Map.Entry<APISupported, RuntimeStatistics> entry: statisticsMap.entrySet()) {
            APIStatsEndpointStats apiStatsEndpointStats =
                GenerateAPIStatsEndpointStats.generate(entry.getKey(), entry.getValue());
            apiStats.addEndpointStatsItem(apiStatsEndpointStats);
        }
        Gson gson = new Gson();
        String apiStatsJson = gson.toJson(apiStats);
        res.getWriter().print(apiStatsJson);
        res.getWriter().flush();
        res.setStatus(HttpServletResponse.SC_OK);
    }
}