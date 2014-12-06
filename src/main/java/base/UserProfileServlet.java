package base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexey on 25.10.2014.
 */
public interface UserProfileServlet {
    static final String userProfilePageURL = "/api/v1/auth/profile";

    void doGet(HttpServletRequest request,
               HttpServletResponse response) throws ServletException, IOException;

    void doPost(HttpServletRequest request,
               HttpServletResponse response) throws ServletException, IOException;
}
