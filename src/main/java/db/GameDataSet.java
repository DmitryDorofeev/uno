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

    @Column(name = "playerId")
    private long playerId;

    @Column(name = "enemies")
    private String enemies;

    @Column(name = "score")
    private long score;

    //Important to Hibernate!
    public GameDataSet() {
    }

    public GameDataSet(long id, long playerId, String enemies, long score){
        setId(id);
        setPlayerId(playerId);
        setEnemies(enemies);
        setScore(score);
    }

    public GameDataSet(long playerId, String enemies, long score){
        setId(-1);
        setPlayerId(playerId);
        setEnemies(enemies);
        setScore(score);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getEnemies() {
        return enemies;
    }

    public void setEnemies(String enemies) {
        this.enemies = enemies;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
