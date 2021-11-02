package com.user;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

@WebServlet(urlPatterns = { "/LoginCheck" })
public class LoginCheck extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // super.doGet(request, response);
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // super.doPost(request, response);
        processRequest(request, response);
    }

    void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/bank?autoReconnect=true&useSSL=false";
                String user = "root";
                String password = "kumar.shubham";

                Connection connection = null;
                Statement statement = null;

                connection = DriverManager.getConnection(url, user, password);

                String query = "SELECT * FROM user_pass";

                statement = connection.createStatement();

                String userAccountNumber = request.getParameter("accountNumber");
                String userPassword = request.getParameter("password");

                if (userAccountNumber.isEmpty() || userPassword.isEmpty()) {

                    RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                    dispatcher.include(request, response);

                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Empty Account Number or Password or Not Authorized');");
                    out.println("</script>");

                } else {
                    int flag = 0;

                    String correctAccountNumber = "", correctPassword = "";
                    ResultSet resultSet = statement.executeQuery(query);

                    System.out.println(userAccountNumber);

                    while (resultSet.next()) {

                        if (resultSet.getString("accountNumber").equals(userAccountNumber)
                                && resultSet.getString("password").equals(userPassword)) {
                            flag++;
                            correctAccountNumber = resultSet.getString("accountNumber");
                        }
                    }
                    statement.close();
                    if (flag != 0) {
                        /*
                         * if (session.isNew() == false) { session.invalidate(); session =
                         * request.getSession(true); }
                         */
                        session.setAttribute("session_accountNumber", correctAccountNumber);
                        session.setAttribute("session_password", correctPassword);

                        Cookie cookie_ID = new Cookie("cookie_accountNumber", correctAccountNumber);
                        cookie_ID.setMaxAge(365 * 24 * 60 * 60);
                        cookie_ID.setPath("/");
                        response.addCookie(cookie_ID);

                        request.setAttribute("BankAccountNumber", correctAccountNumber);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("homepage.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                        dispatcher.include(request, response);
                        out.println("<script type=\'text/javascript\'>");
                        out.println("alert('Wrong Credentials or Unauthorized Access!');");
                        out.println("location='index.jsp';");
                        out.println("</script>");
                    }

                }

            } catch (Exception e) {
                System.err.println("Got an exception! ");
                e.printStackTrace();
            }
        }
    }

}
