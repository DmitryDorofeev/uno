package frontend;

import base.AuthService;
import base.ScoreboardServlet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by alexey on 25.10.2014.
 */
public class ScoreboardServletImpl extends HttpServlet implements ScoreboardServlet {
    private AuthService authService;

    public ScoreboardServletImpl(AuthService authService) {
        this.authService = authService;
    }

    public void doGet(HttpServletRequest request,
               HttpServletResponse response) throws ServletException, IOException {
        JSONArray jsonArray = new JSONArray();
//        Iterator it = authService.getScoreboard().entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry<String, Integer> pair = (Map.Entry) it.next();
//            JSONObject jsonObj = new JSONObject();
//            jsonObj.put("login", pair.getKey());
//            jsonObj.put("score", pair.getValue());
//            jsonArray.add(jsonObj);
//        }
        response.getWriter().print(jsonArray.toJSONString());
    }
}
