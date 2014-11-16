package admin;

import base.AdminPageServlet;
import base.AuthService;
import base.UserProfile;
import db.DBService;
import org.json.simple.JSONObject;
import utils.TimeHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexey on 25.10.2014.
 */
public class AdminPageServletImpl extends HttpServlet implements AdminPageServlet {
    private AuthService authService;

    public AdminPageServletImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        final String sessionId = request.getSession().getId();
        UserProfile user = authService.getUserProfile(sessionId);

        JSONObject jsonObj = new JSONObject();

        if (user != null && user.getLogin().equals("admin")) {
            response.setContentType("text/html; charset = utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            final String timeString = request.getParameter("shutdown");
            if (timeString != null) {
                final int timeMS = Integer.valueOf(timeString);
                System.out.print("Server will be down after: " + timeMS + " ms");
                TimeHelper.sleep(timeMS);
                System.out.print("\nShutdown");
                System.exit(0);
            }

            jsonObj.put("code", 200);
            jsonObj.put("amountOfRegisteredUsers", DBService.instance().getAmountOfRegisteredUsers());
            jsonObj.put("amountOfUsersOnline", authService.getAmountOfUsersOnline());
            response.getWriter().print(jsonObj.toJSONString());
            return;
        }

        jsonObj.put("code", 500);
        jsonObj.put("message", "Access denied");
        response.getWriter().print(jsonObj.toJSONString());
    }
}
