/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import Entities.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InitHibernate extends HttpServlet {

    public void init() {
        boolean create = Boolean.parseBoolean(this.getInitParameter("create"));
        if (create) {
            HibernateHelper.createTable(User.class);
        }
        HibernateHelper.initSessionFactory(User.class);
    }
}
