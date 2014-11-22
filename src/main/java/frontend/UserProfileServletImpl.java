package frontend;

import base.AuthService;
import db.UserProfile;
import base.UserProfileServlet;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexey on 25.10.2014.
 */
public class UserProfileServletImpl extends HttpServlet implements UserProfileServlet {
    private AuthService authService;

    public UserProfileServletImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        final String sessionId = request.getSession().getId();

        response.setStatus(HttpServletResponse.SC_OK);
        JSONObject jsonObj = new JSONObject();
        if (!authService.isLoggedIn(sessionId)) {
            jsonObj.put("status", 500);
            jsonObj.put("message", "User has not logged in");
            response.getWriter().print(jsonObj.toJSONString());
            return;
        }
        UserProfile user = authService.getUserProfile(sessionId);
        jsonObj.put("status", 200);
        jsonObj.put("login", user.getLogin());
        jsonObj.put("email", user.getEmail());
        response.getWriter().print(jsonObj.toJSONString());
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        //TODO userExists? => change

    }
}
