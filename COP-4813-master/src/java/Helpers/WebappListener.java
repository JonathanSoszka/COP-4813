
package Helpers;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebappListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce)
  {}

  public void contextDestroyed(ServletContextEvent sce)
  {
    try {
      Enumeration<Driver> enumer = DriverManager.getDrivers();
      while (enumer.hasMoreElements()) {
	  System.out.println("Forms: deregisterd driver");
        DriverManager.deregisterDriver(enumer.nextElement());
      }
    } catch (java.sql.SQLException se) {
      se.printStackTrace();
    }
    shared.HibernateHelper.closeFactory();
  }
}