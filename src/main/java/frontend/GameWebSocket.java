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
import resources.CardResource;
import resources.ResourceSystem;

import java.util.ArrayList;
import java.util.List;

@WebSocket
public class GameWebSocket {
    private String myName;
    private Session session;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;
    private String extra;

    public GameWebSocket(String myName, GameMechanics gameMechanics, WebSocketService webSocketService) {
        System.out.println("GameWebSocket()");
        this.myName = myName;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    public String getMyName() {
        return myName;
    }

    public void startGame(ArrayList<GameUser> players) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "start");
            JSONObject jsonBody = new JSONObject();
            jsonObject.put("body", jsonBody);
            JSONArray jsonPlayers = new JSONArray();
            jsonBody.put("players", jsonPlayers);
            for (GameUser player : players) {
                JSONObject jsonPlayer = new JSONObject();
                jsonPlayer.put("login", player.getMyName());
                jsonPlayer.put("id", player.getGamePlayerId());
                jsonPlayers.add(jsonPlayer);
            }
            System.out.println(jsonObject.toJSONString());
            session.getRemote().sendString(jsonObject.toJSONString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendStartCards(List<CardResource> cards) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "cards");
            JSONObject jsonBody = new JSONObject();
            jsonObject.put("body", jsonBody);
            JSONArray jsonCards = new JSONArray();
            jsonBody.put("cards", jsonCards);
            for (CardResource card : cards) {
                JSONObject jsonCard = new JSONObject();
                jsonCard.put("cardId", card.getCardId());
                jsonCard.put("x", card.getX());
                jsonCard.put("y", card.getY());
                jsonCard.put("width", card.getWidth());
                jsonCard.put("height", card.getHeight());
                jsonCards.add(jsonCard);
            }
            System.out.println(myName + jsonObject.toJSONString());
            session.getRemote().sendString(jsonObject.toJSONString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gameStep(boolean correct, String message, long curStepPlayerId, CardResource card,
                         boolean direction, long focusOnCard) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "step");
            JSONObject jsonBody = new JSONObject();
            jsonObject.put("body", jsonBody);
            jsonBody.put("correct", correct);
            jsonBody.put("message", message);
            jsonBody.put("curStepPlayerId", curStepPlayerId);
            jsonBody.put("direction", direction);
            jsonBody.put("focusOnCard", focusOnCard);
            JSONArray jsonCards = new JSONArray();
            jsonBody.put("cards", jsonCards);
            JSONObject jsonCard = new JSONObject();
            jsonCard.put("id", card.getCardId());
            jsonCard.put("x", card.getX());
            jsonCard.put("y", card.getY());
            jsonCard.put("width", card.getWidth());
            jsonCard.put("height", card.getHeight());
            jsonCards.add(jsonCard);
            System.out.println(myName + jsonObject.toJSONString());
            session.getRemote().sendString(jsonObject.toJSONString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendCardsToJoystick(boolean correct, String message, long focusOnCard, List<CardResource> cards) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "cards");
            JSONObject jsonBody = new JSONObject();
            jsonObject.put("body", jsonBody);
            jsonBody.put("correct", correct);
            jsonBody.put("message", message);
            if (correct) {
                jsonBody.put("focusOnCard", focusOnCard);
                JSONArray jsonCards = new JSONArray();
                jsonBody.put("cards", jsonCards);
                for (CardResource card : cards) {
                    JSONObject jsonCard = new JSONObject();
                    jsonCard.put("cardId", card.getCardId());
                    jsonCard.put("x", card.getX());
                    jsonCard.put("y", card.getY());
                    jsonCard.put("width", card.getWidth());
                    jsonCard.put("height", card.getHeight());
                    jsonCards.add(jsonCard);
                }
            }
            System.out.println(myName + " joystick " + jsonObject.toJSONString());
            session.getRemote().sendString(jsonObject.toJSONString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data) throws ParseException {
        try {
            JSONObject jsonObject = (JSONObject)new JSONParser().parse(data);
            if (jsonObject.get("type").equals("gameInfo")) {
                extra = null;
                webSocketService.addUser(this, null);
                JSONObject jsonBody = (JSONObject)jsonObject.get("body");
                gameMechanics.addUser(myName, (Long)jsonBody.get("players"));
                return;
            }
            if (jsonObject.get("type").equals("card")) {
                JSONObject jsonBody = (JSONObject)jsonObject.get("body");
                gameMechanics.gameStep(myName, (Long)jsonBody.get("focusOnCard"));
                return;
            }
            if (jsonObject.get("type").equals("joystick")) {
                extra = "joystick";
                webSocketService.addUser(this, "joystick");
                JSONObject jsonBody = (JSONObject)jsonObject.get("body");
                if (jsonBody.get("message").equals("init")) {
                    gameMechanics.initJoystick(myName);
                    return;
                }
                gameMechanics.stepByJoystick(myName, (String)jsonBody.get("message"));
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketConnect
     public void onOpen(Session session) {
        System.out.println("onOpen()");
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        gameMechanics.removeUser(myName);
        webSocketService.removeUser(this, extra);
    }
}
