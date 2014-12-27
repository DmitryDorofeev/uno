package mechanics;

import MessageSystem.ToWebSocketService.*;
import MessageSystem.Msg;
import MessageSystem.Address;
import MessageSystem.MessageSystem;
import base.GameMechanics;
import db.DBService;
import resources.CardResource;
import resources.ResourceSystem;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author alexey
 */
public class GameMechanicsImpl implements GameMechanics, Runnable {
    private Address address = new Address();
    private DBService dbService;
    private Map<String, GameSession> playerGame = new HashMap<>();
    private ConcurrentLinkedQueue<GameUser> waiters = new ConcurrentLinkedQueue<>();

    public GameMechanicsImpl(DBService dbService) {
        this.dbService = dbService;
        MessageSystem.instance().addService(this);
        MessageSystem.instance().getAddressService().setGameMechanics(getAddress());
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        while (true) {
            MessageSystem.instance().execForAbonent(this);
            try {
                Thread.sleep(ResourceSystem.instance().getServerConfigResource().getServiceSleepTime());
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    public void gameStep(String username, long focusOnCard, String newColor, String fromJoystick) {
        GameSession gameSession = getPlayerGame(username);
        GameUser curPlayer = gameSession.getUser(username);
        if (curPlayer.getGamePlayerId() == gameSession.getCurStepPlayerId()) {
            if (focusOnCard == -1)
                addCardsToPlayerAndStep(curPlayer, gameSession, newColor, fromJoystick);
            else if (curPlayer.isFocusOnCardValid(focusOnCard)){
                curPlayer.setFocusOnCard(focusOnCard);
                CardResource temp = ResourceSystem.instance().getCardsResource().getCard(curPlayer.getFocusedCardId());
                CardResource card = new CardResource(temp.getCardId(), temp.getColor(), temp.getType(),
                        temp.getNum(), temp.getWidth(), temp.getHeight(), temp.getX(), temp.getY());
                if (curPlayer.canDeleteCard(card)) {
                    if (gameSession.canSetCard(card, curPlayer)) {
                        curPlayer.deleteCard(card);
                        gameSession.setCard(card, newColor);
                        finishGameStep(gameSession, fromJoystick);
                    } else
                        notifyGameStep(false, "You can not put this card!", curPlayer, fromJoystick);
                } else
                    notifyGameStep(false, "Player has not that card!", curPlayer, fromJoystick);
            }
            else
                notifyGameStep(false, "FocusOnCard is invalid!", curPlayer, fromJoystick);
        }
        else
            notifyGameStep(false, "Not your turn!", curPlayer, fromJoystick);
    }

    public void initJoystick(String username) {
        GameSession gameSession = getPlayerGame(username);
        if (gameSession != null) {
            GameUser curPlayer = gameSession.getUser(username);
            sendCardsToJoystick(true, "OK", username, curPlayer.getFocusOnCard(), curPlayer.getCards());
        }
        else
            sendCardsToJoystick(false, "Player has not started game yet", username, -1, null);
    }

    public void stepByJoystick(String username, String action, String newColor) {
        GameSession gameSession = getPlayerGame(username);
        GameUser curPlayer = gameSession.getUser(username);
        switch (action) {
            case "selectRightCard":
                curPlayer.updateFocusOnCard("right");
                notifyChangeFocus(curPlayer);
                return;
            case "selectLeftCard":
                curPlayer.updateFocusOnCard("left");
                notifyChangeFocus(curPlayer);
                return;
        }
        if (curPlayer.getGamePlayerId() == gameSession.getCurStepPlayerId()) {
            switch (action) {
                case "throwCard":
                    gameStep(username, curPlayer.getFocusOnCard(), newColor, username);
                    break;
                case "getCard":
                    addCardsToPlayerAndStep(curPlayer, gameSession, newColor, username);
                    break;
            }
        }
        else
            notifyGameStep(false, "Not your turn!", curPlayer, username);
    }

    public void doUno(String username) {
        GameSession gameSession = getPlayerGame(username);
        GameUser curPlayer = gameSession.getUser(username);
        if (gameSession.unoActionExists())
            gameSession.removeUnoAction(curPlayer, false);
    }

    public boolean isPlayerInWaiters(String login) {
        for (GameUser waiter : waiters) {
            if (waiter.getMyName().equals(login))
                return true;
        }
        return false;
    }

    private void addCardsToPlayerAndStep(GameUser player, GameSession gameSession, String newColor, String fromJoystick) {
        if (!gameSession.playerHasCardToSet(player)) {
            List<CardResource> cards = gameSession.generateCards(1);
            if (!cards.get(0).getColor().equals("black") && gameSession.canSetCard(cards.get(0), player)) {
                gameSession.setCard(cards.get(0), newColor);
                finishGameStep(gameSession, fromJoystick);
                return;
            }
            player.addCards(cards);
            if (!cards.get(0).getColor().equals("black"))
                gameSession.updateCurStepPlayerId();
            List<GameUser> playersList = gameSession.getPlayersList();
            sendCards(player);
            for (GameUser curPlayer : playersList)
                notifyGameStep(true, "newCards", curPlayer, fromJoystick);
        }
        else
            notifyGameStep(false, "You have card to put!", player, fromJoystick);
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
        players.forEach(this::notifyStartGame);
        for (GameUser player : players) {
            player.setCards(gameSession.generateCards(ResourceSystem.instance().getGameParamsResource().getStartCardsCount()));
            sendCards(player);
        }
        CardResource card = gameSession.generateCards(1).get(0);
        while (!card.getType().equals("number"))
            card = gameSession.generateCards(1).get(0);
        gameSession.setCard(card, card.getColor());
        for (GameUser player : players)
            notifyGameStep(true, "OK", player, null);
    }

    private void finishGameStep(GameSession gameSession, String fromJoystick) {
        List<GameUser> playersList = gameSession.getPlayersList();
        if (gameSession.getPlayerById(gameSession.getCurStepPlayerId()).getCardsCount() != 0) {
            if (gameSession.unoActionExists()) {
                gameSession.removeUnoAction(gameSession.getUnoFailPlayer(), true);
                sendCards(gameSession.getUnoFailPlayer());
                for (GameUser curPlayer : playersList)
                    notifyUnoFail("UNO fail!", curPlayer);
            }
            if (gameSession.getPlayerById(gameSession.getCurStepPlayerId()).getCardsCount() == 1)
                gameSession.setUnoAction();
            gameSession.updateCurStepPlayerId();
            for (GameUser curPlayer : playersList)
                notifyGameStep(true, "OK", curPlayer, fromJoystick);
            if (gameSession.actionExists()) {
                gameSession.doAction();
                sendCards(gameSession.getPlayerById(gameSession.getCurStepPlayerId()));
                gameSession.updateCurStepPlayerId();
                for (GameUser curPlayer : playersList)
                    notifyGameStep(true, "newCards", curPlayer, fromJoystick);
            }
        }
        else {
            playersList.forEach(mechanics.GameUser::calculateScore);
            for (GameUser curPlayer : playersList) {
                sendScores(curPlayer);
                dbService.savePlayerScores(gameSession.getGameId(), curPlayer.getMyName(), curPlayer.getScore());
                playerGame.remove(curPlayer.getMyName());
            }
        }
    }

    void notifyStartGame(GameUser user) {
        Msg msgNotifyStartGame = new MsgNotifyStartGame(MessageSystem.instance().getAddressService().getGameMechanics(),
                MessageSystem.instance().getAddressService().getWebSocketService(), user);
        MessageSystem.instance().sendMessage(msgNotifyStartGame);
    }

    void sendCards(GameUser user) {
        Msg msgSendCards = new MsgSendCards(MessageSystem.instance().getAddressService().getGameMechanics(),
                MessageSystem.instance().getAddressService().getWebSocketService(), user);
        MessageSystem.instance().sendMessage(msgSendCards);
    }

    void notifyGameStep(boolean correct, String message, GameUser user, String fromJoystick) {
        Msg msgNotifyGameStep = new MsgNotifyGameStep(MessageSystem.instance().getAddressService().getGameMechanics(),
                MessageSystem.instance().getAddressService().getWebSocketService(),
                correct, message, user, fromJoystick);
        MessageSystem.instance().sendMessage(msgNotifyGameStep);
    }

    void notifyChangeFocus(GameUser user) {
        Msg msgNotifyChangeFocus = new MsgNotifyChangeFocus(MessageSystem.instance().getAddressService().getGameMechanics(),
                MessageSystem.instance().getAddressService().getWebSocketService(), user);
        MessageSystem.instance().sendMessage(msgNotifyChangeFocus);
    }

    void notifyUnoFail(String message, GameUser user) {
        Msg msgNotifyUnoFail = new MsgNotifyUnoFail(MessageSystem.instance().getAddressService().getGameMechanics(),
                MessageSystem.instance().getAddressService().getWebSocketService(), message, user);
        MessageSystem.instance().sendMessage(msgNotifyUnoFail);
    }

    void sendCardsToJoystick(boolean correct, String message, String username,
                             long focusOnCard, List<CardResource> cards) {
        Msg msgSendCardsToJoystick = new MsgSendCardsToJoystick(MessageSystem.instance().getAddressService().getGameMechanics(),
                MessageSystem.instance().getAddressService().getWebSocketService(), correct, message, username,
                focusOnCard, cards);
        MessageSystem.instance().sendMessage(msgSendCardsToJoystick);
    }

    void sendScores(GameUser user) {
        Msg msgSendScores = new MsgSendScores(MessageSystem.instance().getAddressService().getGameMechanics(),
                MessageSystem.instance().getAddressService().getWebSocketService(), user);
        MessageSystem.instance().sendMessage(msgSendScores);
    }
}
