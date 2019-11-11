package Servlets;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SetResponse {

    protected static void setMissingParameter(HttpServletResponse res) throws IOException {
        res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        res.getWriter().write("missing paramterers");
    }
}
