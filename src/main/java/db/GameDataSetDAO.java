package db;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alexey on 16.11.2014.
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
        Object[] result = (Object[])criteria.add(Restrictions.eq("playerId", playerId))
                .setProjection(Projections.projectionList()
                                .add(Projections.groupProperty("playerId"))
                                .add(Projections.sum("score"))
                ).uniqueResult();
        session.close();
        return result != null ? (Long)result[1] : 0;
    }

    public long getLastGameId() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(GameDataSet.class);
        Long result = (Long)criteria.setProjection(Projections.projectionList()
              .add(Projections.max("gameId"))).uniqueResult();
        session.close();
        return result == null ? -1 : result;
    }

    public Map<Long, Long> getScores(int limit) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(GameDataSet.class);
        List<Object[]> objects = criteria.setProjection(Projections.projectionList()
                        .add(Projections.groupProperty("playerId"))
                        .add(Projections.count("score"))
        ).setMaxResults(limit).list();
        Map<Long, Long> result = new HashMap<>();
        for (Object[] elem : objects)
            result.put((Long)elem[0], (Long)elem[1]);
        return result;
    }
}
