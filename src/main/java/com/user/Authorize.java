package com.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/Authorize" })
public class Authorize extends HttpServlet {
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

                String query = "";

                statement = connection.createStatement();

                String name = request.getParameter("name");
                String cardNumber = request.getParameter("cardNumber");
                String cvv = request.getParameter("cvv");
                Double amount = 0.0;
                if (!request.getParameter("amount").toString().isEmpty()) {
                    amount = Double.parseDouble(request.getParameter("amount"));
                }

                if (name.isEmpty() || cardNumber.isEmpty() || cvv.isEmpty() || amount <= 0) {

                    RequestDispatcher dispatcher = request.getRequestDispatcher("authorizeCreditCard.jsp");
                    dispatcher.include(request, response);

                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Empty Fields and Transaction below 0 is not valid !');");
                    out.println("</script>");
                    session.setAttribute("message", "Empty Fields and Transaction below 0 is not valid !");
                    dispatcher.forward(request, response);

                } else {
                    int flag = 0;

                    query = "select * from account_details where cardNumber=" + cardNumber;
                    ResultSet resultSet = statement.executeQuery(query);

                    if (!resultSet.next()) {
                        RequestDispatcher dispatcher = request.getRequestDispatcher("authorizeCreditCard.jsp");
                        dispatcher.include(request, response);
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Card Not Found !');");
                        out.println("</script>");
                        session.setAttribute("message", "Card Not Found !");
                        dispatcher.forward(request, response);
                    }
                    resultSet = statement.executeQuery(query);
                    while (resultSet.next()) {

                        if (resultSet.getString("cardNumber").equals(cardNumber)
                                && resultSet.getString("cvv").equals(cvv) && amount > 0 && amount <= 100000) {
                            flag++;
                        }
                    }
                    session.setAttribute("session_amount", amount);
                    statement.close();

                    System.out.println(flag);

                    if (flag != 0) {
                        RequestDispatcher dispatcher = request.getRequestDispatcher("authorized.jsp");
                        dispatcher.include(request, response);
                    } else {
                        RequestDispatcher dispatcher = request.getRequestDispatcher("unAuthorized.jsp");
                        dispatcher.include(request, response);
                    }

                }

            } catch (Exception e) {
                System.err.println("Got an exception! ");
                e.printStackTrace();
            }
        }
    }

}
