<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%@page import="java.sql.*" %> <% String BankAccount = ""; if
(request.getParameter("BankAccountNumber") == null) { BankAccount = ""; } else {
BankAccount = request.getParameter("BankAccountNumber"); } String accountNumber
= request.getSession().getAttribute("session_accountNumber").toString(); String
password = request.getSession().getAttribute("session_password").toString();
String userAccountNumberCreated="";
if(!(request.getSession().getAttribute("userAccountCreated") == null)){
userAccountNumberCreated =
request.getSession().getAttribute("userAccountCreated").toString(); } String
userCardAllocated="";
if(!(request.getSession().getAttribute("userCardAllocated") == null)){
userCardAllocated =
request.getSession().getAttribute("userCardAllocated").toString(); } String
userCvv=""; if(!(request.getSession().getAttribute("userCvv") == null)){ userCvv
= request.getSession().getAttribute("userCvv").toString(); } %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Home Page</title>
    <link rel="stylesheet" href="bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="styles.css">
  </head>
  <body>
    <div class="container-fluid homepage" >
      <%if(!userAccountNumberCreated.isEmpty()){%>
      <h5>
        New Account Created with Account Number : <%= userAccountNumberCreated
        %>
      </h5>
      <%}%> <%if(!userCardAllocated.isEmpty()){%>
      <h5>Card Number allocated to New Account : <%= userCardAllocated %></h5>
      <%}%> <%if(!userCvv.isEmpty()){%>
      <h5>CVV allocated to New Card : <%= userCvv %></h5>
      <%}%> <%if(accountNumber.isEmpty()) {%>
      <h5>Go Back and Login Again !</h5>
      <%} else { %>
      <h4>Account Number Logged In: <%= accountNumber %></h4>
      <%} %>
      <form action="createAccount.jsp" method="Get">
        <% if (accountNumber.isEmpty()) {%>
        <input
          type="submit"
          class="btn btn-lg btn-primary"
          value="Create Account"
          disabled
        />
        <% } else {%>
        <input type="submit" class="btn btn-primary" value="Create Account" />
        <%}%>
      </form>
      <form action="debitOrCredit.jsp" method="Post">
        <% if (accountNumber.isEmpty()) {%>
        <input
          type="submit"
          class="btn btn-secondary btn-lg"
          value="Debit or Credit"
          disabled
        />
        <% } else {%>
        <input type="submit" class="btn btn-outline-primary" value="Debit or Credit" />
        <%}%>
      </form>
      <form action="transactions.jsp" method="Post">
        <% if (accountNumber.isEmpty()) {%>
        <input
          type="submit"
          class="btn btn-secondary btn-lg"
          value="Transactions"
          disabled
        />
        <% } else {%>
        <input type="submit" class="btn btn-outline-primary" value="Transactions" />
        <%}%>
      </form>
      <form action="authorizeCreditCard.jsp" method="Post">
        <% if (accountNumber.isEmpty()) {%>
        <input
          type="submit"
          class="btn btn-secondary btn-lg"
          value="Authorize Credit Card"
          disabled
        />
        <% } else {%>
        <input
          type="submit"
          class="btn btn-outline-primary"
          value="Authorize Credit Card"
        />
        <%}%>
      </form>
      <form action="index.jsp" method="Get">
        <input type="submit" class="btn btn-danger" value="Logout" />
      </form>
    </div>
  </body>
</html>
