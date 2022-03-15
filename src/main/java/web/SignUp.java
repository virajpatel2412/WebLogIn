/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HP
 */
public class SignUp extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pr = response.getWriter();
        String email = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/viraj", "root", "system manager");

            Statement st = conn.createStatement();
            email = (String) request.getParameter("email");
            String pass = (String) request.getParameter("password");

            String iquery = String.format("insert into customer values('%s','%s')", email, pass);

            int n = st.executeUpdate(iquery);
            RequestDispatcher rd = request.getRequestDispatcher("index.html");
            pr.append("<div class='signup'>Your data Inserted Sucessfully <div>");
            rd.include(request, response);

        } catch (SQLException | ClassNotFoundException ex) {
            if (ex.getMessage() == null ? String.format("Duplicate entry '%s' for key 'customer.PRIMARY'", email) == null : ex.getMessage().equals(String.format("Duplicate entry '%s' for key 'customer.PRIMARY'", email))) {
                RequestDispatcher rd = request.getRequestDispatcher("index.html");
                pr.append("<div class='signuperr'>Your email already registered <div>");
                rd.include(request, response);
            } else {
                pr.append(ex.getMessage());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
