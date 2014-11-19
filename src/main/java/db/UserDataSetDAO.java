package db;

import base.UserProfile;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Created by alexey on 16.11.2014.
 */
public class UserDataSetDAO {
    private SessionFactory sessionFactory;

    public UserDataSetDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean save(UserProfile user) {
        if (getUserDataByLogin(user.getLogin()) == null) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UserDataSet userDataSet = new UserDataSet(user);
            session.save(userDataSet);
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }

//    public void drop_db() {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        SQLQuery query = session.createSQLQuery("DROP TABLE user");
//        transaction.commit();
//        session.close();
//    }

    public UserDataSet getUserDataByLogin(String login) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(UserDataSet.class);
        UserDataSet userDataSet = (UserDataSet) criteria.add(Restrictions.eq("login", login)).uniqueResult();
        session.close();
        return userDataSet;
    }

    public long getUsersCount() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(UserDataSet.class);
        Long result = (long)criteria.setProjection(Projections.projectionList()
                .add(Projections.count("id"))).uniqueResult();
        session.close();
        return result;
    }
}