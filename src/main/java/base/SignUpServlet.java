package base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexey on 25.10.2014.
 */
public interface SignUpServlet {
    static final String signUpPageURL = "/api/v1/auth/signup";

    void doPost(HttpServletRequest request,
                HttpServletResponse response) throws ServletException, IOException;
}
