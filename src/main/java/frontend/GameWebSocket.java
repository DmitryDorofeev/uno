package frontend;

import base.GameMechanics;
import mechanics.GameUser;
import base.WebSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

@WebSocket
public class GameWebSocket {
    private String myName;
    private Session session;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocket(String myName, GameMechanics gameMechanics, WebSocketService webSocketService) {
        this.myName = myName;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    public String getMyName() {
        return myName;
    }

    public void startGame(GameUser user, ArrayList<GameUser> players) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "start");
            JSONObject jsonBody = new JSONObject();
            jsonObject.put("body", jsonBody);
            JSONArray jsonPlayers = new JSONArray();
            jsonBody.put("players", jsonPlayers);
            int i = 0;
            for (GameUser player : players) {
                JSONObject jsonPlayer = new JSONObject();
                jsonPlayer.put("login", player.getMyName());
                jsonPlayer.put("id", ++i);
                jsonPlayers.add(jsonPlayer);
            }
            session.getRemote().sendString(jsonObject.toJSONString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data) throws ParseException {
        try {
            JSONObject jsonObject = (JSONObject)new JSONParser().parse(data);
            if (jsonObject.get("type").equals("gameInfo")) {
                JSONObject jsonBody = (JSONObject)jsonObject.get("body");
                gameMechanics.addUser(myName, (Integer)jsonBody.get("players"));
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        this.session = session;
        webSocketService.addUser(this);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {

    }
}
