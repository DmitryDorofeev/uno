package mechanics;

import base.GameMechanics;
import base.WebSocketService;
import resources.CardResource;
import resources.ResourceSystem;
import utils.TimeHelper;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author alexey
 */
public class GameMechanicsImpl implements GameMechanics {
    private WebSocketService webSocketService;
    private Map<String, GameSession> playerGame = new HashMap<>();
    private ConcurrentLinkedQueue<GameUser> waiters = new ConcurrentLinkedQueue<>();

    public GameMechanicsImpl(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    public void addUser(String username, long playersCount) {
        GameUser newUser = new GameUser(username, playersCount);
        ArrayList<GameUser> tempList = new ArrayList<>();
        tempList.add(newUser);
        Iterator iter = waiters.iterator();
        while (iter.hasNext() && tempList.size() != playersCount) {
            GameUser waiter = (GameUser) iter.next();
            if (waiter.getPlayersCount() == playersCount)
                tempList.add(waiter);
        }
        if (tempList.size() == playersCount) {
            for (GameUser player : tempList)
                waiters.remove(player);
            startGame(tempList);
        } else
            waiters.add(newUser);
        tempList.clear();
    }

    public void removeUser(String username) {
        //TODO
    }

    public void gameStep(String username, long focusOnCard) {
        GameSession gameSession = getPlayerGame(username);
        ArrayList<GameUser> playersList = gameSession.getPlayersList();
        GameUser curPlayer = gameSession.getUser(username);
        if (curPlayer.getGamePlayerId() == gameSession.getCurStepPlayerId()) {
            if (focusOnCard == -1)
                addCardsToPlayerAndStep(curPlayer, gameSession);
            else {
                curPlayer.setFocusOnCard(focusOnCard);
                CardResource card = ResourceSystem.instance().getCardsResource().getCard(curPlayer.getFocusedCardId());
                if (curPlayer.canDeleteCard(card)) {
                    if (gameSession.canSetCard(card)) {
                        curPlayer.deleteCard(card);
                        gameSession.setCard(card);
                        gameSession.updateCurStepPlayerId();
                        for (GameUser player : playersList)
                            webSocketService.notifyGameStep(true, "OK", player);
                    } else
                        webSocketService.notifyGameStep(false, "You can not put this card!", curPlayer);
                } else
                    webSocketService.notifyGameStep(false, "Player has not that card!", curPlayer);
            }
        }
        else
            webSocketService.notifyGameStep(false, "Not your turn!", curPlayer);
    }

    public void initJoystick(String username) {
        GameSession gameSession = getPlayerGame(username);
        if (gameSession != null) {
            GameUser curPlayer = gameSession.getUser(username);
            webSocketService.sendCardsToJoystick(true, "OK", username, curPlayer.getFocusOnCard(), curPlayer.getCards());
        }
        else
            webSocketService.sendCardsToJoystick(false, "Player has not started game yet", username, -1, null);
    }

    public void stepByJoystick(String username, String action) {
        GameSession gameSession = getPlayerGame(username);
        GameUser curPlayer = gameSession.getUser(username);
        switch (action) {
            case "selectRightCard":
                curPlayer.updateFocusOnCard("right");
                webSocketService.notifyChangeFocus(curPlayer);
                return;
            case "selectLeftCard":
                curPlayer.updateFocusOnCard("left");
                webSocketService.notifyChangeFocus(curPlayer);
                return;
        }
        if (curPlayer.getGamePlayerId() == gameSession.getCurStepPlayerId()) {
            switch (action) {
                case "throwCard":
                    gameStep(username, curPlayer.getFocusOnCard());
                    break;
                case "getCard":
                    addCardsToPlayerAndStep(curPlayer, gameSession);
                    break;
            }
        }
        else
            webSocketService.notifyGameStep(false, "Not your turn!", curPlayer);
    }

    private void addCardsToPlayerAndStep(GameUser player, GameSession gameSession) {
        if (!gameSession.playerHasCardToSet(player)) {
            List<CardResource> cards = gameSession.generateCards(1);
            while (!gameSession.canSetCard(cards.get(0))) {
                player.addCards(cards);
                cards = gameSession.generateCards(1);
            }
            gameSession.setCard(cards.get(0));
            gameSession.updateCurStepPlayerId();
            List<GameUser> playersList = gameSession.getPlayersList();
            for (GameUser curPlayer : playersList)
                webSocketService.notifyGameStep(true, "OK", curPlayer);
        }
        else
            webSocketService.notifyGameStep(false, "You have card to put!", player);
    }

    private GameSession getPlayerGame(String login) {
        return playerGame.get(login);
    }

    private void startGame(ArrayList<GameUser> players) {
        GameSession gameSession = new GameSession(players);
        int j = 0;
        for (GameUser player : players) {
            playerGame.put(player.getMyName(), gameSession);
            player.setGamePlayerId(j++);
            player.setGameSession(gameSession);
        }
        for (GameUser player : players)
            webSocketService.notifyStartGame(player);
        for (GameUser player : players) {
            player.setCards(gameSession.generateCards(ResourceSystem.instance().getGameParamsResource().getStartCardsCount()));
            webSocketService.sendStartCards(player);
        }
        gameSession.setCard(gameSession.generateCards(1).get(0));
        for (GameUser player : players)
            webSocketService.notifyGameStep(true, "OK", player);
    }
}
