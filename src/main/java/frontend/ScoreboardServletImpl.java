package frontend;

import base.AuthService;
import base.ScoreboardServlet;
import db.DBService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.LoggerHelper;

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
    private DBService dbService;

    public ScoreboardServletImpl(DBService dbService) {
        this.dbService = dbService;
    }

    public void doGet(HttpServletRequest request,
               HttpServletResponse response) throws ServletException, IOException {

        String limitString = request.getParameter("limit");
        String offsetString = request.getParameter("offset");

        int limit;
        int offset;
        try {
            if (limitString == null)
                limit = 10;
            else
                limit = Integer.getInteger(limitString);

            if (offsetString == null)
                offset = 0;
            else
                offset = Integer.getInteger(offsetString);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", 200);
            JSONObject jsonBody = new JSONObject();
            jsonObject.put("body", jsonBody);
            JSONArray jsonArray = new JSONArray();
            jsonBody.put("scores", jsonArray);
            for (Object o : dbService.getScoreboard(offset, limit).entrySet()) {
                Map.Entry<String, Long> pair = (Map.Entry) o;
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("login", pair.getKey());
                jsonObj.put("score", pair.getValue());
                jsonArray.add(jsonObj);
            }
            LoggerHelper.logJSON("scores", jsonObject);
            response.getWriter().print(jsonObject.toJSONString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
