package frontend;

import base.AuthService;
import base.SignUpServlet;
import db.UserProfile;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexey on 25.10.2014.
 */
public class SignUpServletImpl extends HttpServlet implements SignUpServlet {
    private AuthService authService;

    public SignUpServletImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        final String email = request.getParameter("email");

        response.setStatus(HttpServletResponse.SC_OK);

        JSONObject jsonObj = new JSONObject();
        if (!login.isEmpty() && !password.isEmpty()) {
            UserProfile user = new UserProfile(login, password, email);
            if (authService.signUp(user)) {
                jsonObj.put("status", 200);
                response.getWriter().print(jsonObj.toJSONString());
                return;
            }
            jsonObj.put("status", 500);
            jsonObj.put("message", "Player with login " + login + " is already registered");
            response.getWriter().print(jsonObj.toJSONString());
            return;
        }
        jsonObj.put("status", 500);
        jsonObj.put("message", "Not all fields are filled");
        response.getWriter().print(jsonObj.toJSONString());
    }
}