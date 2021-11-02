<%@page import="java.sql.ResultSet"%> <%@ page language="java"
contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1" />
    <title>All Transactions</title>
    <link rel="stylesheet" href="bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="styles.css">
  </head>
  <body>
    <div class="container-fluid transactions">
      <form method="post" action="transactionsTable.jsp">
        <h2>Date Range</h2>
        <div class="form-group">
          <label class="form-label" for="fromDate">From Date</label>
          <input
            type="date"
            class="form-control"
            id="fromDate"
            name="fromDate"
            placeholder="From Date"
          />
        </div>
        <div class="form-group">
          <label class="form-label" for="toDate">To Date</label>
          <input
            type="date"
            class="form-control"
            id="toDate"
            name="toDate"
            placeholder="To Date"
          />
        </div>

        <input type="submit" class="btn btn-outline-success" value="Submit" />
      </form>
      <form action="homepage.jsp">
        <input type="submit" class="btn btn-info" name="submitButton" value="Homepage" />
      </form>
    </div>
  </body>
</html>
