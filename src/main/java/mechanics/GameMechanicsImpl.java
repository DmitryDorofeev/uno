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

    public void addUser(String user, long playersCount) {
        GameUser newUser = new GameUser(user, playersCount);
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

    public void gameStep(String username, long cardId) {
        GameSession gameSession = getPlayerGame(username);
        ArrayList<GameUser> playersList = gameSession.getPlayersList();
        GameUser curPlayer = gameSession.getUser(username);
        CardResource card = ResourceSystem.instance().getCardsResource().getCard(cardId);
        if (curPlayer.deleteCard(card)) {
            if (gameSession.setCard(card)) {
                for (GameUser player : playersList)
                    webSocketService.notifyGameStep(true, "OK", player);
            }
            else
                for (GameUser player : playersList)
                    webSocketService.notifyGameStep(false, "You can not put this card!", player);
        }
        else
            for (GameUser player : playersList)
                webSocketService.notifyGameStep(false, "Player has not that card!", player);
    }

    public GameSession getPlayerGame(String login) {
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
        Random rnd = new Random();
        for (GameUser player : players) {
            List<CardResource> cards = new ArrayList<>();
            for (int i = 0; i < ResourceSystem.instance().getGameParamsResource().getStartCardsCount(); ++i) {
                CardResource temp = ResourceSystem.instance().getCardsResource().getCard(
                        rnd.nextInt(ResourceSystem.instance().getCardsResource().CardsCount()));
                cards.add(new CardResource(temp.getCardId(), temp.getColor(), temp.getNum(),
                        temp.getWidth(), temp.getHeight(), temp.getX(), temp.getY()));
            }
            player.setCards(cards);
            webSocketService.sendStartCards(player);
        }
        CardResource temp = ResourceSystem.instance().getCardsResource().getCard(
                rnd.nextInt(ResourceSystem.instance().getCardsResource().CardsCount()));
        gameSession.setCard(temp);
        for (GameUser player : players)
            webSocketService.notifyGameStep(true, "OK", player);
    }
}
