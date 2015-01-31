package utils;

import org.json.simple.JSONObject;

/**
 * Created by пользователь on 28.01.2015.
 */
public class LoggerHelper {
    public static void logJSON(String title, JSONObject jsonObject) {
        try {
            JSONObject result = new JSONObject();
            result.put("title", title);
            if (jsonObject == null)
                result.put("body", new JSONObject());
            else
                result.put("body", jsonObject);
            System.out.println(result.toJSONString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
