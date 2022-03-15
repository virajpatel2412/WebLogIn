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
import javax.servlet.http.HttpSession;

/**
 *
 * @author HP
 */
public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pr = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/viraj", "root", "system manager");

            Statement st = conn.createStatement();
            String email = (String) request.getParameter("email");
            String pass = (String) request.getParameter("password");

//            User Validation query
            String iquery = String.format("select * from customer where email = '%s' and password='%s'", email, pass);
            
//            Check for Email ID that is registered but try to login with wrong password
            String emailquery = String.format("select email from customer where email = '%s'", email);

            ResultSet result = st.executeQuery(iquery);
            boolean r = result.next();
            result = st.executeQuery(emailquery);
            boolean e = result.next();

            if ((e == true) & (r == false)) {
//                User Email is correct but password is wrong
                RequestDispatcher rd = request.getRequestDispatcher("index.html");
                pr.append("<div class='wrongpass'> Your password is Wrong!!!  Please Enter correct Password </div>");
                rd.include(request, response);
            } 
            else if (r == true) {
//                Valid User for LogIn 
                HttpSession session = request.getSession();
                session.setAttribute("isValid", "yes");
                response.sendRedirect("welcome.jsp");
            } else {
//                Email id is not registered 
                pr.append("Please Sign Up first");
            }

        } catch (SQLException | ClassNotFoundException ex) {
            pr.append(ex.getMessage());
        }
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
