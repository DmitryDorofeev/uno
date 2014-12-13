package frontend;

import base.AuthService;
import base.LogOutServlet;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexey on 25.10.2014.
 */
public class LogOutServletImpl extends HttpServlet implements LogOutServlet {
    private AuthService authService;

    public LogOutServletImpl(AuthService authService) {
        this.authService = authService;
    }

    public void doPost(HttpServletRequest request,
                HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        JSONObject jsonObj = new JSONObject();
        if (authService.isLoggedIn(sessionId)) {
            authService.logOut(sessionId, null);
            jsonObj.put("status", 200);
            response.getWriter().print(jsonObj.toJSONString());
            return;
        }
        jsonObj.put("status", 500);
        jsonObj.put("message", "User was not logged in");
        response.getWriter().print(jsonObj.toJSONString());
    }
}
