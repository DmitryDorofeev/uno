package base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexey on 25.10.2014.
 */
public interface AdminPageServlet {
    static final String adminPageURL = "/admin";

    void doGet(HttpServletRequest request,
               HttpServletResponse response) throws ServletException, IOException;
}
