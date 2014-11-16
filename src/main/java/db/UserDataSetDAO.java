package db;

import base.UserProfile;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

    public UserDataSet getUserDataByLogin(String login) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(UserDataSet.class);
        UserDataSet userDataSet = (UserDataSet) criteria.add(Restrictions.eq("login", login)).uniqueResult();
        transaction.commit();
        session.close();
        return userDataSet;
    }

    public long getUsersCount() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(UserDataSet.class);
        Object result = criteria.setProjection(Projections.projectionList()
                .add(Projections.count("id"))).uniqueResult();
        session.close();
        System.out.print(result.toString());
        return 0;
    }
}