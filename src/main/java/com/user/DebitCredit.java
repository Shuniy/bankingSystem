package com.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/DebitCredit" })
public class DebitCredit extends HttpServlet {

	private static final long serialVersionUID = 1L;

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

				String beneficiaryAccountNumber = request.getParameter("beneficiaryAccountNumber").toString();
				Double amount = 0.0;
				if (!request.getParameter("amount").toString().isEmpty()) {
					amount = Double.parseDouble(request.getParameter("amount"));
				}

				String description = request.getParameter("description");
				String chequeNo = request.getParameter("chequeNo");

				LocalDate todayLocalDate = LocalDate.now();
				java.sql.Date currentDate = java.sql.Date.valueOf(todayLocalDate);

				Double deposit = 0.0;
				Double withdraw = 0.0;

				if (beneficiaryAccountNumber.isEmpty() || amount == 0.0) {
					session.setAttribute("message", "Account Number and Amount can't be empty !");
					RequestDispatcher dispatcher = request.getRequestDispatcher("debitOrCredit.jsp");
					dispatcher.include(request, response);
					dispatcher.forward(request, response);
					out.println("<script type=\"text/javascript\">");
					out.println("alert('Account Number and Amount can't be empty !');");
					out.println("</script>");

				} else {
					String sql = "CREATE TABLE IF NOT EXISTS " + "transactions"
							+ "  (accountNumber            varchar(200),"
							+ "   beneficiaryAccountNumber          varchar(200)," + "   date           date,"
							+ "   description           varchar(200)," + "   chequeNo     VARCHAR(100),"
							+ "   withdraw     double," + "   deposit     double," + "   balance double)";

					statement = connection.createStatement();
					Boolean status = statement.execute(sql);

					sql = "select * from account_details where accountNumber='" + beneficiaryAccountNumber + "'";

					ResultSet rs = statement.executeQuery(sql);

					if (rs.next()) {
						sql = "Select accountNumber, balance from account_details where accountNumber='"
								+ session.getAttribute("session_accountNumber") + "'";
						rs = statement.executeQuery(sql);

						String accountNumber = "";
						Double balance = 0.0;
						while (rs.next()) {
							accountNumber = rs.getString("accountNumber");
							balance = rs.getDouble("balance");
						}

						sql = "Select accountNumber, balance from account_details where accountNumber='"
								+ beneficiaryAccountNumber + "'";
						rs = statement.executeQuery(sql);

						Double beneficiaryBalance = 0.0;
						while (rs.next()) {
							beneficiaryBalance = rs.getDouble("balance");
						}

						String submitButton = request.getParameter("submitButton");

						if (submitButton.equals("Debit From")) {
							balance += amount;
							beneficiaryBalance -= amount;
							withdraw = amount;
						} else if (submitButton.equals("Credit To")) {
							balance -= amount;
							beneficiaryBalance += amount;
							deposit = amount;
						} else {
							RequestDispatcher dispatcher = request.getRequestDispatcher("debitOrCredit.jsp");
							dispatcher.include(request, response);
						}

						PreparedStatement ps = connection
								.prepareStatement("UPDATE account_details SET balance = ? where accountNumber = ?;");

						ps.setDouble(1, balance);
						ps.setString(2, accountNumber);
						status = ps.execute();

						ps.setDouble(1, beneficiaryBalance);
						ps.setString(2, beneficiaryAccountNumber);
						status = ps.execute();

						ps = connection.prepareStatement(
								"insert into transactions(accountNumber, beneficiaryAccountNumber, date, description, chequeNo, withdraw, deposit, balance) values(?,?,?,?,?,?,?,?)");
						ps.setString(1, accountNumber);
						ps.setString(2, beneficiaryAccountNumber);
						ps.setDate(3, currentDate);
						ps.setString(4, description);
						ps.setString(5, chequeNo);
						ps.setDouble(6, withdraw);
						ps.setDouble(7, deposit);
						ps.setDouble(8, balance);

						status = ps.execute();

						String query = "SELECT balance from account_details where accountNumber = " + accountNumber;
						Boolean checkBalance1 = false;
						ResultSet resultSet = statement.executeQuery(query);

						while (resultSet.next()) {
							if (resultSet.getDouble("balance") == balance) {
								checkBalance1 = true;
							}
						}

						query = "SELECT balance from account_details where accountNumber = " + beneficiaryAccountNumber;
						Boolean checkBalance2 = false;
						resultSet = statement.executeQuery(query);

						while (resultSet.next()) {
							if (resultSet.getDouble("balance") == beneficiaryBalance) {
								checkBalance2 = true;
							}
						}

						statement.close();
						ps.close();

						if (checkBalance1 && checkBalance2) {
							session.setAttribute("message", "Transaction Successfull!");
							RequestDispatcher dispatcher = request.getRequestDispatcher("debitOrCredit.jsp");
							dispatcher.include(request, response);
							dispatcher.forward(request, response);
							out.println("<script type=\'text/javascript\'>");
							out.println("alert('Transaction Successfull!');");
							out.println("</script>");
						} else {
							session.setAttribute("message", "Transaction Failed, Try Again!");
							RequestDispatcher dispatcher = request.getRequestDispatcher("debitOrCredit.jsp");
							dispatcher.include(request, response);
							dispatcher.forward(request, response);
							out.println("<script type=\'text/javascript\'>");
							out.println("alert('Transaction Failed, Try Again!');");
							out.println("</script>");
						}
					} else {
						session.setAttribute("message", "Enter Valid Account Number!");
						RequestDispatcher dispatcher = request.getRequestDispatcher("debitOrCredit.jsp");
						dispatcher.include(request, response);
						dispatcher.forward(request, response);
						out.println("<script type=\"text/javascript\">");
						out.println("alert('Enter Valid Account Number!`);");
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
