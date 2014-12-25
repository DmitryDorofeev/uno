package db;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.beans.Expression;
import java.util.HashMap;
import java.util.List;

/**
 * Created by BDV on 16.11.2014.
 */
public class GameDataSetDAO {
    private SessionFactory sessionFactory;

    public GameDataSetDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(GameDataSet dataSet) {
        Session session = sessionFactory.openSession();
        session.save(dataSet);
        session.close();
    }

    public long getPlayerScores(long playerId) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(GameDataSet.class);
        Object result = criteria.add(Restrictions.eq("playerId", playerId))
            .setProjection(Projections.projectionList()
                    .add(Projections.count("score"))
                    .add(Projections.groupProperty("playerId"))).uniqueResult();
        session.close();
        System.out.print(result.toString());
        return 0;
    }

    public long getLastGameId() {
        Long result;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(GameDataSet.class);
        result = (Long)criteria.setProjection(Projections.projectionList().add(Projections.max("gameId"))).uniqueResult();
        session.close();
        return result == null ? 0 : result;
    }

    public List getScores(int limit) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(GameDataSet.class);
        List objects = criteria.setProjection(Projections.projectionList()
                .add(Projections.count("score"))
                .add(Projections.groupProperty("playerId"))).setMaxResults(limit).list();
        session.close();
        System.out.print(objects.toString());
        return null;
    }
}
