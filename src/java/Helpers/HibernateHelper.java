package Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.criterion.Restrictions;

public class HibernateHelper {

    static protected Logger log =
            Logger.getLogger("bytesizebook.webdev.hibernate");
    static protected List<Class> listClasses = new ArrayList<Class>();
    static protected SessionFactory sessionFactory;
    
    static public void initSessionFactory(Properties props,
            Class... mappings) {
        if (addMappings(listClasses, mappings)) {
            closeSessionFactory(sessionFactory);
            sessionFactory = createFactory(props, listClasses);
        }
    }

    static public void initSessionFactory(Class... mappings) {
        initSessionFactory(null, mappings);
    }

    static public void createTable(Properties props, Class... mappings) {
        List<Class> tempList = new ArrayList<Class>();
        SessionFactory tempFactory = null;

        addMappings(tempList, mappings);
        if (props == null) {
            props = new Properties();
        }
        props.setProperty(Environment.HBM2DDL_AUTO, "create");
        tempFactory = createFactory(props, tempList);
        closeSessionFactory(tempFactory);
    }

    static public void createTable(Class... mappings) {
        createTable(null, mappings);
    }

    static protected boolean addMappings(List<Class> list, Class... mappings) {
        boolean bNewClass = false;
        for (Class mapping : mappings) {
            if (!list.contains(mapping)) {
                list.add(mapping);
                bNewClass = true;
            }
        }
        return bNewClass;
    }

    static protected SessionFactory createFactory(
            Properties props,
            List<Class> list) {
        SessionFactory factory = null;
        Configuration cfg =
                new Configuration();
        try {
            if (props != null) {
                cfg.addProperties(props);
            }
            configureFromFile(cfg);
            for (Class mapping : list) {
                cfg.addAnnotatedClass(mapping);
            }
            factory = buildFactory(cfg);
        } catch (Exception ex) {
            // Make sure you log the exception, as it might be swallowed
            log.error("SessionFactory creation failed.", ex);
            closeSessionFactory(factory);
            factory = null;
            //hibernate has a runtime exception for handling problems with
            //initialisation. Cast the ex to HibernateException and raise,
            //since the root problem is a Hibernate problem.
            throw new HibernateException(ex);
        }
        return factory;
    }

    static protected void configureFromFile(Configuration cfg) {
        try {
            cfg.configure();
        } catch (HibernateException ex) {
            if (ex.getMessage().equals(
                    "/hibernate.cfg.xml not found")) {
                log.warn(ex.getMessage());
            } else {
                log.error("Error in hibernate "
                        + "configuration file.", ex);
                throw ex;
            }
        }
    }

    static protected SessionFactory buildFactory(Configuration cfg)
            throws Exception {
        SessionFactory factory = null;
        try {
            factory = cfg.buildSessionFactory();
        } catch (Exception ex) {
            closeSessionFactory(factory);
            factory = null;
            throw ex;
        }
        return factory;
    }

    static public void closeSessionFactory(SessionFactory factory) {
        if (factory != null) {
            factory.close();
        }
    }

    static public void closeFactory() {
        closeSessionFactory(sessionFactory);
    }

    static public void updateDB(Object obj) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            session.saveOrUpdate(obj);

            tx.commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    static public void updateDB(java.util.List list) {

        Session session = null;        
        try {
            session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            for (Object obj : list) {
                session.saveOrUpdate(obj);
            }

            tx.commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }  

    static public void saveDB(Object obj) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            session.save(obj);

            tx.commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    static public void removeDB(Object obj) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            session.delete(obj);

            tx.commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }  

    static public java.util.List getListData(
            Class classBean, String strKey, Object value) {
        java.util.List result = new java.util.ArrayList();

        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            Criteria criteria =
                    session.createCriteria(classBean);
            if (strKey != null) {
                criteria.add(Restrictions.like(strKey, value));
            }
            result = criteria.list();

            tx.commit();

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    static public java.util.List getListData(
            Class classBean,
            String strKey1, Object value1,
            String strKey2, Object value2) {
        java.util.List result = new java.util.ArrayList();
        boolean withParent = false;
        int age = 0;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            Criteria criteria =
                    session.createCriteria(classBean);
            if (strKey1 != null) {
                criteria.add(Restrictions.like(strKey1, value1));
            }
            if (strKey2 != null) {
                criteria.add(Restrictions.like(strKey2, value2));
            }

            result = criteria.list();

            tx.commit();

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    static public java.util.List getListData(
            Class classBean) {
        return getListData(classBean, null, null);
    }

    static public Object getFirstMatch(
            Class classBean, String strKey, Object value) {

        Object result = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            Criteria criteria =
                    session.createCriteria(classBean);
            if (strKey != null) {
                criteria.add(Restrictions.like(strKey, value));
            }
            criteria.setMaxResults(1);
            result = criteria.uniqueResult();

            tx.commit();

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    static public Object getFirstMatch(
            Object data, String strKey, Object value) {
        return getFirstMatch(data.getClass(),
                strKey, value);
    }

    static public Object getKeyData(Class beanClass, long itemId) {
        Object data = null;
        Session session = sessionFactory.openSession();

        data = session.get(beanClass, itemId);

        session.close();

        return data;
    }

    static public boolean isSessionOpen() {
        return sessionFactory != null;
    }
}
