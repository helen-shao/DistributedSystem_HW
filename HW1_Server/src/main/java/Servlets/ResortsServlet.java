package Servlets;

import com.google.gson.Gson;
import com.sun.media.jfxmedia.logging.Logger;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ResortServlet")
public class ResortsServlet extends HttpServlet {
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    String urlPath = req.getPathInfo();

    // check we have a URL!
    if (urlPath == null || urlPath.isEmpty()) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      res.getWriter().write("missing paramterers");
      return;
    }

    String[] urlParts = urlPath.split("/");
    // and now validate url path and return the response status code
    // (and maybe also some value if input is valid)

    if (!isResortURLValid(urlParts)) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      // do any sophisticated processing with urlParts which contains all the url params
      // TODO: process url params in `urlParts`
      //res.getWriter().write("Resort Servlet works!");
      String response = "Get Successful";
      String json = new Gson().toJson(response);
      res.getWriter().write(json);
    }
  }

  protected void postGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    String urlPath = req.getPathInfo();

    // check we have a URL!
    if (urlPath == null || urlPath.isEmpty()) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      res.getWriter().write("missing paramterers");
      return;
    }

    String[] urlParts = urlPath.split("/");
    // and now validate url path and return the response status code
    // (and maybe also some value if input is valid)

    if (!isResortURLValid(urlParts)) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      // do any sophisticated processing with urlParts which contains all the url params
      // TODO: process url params in `urlParts`
      //res.getWriter().write("Resort Servlet works!");
      String response = "Post Successful";
      String json = new Gson().toJson(response);
      res.getWriter().write(json);
    }
  }

  private boolean isResortURLValid(String[] urlPath) {
//    if (urlPath.length == 1) {
//      return true;
//    } else if (urlPath.length == 4) {
//      String skiers = urlPath[1];
//      String resortID = urlPath[2];
//      String seasons = urlPath[3];
//      if (!validate32Int(resortID)) return false;
//    }
    return true;
  }

  private Integer strToNum(String str) {
    Integer num = null;
    try {
      num = Integer.parseInt(str);
    } catch (NumberFormatException nfe) {
      System.out.println("Wrong Format");
    } catch (NullPointerException npe) {
      System.out.println("Null Pointer");
    }
    return num;
  }
  private boolean validate32Int(String str) {
    if (strToNum(str) == null) return false;
    return true;
  }
//  private boolean validateYear(String str) {
//    Integer year = strToNum(str);
//    if (year == null) return false;
//    // year validation
//    if (year.intValue() < 1500 || year.intValue() > 3000) return false;
//    return true;
//  }
//  private boolean validateDay(String str) {
//    Integer day = strToNum(str);
//    if (day == null) return false;
//    // year validation
//    if (day.intValue() < 1 || day.intValue() > 366) return false;
//    return true;
//  }

}
