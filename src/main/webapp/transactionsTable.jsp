<%@page import="java.sql.DriverManager"%> <%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%> <%@page import="java.sql.Connection"%> <%
String from_date=request.getParameter("fromDate"); String
to_date=request.getParameter("toDate"); String accountNumber =
request.getSession().getAttribute("session_accountNumber").toString(); String
driver = "com.mysql.cj.jdbc.Driver"; String connectionUrl =
"jdbc:mysql://localhost:3306/"; String database = "bank"; String userid =
"root"; String password = "kumar.shubham"; try { Class.forName(driver); } catch
(ClassNotFoundException e) { e.printStackTrace(); } Connection connection =
null; Statement statement = null; ResultSet resultSet = null; %>
<!DOCTYPE html>

<html>
  <head>
    <link rel="stylesheet" href="bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="styles.css">
  </head>
  <body>
    <div class="container-fluid tr-table">
      <h3>Transactions</h3>
      <table class="table table-hover" border="1">
        <thead>
          <tr>
            <th>SL. No.</th>
            <th>Account Number</th>
            <th>Beneficiary Account No.</th>
            <th>Date</th>
            <th>Description</th>
            <th>Cheque No.</th>
            <th>Withdraw</th>
            <th>Deposit</th>
            <th>Available Balance</th>
          </tr>
        </thead>
        <tbody>
          <% try{ connection =
          DriverManager.getConnection(connectionUrl+database, userid, password);
          statement=connection.createStatement(); String sql ="select * from transactions where date between '" + from_date + "' and '" + to_date + "' and accountNumber = '" + accountNumber + "'"; 
          resultSet = statement.executeQuery(sql); int i=1; while(resultSet.next()) { %>

          <tr>
            <td><%= i %></td>
            <%i++; %>
            <td><%=resultSet.getString("accountNumber") %></td>
            <td><%=resultSet.getString("beneficiaryAccountNumber") %></td>
            <td><%=resultSet.getString("date") %></td>
            <td><%=resultSet.getString("description") %></td>
            <td><%=resultSet.getString("chequeNo") %></td>
            <td><%=resultSet.getDouble("withdraw") %></td>
            <td><%=resultSet.getDouble("deposit") %></td>
            <td><%=resultSet.getDouble("balance") %></td>
          </tr>
          <% } connection.close(); } catch (Exception e) { e.printStackTrace();
          } %>
        </tbody>
      </table>

      <form method="post" action="transactions.jsp">
        <input type="submit" class="btn btn-outline-success" value="Go Back" />
      </form>

      <form method="post" action="homepage.jsp">
        <input type="submit" class="btn btn-info" value="Homepage" />
      </form>
    </div>
  </body>
</html>
