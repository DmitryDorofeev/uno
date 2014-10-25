package frontend;

import base.AuthService;
import base.SignInServlet;
import base.UserProfile;
import base.UserProfileServlet;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexey on 25.10.2014.
 */
public class SignInServletImpl extends HttpServlet implements SignInServlet {
    private AuthService authService;

    public SignInServletImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");

        JSONObject jsonObj = new JSONObject();
        response.setStatus(HttpServletResponse.SC_OK);
        if (!login.isEmpty() && !password.isEmpty()) {
            final String sessionId = request.getSession().getId();
            if (authService.isLoggedIn(sessionId)) {
                jsonObj.put("status", 500);
                jsonObj.put("message", "User has already logged in");
                response.getWriter().print(jsonObj.toJSONString());
                return;
            }
            if (authService.signIn(sessionId, login, password)) {
                jsonObj.put("status", 200);
                UserProfile user = authService.getUserProfile(sessionId);
                jsonObj.put("login", user.getLogin());
                jsonObj.put("email", user.getEmail());
                response.getWriter().print(jsonObj.toJSONString());
                return;
            }
            jsonObj.put("status", 403);
            jsonObj.put("message", "Wrong login or password!");
            response.getWriter().print(jsonObj.toJSONString());
            return;
        }
    }
}
