package com.user;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/CreateAccount" })
public class CreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public String generateRandomNumbers(int n) {
		Random rnd = new Random();
		char[] digits = new char[n];
		digits[0] = (char) (rnd.nextInt(9) + '1');
		for (int i = 1; i < digits.length; i++) {
			digits[i] = (char) (rnd.nextInt(10) + '0');
		}
		return (new String(digits));
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

				statement = connection.createStatement();

				String userName = request.getParameter("name");
				String userDOB = request.getParameter("dob").toString();
				String userAdress = request.getParameter("address");
				String userEmail = request.getParameter("email").toString();
				String userAccountNumber = generateRandomNumbers(12);
				String userCvv = generateRandomNumbers(3);
				String userCardNumber = generateRandomNumbers(16);
				String userAccountType = request.getParameter("accountType").toString();

				if (userName.isEmpty() || userDOB.isEmpty() || userAdress.isEmpty() || userEmail.isEmpty()
						|| userAccountType.isEmpty()) {

					RequestDispatcher dispatcher = request.getRequestDispatcher("homepage.jsp");
					dispatcher.include(request, response);

					out.println("<script type=\"text/javascript\">");
					out.println("alert('All the fields are mandatory, Fill All of Them !');");
					out.println("</script>");

				} else {

					String sqlCreate = "CREATE TABLE IF NOT EXISTS " + "user_pass"
							+ "  (accountNumber           Varchar(100)," + "   password VARCHAR(100))";

					Boolean status = statement.execute(sqlCreate);

					sqlCreate = "CREATE TABLE IF NOT EXISTS " + "account_details" + "  (name            varchar(200),"
							+ "   dob          varchar(100)," + "   address           varchar(200),"
							+ "   email           varchar(200)," + "   typeOfAccount     VARCHAR(100),"
							+ "   accountNumber     VARCHAR(100)," + "   cardNumber     VARCHAR(100),"
							+ "   cvv     VARCHAR(10)," + "   balance double)";

					statement = connection.createStatement();
					status = statement.execute(sqlCreate);

					PreparedStatement ps = connection.prepareStatement(
							"insert into account_details(name, dob, address, email, typeofAccount, accountNumber, cardNumber, cvv, balance) values(?,?,?,?,?,?,?,?,?)");
					ps.setString(1, userName);
					ps.setString(2, userDOB);
					ps.setString(3, userAdress);
					ps.setString(4, userEmail);
					ps.setString(5, userAccountType);
					ps.setString(6, userAccountNumber);
					ps.setString(7, userCardNumber);
					ps.setString(8, userCvv);
					ps.setDouble(9, 0.0);

					status = ps.execute();

					ps = connection.prepareStatement("insert into user_pass(accountNumber, password) values(?,?)");
					ps.setString(1, userAccountNumber);
					ps.setString(2, "");

					status = ps.execute();

					int flag = 0;

					String query = "SELECT accountNumber from account_details where accountNumber = "
							+ userAccountNumber;
					String correctAccountNumber = "";
					ResultSet resultSet = statement.executeQuery(query);

					while (resultSet.next()) {
						System.out.println("inside loop");

						if (resultSet.getString("accountNumber").equals(userAccountNumber)) {
							flag++;
							correctAccountNumber = resultSet.getString("accountNumber");
						}
					}
					statement.close();
					ps.close();

					if (flag != 0) {

						session.setAttribute("userAccountCreated", correctAccountNumber);
						session.setAttribute("userCardAllocated", userCardNumber);
						session.setAttribute("userCvv", userCvv);

						RequestDispatcher dispatcher = request.getRequestDispatcher("homepage.jsp");
						dispatcher.include(request, response);
						out.println("<script type=\"text/javascript\">");
						out.println("alert('Account Created!`);");
						out.println("</script>");
						dispatcher.forward(request, response);
					} else {
						RequestDispatcher dispatcher = request.getRequestDispatcher("homepage.jsp");
						dispatcher.include(request, response);
						out.println("<script type=\"text/javascript\">");
						out.println("alert(`Account not created!`);");
						out.println("</script>");
					}

				}

			} catch (Exception e) {
				System.err.println("Got an exception! ");
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
}