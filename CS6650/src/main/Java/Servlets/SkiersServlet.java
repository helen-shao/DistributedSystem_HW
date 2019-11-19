package Servlets;

import Database.DataSource;
import Database.QueryStatements;
import Metrics.Cacher;
import Metrics.PostCache;
import Metrics.RuntimeStatistics;
import Model.APISupported;
import Model.LiftRide;
import Model.LiftRideQuery;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp2.BasicDataSource;

import static Servlets.SetResponse.setMissingParameter;
import static Servlets.ValidateSkiersURL.hasURL;

@WebServlet(name = "SkiersServlet")
public class SkiersServlet extends HttpServlet {

//  private static final Logger LOGGER = Logger.getLogger(SkiersServlet.class.getName());
  static {
    Cacher.start();
  }

  protected static Map<APISupported, RuntimeStatistics> statisticsMap = new ConcurrentHashMap<>();

  protected void doPost(HttpServletRequest req,
      HttpServletResponse res)
      throws ServletException, IOException {
    res.setContentType("application/json");
    long startTime = System.currentTimeMillis();
    String urlPath = req.getPathInfo();
    if (!hasURL(urlPath)) {
      setMissingParameter(res);
      return;
    }
    BufferedReader reader = req.getReader();
    Gson gson = new Gson();
    LiftRide liftRide = gson.fromJson(reader, LiftRide.class);
    String[] urlParts = urlPath.split("/");
    if (!ValidateSkiersURL.isSkierURLValid(urlParts)) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } else {
      LiftRideQuery liftRideQuery = new LiftRideQuery(urlParts, liftRide);
      PostCache.addToCache(liftRideQuery);
      res.setStatus(HttpServletResponse.SC_CREATED);
    }
    long endTime = System.currentTimeMillis();
    long latency = endTime - startTime;
    if (!statisticsMap.containsKey(APISupported.PostLiftRide)) {
      statisticsMap.put(APISupported.PostLiftRide,  new RuntimeStatistics());
    }
    statisticsMap.get(APISupported.PostLiftRide).updateStats(latency);
  }

  protected void doGet(HttpServletRequest req,
      HttpServletResponse res)
      throws ServletException, IOException {
    res.setContentType("application/json");

    String urlPath = req.getPathInfo();
    // check we have a URL!
    if (!hasURL(urlPath)) {
      setMissingParameter(res);
      return;
    }
    String[] urlParts = urlPath.split("/");
    Statement statement = null;
    Connection skierConnection = null;
    ResultSet resultSet = null;
    // check if the url is valid
    if (!ValidateSkiersURL.isSkierURLValid(urlParts)) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } else if (ValidateSkiersURL.isGetVertical(urlParts)) {
      try {
        BasicDataSource basicDS = DataSource.getInstance().getBasicDS();
        skierConnection = basicDS.getConnection();
        statement = skierConnection.createStatement();
        LiftRideQuery liftRideQuery = new LiftRideQuery(urlParts, null);
        resultSet = QueryStatements.getSumOfVerticals(liftRideQuery);
        StringBuilder sb = new StringBuilder();
        while (resultSet.next()) {
          String result = resultSet.getString(1);
          sb.append(result);
        }
        res.setStatus(HttpServletResponse.SC_OK);
//        res.getWriter().write("ResultSet: " + sb.toString());
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
  }
