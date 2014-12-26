package db;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Created by alexey on 16.11.2014.
 */

@Entity
@Table(name = "game")
public class GameDataSet implements Serializable { // Serializable Important to Hibernate!
    private static final long serialVersionUID = 0L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "gameId", nullable = false)
    private long gameId;

    @Column(name = "playerId", nullable = false)
    private long playerId;

    @Column(name = "score", nullable = false)
    private long score;

    //Important to Hibernate!
    public GameDataSet() {
    }

    public GameDataSet(long id, long gameId, long playerId, long score){
        setId(id);
        setGameId(gameId);
        setPlayerId(playerId);
        setScore(score);
    }

    public GameDataSet(long gameId, long playerId, long score){
        setId(-1);
        setGameId(gameId);
        setPlayerId(playerId);
        setScore(score);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
