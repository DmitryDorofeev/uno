package mechanics;

import base.GameMechanics;
import base.WebSocketService;
import db.DBService;
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
    private DBService dbService;
    private Map<String, GameSession> playerGame = new HashMap<>();
    private ConcurrentLinkedQueue<GameUser> waiters = new ConcurrentLinkedQueue<>();

    public GameMechanicsImpl(WebSocketService webSocketService, DBService dbService) {
        this.webSocketService = webSocketService;
        this.dbService = dbService;
    }

    public void addUser(String username, long playersCount) {
        if (playersCount >= ResourceSystem.instance().getGameParamsResource().getMinPlayersCount()
                && playersCount <= ResourceSystem.instance().getGameParamsResource().getMaxPlayersCount()) {
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
    }

    public void removeUser(String username) {
        //TODO
    }

    public void gameStep(String username, long focusOnCard, String newColor) {
        GameSession gameSession = getPlayerGame(username);
        GameUser curPlayer = gameSession.getUser(username);
        if (curPlayer.getGamePlayerId() == gameSession.getCurStepPlayerId()) {
            if (focusOnCard == -1)
                addCardsToPlayerAndStep(curPlayer, gameSession, newColor);
            else if (curPlayer.isFocusOnCardValid(focusOnCard)){
                curPlayer.setFocusOnCard(focusOnCard);
                CardResource card = ResourceSystem.instance().getCardsResource().getCard(curPlayer.getFocusedCardId());
                if (curPlayer.canDeleteCard(card)) {
                    if (gameSession.canSetCard(card, curPlayer)) {
                        curPlayer.deleteCard(card);
                        gameSession.setCard(card, newColor);
                        finishGameStep(gameSession);
                    } else
                        webSocketService.notifyGameStep(false, "You can not put this card!", curPlayer);
                } else
                    webSocketService.notifyGameStep(false, "Player has not that card!", curPlayer);
            }
            else
                webSocketService.notifyGameStep(false, "FocusOnCard is invalid!", curPlayer);
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

    public void stepByJoystick(String username, String action, String newColor) {
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
                    gameStep(username, curPlayer.getFocusOnCard(), newColor);
                    break;
                case "getCard":
                    addCardsToPlayerAndStep(curPlayer, gameSession, newColor);
                    break;
            }
        }
        else
            webSocketService.notifyGameStep(false, "Not your turn!", curPlayer);
    }

    public void doUno(String username) {
        GameSession gameSession = getPlayerGame(username);
        GameUser curPlayer = gameSession.getUser(username);
        if (gameSession.unoActionExists())
            gameSession.removeUnoAction(curPlayer, true);
    }

    public boolean isPlayerInWaiters(String login) {
        for (GameUser waiter : waiters) {
            if (waiter.getMyName().equals(login))
                return true;
        }
        return false;
    }

    private void addCardsToPlayerAndStep(GameUser player, GameSession gameSession, String newColor) {
        if (!gameSession.playerHasCardToSet(player)) {
            List<CardResource> cards = gameSession.generateCards(1);
            if (!cards.get(0).getColor().equals("black") && gameSession.canSetCard(cards.get(0), player)) {
                gameSession.setCard(cards.get(0), newColor);
                finishGameStep(gameSession);
                return;
            }
            player.addCards(cards);
            if (!cards.get(0).getColor().equals("black"))
                gameSession.updateCurStepPlayerId();
            List<GameUser> playersList = gameSession.getPlayersList();
            webSocketService.sendCards(player);
            for (GameUser curPlayer : playersList)
                webSocketService.notifyGameStep(true, "newCards", curPlayer);
        }
        else
            webSocketService.notifyGameStep(false, "You have card to put!", player);
    }

    private GameSession getPlayerGame(String login) {
        return playerGame.get(login);
    }

    private void startGame(ArrayList<GameUser> players) {
        GameSession gameSession = new GameSessionImpl(players, dbService.getNewGameId());
        int j = 0;
        for (GameUser player : players) {
            playerGame.put(player.getMyName(), gameSession);
            player.setGamePlayerId(j++);
            player.setGameSession(gameSession);
        }
        players.forEach(webSocketService::notifyStartGame);
        for (GameUser player : players) {
            player.setCards(gameSession.generateCards(ResourceSystem.instance().getGameParamsResource().getStartCardsCount()));
            webSocketService.sendCards(player);
        }
        CardResource card = gameSession.generateCards(1).get(0);
        while (!card.getType().equals("number"))
            card = gameSession.generateCards(1).get(0);
        gameSession.setCard(card, card.getColor());
        for (GameUser player : players)
            webSocketService.notifyGameStep(true, "OK", player);
    }

    private void finishGameStep(GameSession gameSession) {
        List<GameUser> playersList = gameSession.getPlayersList();
        if (gameSession.getPlayerById(gameSession.getCurStepPlayerId()).getCardsCount() != 0) {
            gameSession.updateCurStepPlayerId();
            if (gameSession.unoActionExists()) {
                gameSession.removeUnoAction(gameSession.getUnoFailPlayer(), true);
                for (GameUser curPlayer : playersList)
                    webSocketService.notifyUnoFail("UNO fail!", curPlayer);
            }
            for (GameUser curPlayer : playersList)
                webSocketService.notifyGameStep(true, "OK", curPlayer);
            if (gameSession.actionExists()) {
                gameSession.doAction();
                webSocketService.sendCards(gameSession.getPlayerById(gameSession.getCurStepPlayerId()));
                for (GameUser curPlayer : playersList)
                    webSocketService.notifyGameStep(true, "newCards", curPlayer);
            }
        }
        else {
            for (GameUser curPlayer : playersList) {
                webSocketService.sendScores(curPlayer);
                dbService.savePlayerScores(gameSession.getGameId(), curPlayer.getMyName(), curPlayer.getScore());
                playerGame.remove(curPlayer.getMyName());
            }
        }
    }
}
