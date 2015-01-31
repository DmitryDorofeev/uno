package frontend;

import MessageSystem.*;
import MessageSystem.ToGameMechanics.*;
import MessageSystem.ToWebSocketService.MsgAddUser;
import MessageSystem.ToWebSocketService.MsgRemoveUser;
import mechanics.GameUser;
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
import utils.LoggerHelper;

import java.util.List;

@WebSocket
public class GameWebSocket {
    private String myName;
    private Session session;
    private String extra;

    public GameWebSocket(String myName) {
        LoggerHelper.logJSON("GameWebSocket()", null);
        this.myName = myName;
    }

    public String getMyName() {
        return myName;
    }

    public void startGame(List<GameUser> players) {
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
            LoggerHelper.logJSON("start", jsonObject);
            session.getRemote().sendString(jsonObject.toJSONString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendCards(List<CardResource> cards) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "cards");
            JSONObject jsonBody = new JSONObject();
            jsonObject.put("body", jsonBody);
            jsonBody.put("cards", getJSONCardsArray(cards));
            LoggerHelper.logJSON(myName, jsonObject);
            session.getRemote().sendString(jsonObject.toJSONString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendUnoFail(String message, long playerId, List<GameUser> players) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "uno");
            JSONObject jsonBody = new JSONObject();
            jsonObject.put("body", jsonBody);
            jsonBody.put("id", playerId);
            jsonBody.put("message", message);
            jsonBody.put("cardsCount", getJSONCardsCountArray(players));
            LoggerHelper.logJSON(myName, jsonObject);
            session.getRemote().sendString(jsonObject.toJSONString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gameStep(boolean correct, String message, long curStepPlayerId, List<CardResource> cards,
                         boolean direction, long focusOnCard, List<GameUser> players, String fromJoystick) {
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
            if (extra == null && fromJoystick != null && fromJoystick.equals(myName))
                jsonBody.put("joystick", true);
            else
                jsonBody.put("joystick", false);
            jsonBody.put("cards", getJSONCardsArray(cards));
            jsonBody.put("cardsCount", getJSONCardsCountArray(players));
            session.getRemote().sendString(jsonObject.toJSONString());
            List<CardResource> playerCards = players.get((int)curStepPlayerId).getCards();
            jsonBody.put("myCards", getJSONCardsArray(playerCards));
            LoggerHelper.logJSON(myName, jsonObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeFocus(long focusOnCard) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "focus");
            JSONObject jsonBody = new JSONObject();
            jsonObject.put("body", jsonBody);
            jsonBody.put("focusOnCard", focusOnCard);
            LoggerHelper.logJSON(myName + (extra == null ? " player " : " joystick "), jsonObject);
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
                jsonBody.put("cards", getJSONCardsArray(cards));
            }
            LoggerHelper.logJSON(myName + " joystick ", jsonObject);
            session.getRemote().sendString(jsonObject.toJSONString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendScores(List<GameUser> players) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "end");
            JSONObject jsonBody = new JSONObject();
            jsonObject.put("body", jsonBody);
            JSONArray jsonScores = new JSONArray();
            jsonBody.put("scores", jsonScores);
            for (GameUser player : players) {
                JSONObject jsonScore = new JSONObject();
                jsonScore.put("login", player.getMyName());
                jsonScore.put("score", player.getScore());
                jsonScores.add(jsonScore);
            }
            LoggerHelper.logJSON(myName + (extra == null ? " player " : " joystick "), jsonObject);
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
            LoggerHelper.logJSON("query: " + myName + (extra == null ? " player " : " joystick "), jsonObject);
            if (jsonObject.get("type").equals("gameInfo")) {
                extra = null;
                addUser(this, null);
                JSONObject jsonBody = (JSONObject)jsonObject.get("body");
                addGameUser(myName, (Long) jsonBody.get("players"));
                return;
            }
            if (jsonObject.get("type").equals("card")) {
                JSONObject jsonBody = (JSONObject)jsonObject.get("body");
                gameStep(myName, (Long) jsonBody.get("focusOnCard"), (String) jsonBody.get("newColor"), null);
                return;
            }
            if (jsonObject.get("type").equals("joystick")) {
                JSONObject jsonBody = (JSONObject)jsonObject.get("body");
                if (jsonBody.get("message").equals("init")) {
                    extra = "joystick";
                    addUser(this, "joystick");
                    initJoystick(myName);
                    return;
                }
                Long tmp = (Long) jsonBody.get("focusOnCard");
                if (tmp == null)
                    tmp = (long)0;
                stepByJoystick(myName, (String) jsonBody.get("message"), (String) jsonBody.get("newColor"), tmp);
                return;
            }
            if (jsonObject.get("type").equals("uno"))
                doUno(myName);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketConnect
     public void onOpen(Session session) {
        LoggerHelper.logJSON(myName + " onOpen()", null);
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        LoggerHelper.logJSON(myName + " onClose()", null);
        removeGameUser(myName);
        removeUser(this, extra);
    }

    private JSONArray getJSONCardsArray(List<CardResource> cards) {
        JSONArray jsonCards = new JSONArray();
        try {
            for (CardResource card : cards) {
                JSONObject jsonCard = new JSONObject();
                jsonCard.put("cardId", card.getCardId());
                jsonCard.put("x", card.getX());
                jsonCard.put("y", card.getY());
                jsonCard.put("num", card.getNum());
                jsonCard.put("width", card.getWidth());
                jsonCard.put("height", card.getHeight());
                jsonCard.put("color", card.getColor());
                jsonCards.add(jsonCard);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return jsonCards;
    }

    private JSONArray getJSONCardsCountArray(List<GameUser> players) {
        JSONArray jsonCardsCount = new JSONArray();
        try {
            for (GameUser player : players) {
                JSONObject jsonCount = new JSONObject();
                jsonCount.put("id", player.getGamePlayerId());
                jsonCount.put("count", player.getCardsCount());
                jsonCardsCount.add(jsonCount);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return jsonCardsCount;
    }

    private void addUser(GameWebSocket gameWebSocket, String extra) {
        Msg msgAddUser = new MsgAddUser(null, MessageSystem.instance().getAddressService().getWebSocketService(),
                gameWebSocket, extra);
        MessageSystem.instance().sendMessage(msgAddUser);
    }

    private void removeUser(GameWebSocket gameWebSocket, String extra) {
        Msg msgRemoveUser = new MsgRemoveUser(null, MessageSystem.instance().getAddressService().getWebSocketService(),
                gameWebSocket, extra);
        MessageSystem.instance().sendMessage(msgRemoveUser);
    }

    private void removeGameUser(String username) {
        //TODO
    }

    private void addGameUser(String username, Long playersCount) {
        Msg msgAddGameUser = new MsgAddGameUser(null, MessageSystem.instance().getAddressService().getGameMechanics(),
                username, playersCount);
        MessageSystem.instance().sendMessage(msgAddGameUser);
    }

    private void gameStep(String username, Long focusOnCard, String newColor, String fromJoystick) {
        Msg msgGameStep = new MsgGameStep(null, MessageSystem.instance().getAddressService().getGameMechanics(),
                username, focusOnCard, newColor, fromJoystick);
        MessageSystem.instance().sendMessage(msgGameStep);
    }

    private void initJoystick(String username) {
        Msg msgInitJoystick = new MsgInitJoystick(null, MessageSystem.instance().getAddressService().getGameMechanics(),
                username);
        MessageSystem.instance().sendMessage(msgInitJoystick);
    }

    private void stepByJoystick(String username, String message, String newColor, Long focusOnCard) {
        Msg msgStepByJoystick = new MsgStepByJoystick(null,
                MessageSystem.instance().getAddressService().getGameMechanics(), username, message, newColor, focusOnCard);
        MessageSystem.instance().sendMessage(msgStepByJoystick);
    }

    private void doUno(String username) {
        Msg msgDoUno = new MsgDoUno(null, MessageSystem.instance().getAddressService().getGameMechanics(), username);
        MessageSystem.instance().sendMessage(msgDoUno);
    }
}
