package frontend;

import base.AuthService;
import base.SignInServlet;
import db.UserProfile;
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
    public void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();

        JSONObject jsonObj = new JSONObject();
        if (authService.isLoggedIn(sessionId) != 500) {
            UserProfile user = authService.getUserProfile(sessionId);
            jsonObj.put("status", 200);
            jsonObj.put("login", user.getLogin());
            response.getWriter().print(jsonObj.toJSONString());
            return;
        }
        jsonObj.put("status", 500);
        jsonObj.put("message", "User has not logged in");
        response.getWriter().print(jsonObj.toJSONString());
    }

        @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String token = request.getParameter("token");
        int status;
        if (login == null) {
            login = "";
        }
        if (password == null) {
            password = "";
        }
        if (token == null) {
            token = "";
        }
        JSONObject jsonObj = new JSONObject();
        response.setStatus(HttpServletResponse.SC_OK);

        if (!login.isEmpty() && !password.isEmpty()) {
            final String sessionId = request.getSession().getId();
            if (authService.isLoggedIn(sessionId) != 500) {
                jsonObj.put("status", 500);
                jsonObj.put("message", "User has already logged in");
                response.getWriter().print(jsonObj.toJSONString());
                return;
            }
            status = authService.signIn(sessionId, login, password);
            switch (status) {
                case 200:
                    jsonObj.put("status", 200);
                    UserProfile user = authService.getUserProfile(sessionId);
                    jsonObj.put("login", user.getLogin());
                    jsonObj.put("email", user.getEmail());
                    break;
                case 403:
                    jsonObj.put("status", 500);
                    jsonObj.put("message", "Wrong login or password");
                    break;
                case 404:
                    jsonObj.put("status", 500);
                    jsonObj.put("message", "User has not logged in");
                    break;
            }
            response.getWriter().print(jsonObj.toJSONString());
            return;
        }
        else if (!token.isEmpty()) {
            final String sessionId = request.getSession().getId();
            if (authService.isLoggedIn(sessionId) != 500) {
                jsonObj.put("status", 500);
                jsonObj.put("message", "User has already logged in");
                response.getWriter().print(jsonObj.toJSONString());
                return;
            }
            status = authService.signInByToken(sessionId, token);
            switch (status) {
                case 200:
                    jsonObj.put("status", 200);
                    UserProfile user = authService.getUserProfile(sessionId);
                    jsonObj.put("login", user.getLogin());
                    jsonObj.put("email", user.getEmail());
                    break;
                case 403:
                    jsonObj.put("status", 500);
                    jsonObj.put("message", "Wrong login or password");
                    break;
                case 404:
                    jsonObj.put("status", 500);
                    jsonObj.put("message", "User has not logged in");
                    break;
            }
        }
        jsonObj.put("status", 500);
        jsonObj.put("message", "Not all fields are filled");
        response.getWriter().print(jsonObj.toJSONString());
    }
}
