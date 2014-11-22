package db;

import db.UserProfile;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import resources.ResourceSystem;

import java.util.Map;

/**
 * Created by BDV on 16.11.2014.
 */
public class DBServiceImpl implements DBService {
    private SessionFactory sessionFactory;
    private boolean status;

    public DBServiceImpl() {
        sessionFactory = createSessionFactory();
        if (sessionFactory == null) {
            status = false;
            return;
        }
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        System.out.append(transaction.getLocalStatus().toString()).append('\n');
        session.close();
        status = true;
    }

    public boolean saveUser(UserProfile user) {
        UserDataSetDAO userDataSetDAO = new UserDataSetDAO(sessionFactory);
        return userDataSetDAO.save(user);
    }

    public UserProfile getUserData(String login) {
        UserDataSetDAO userDataSetDAO = new UserDataSetDAO(sessionFactory);
        UserDataSet userDataSet = userDataSetDAO.getUserDataByLogin(login);
        if (userDataSet != null)
            return new UserProfile(userDataSet.getLogin(), userDataSet.getPassword(), userDataSet.getEmail());
        return null;
    }

    public long getPlayerScores(String login) {
        UserDataSetDAO userDataSetDAO = new UserDataSetDAO(sessionFactory);
        UserDataSet userDataSet = userDataSetDAO.getUserDataByLogin(login);
        if (userDataSet != null) {
            GameDataSetDAO gameDataSetDAO = new GameDataSetDAO(sessionFactory);
            return gameDataSetDAO.getPlayerScores(userDataSet.getId());
        }
        return 0;
    }

    public long getAmountOfRegisteredUsers() {
        UserDataSetDAO userDataSetDAO = new UserDataSetDAO(sessionFactory);
        return userDataSetDAO.getUsersCount();
    }

//    public Map<String, Integer> getScoreBoard(int limit) {
//        GameDataSetDAO gameDataSetDAO = new GameDataSetDAO(sessionFactory);
//
//    }

    public boolean getStatus() {
        return status;
    }

    private SessionFactory createSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(GameDataSet.class);
        ResourceSystem resourceSystem = ResourceSystem.instance();
        configuration.setProperty("hibernate.dialect", resourceSystem.getDBConfigResource().getDialect());
        configuration.setProperty("hibernate.connection.driver_class", resourceSystem.getDBConfigResource().getDriver());
        configuration.setProperty("hibernate.connection.url", resourceSystem.getDBConfigResource().getUrl());
        configuration.setProperty("hibernate.connection.username", resourceSystem.getDBConfigResource().getUsername());
        configuration.setProperty("hibernate.connection.password", resourceSystem.getDBConfigResource().getPassword());
        configuration.setProperty("hibernate.show_sql", resourceSystem.getDBConfigResource().getShow_sql());
        configuration.setProperty("hibernate.hbm2ddl.auto", resourceSystem.getDBConfigResource().getHbm2ddl());

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        SessionFactory sessionFactory = null;
        try {
             sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
}
