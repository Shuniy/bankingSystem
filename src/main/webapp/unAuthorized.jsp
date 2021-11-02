<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1" />
    <title>Card Unauthorized</title>
    <link rel="stylesheet" href="bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="styles.css" />
  </head>
  <body>
    <div class="container-fluid unauthorized">
      <h3>
        Card is not Authorized to make a transaction of :
        <%=request.getSession().getAttribute("session_amount").toString() %>
      </h3>
      <h3>
        Please Check card details or transaction limit as it is less than
        100000.
      </h3>
      <form method="post" action="authorizeCreditCard.jsp">
        <input type="submit" class="btn btn-outline-success" value="Check Again" />
      </form>
      <form action="homepage.jsp">
        <input type="submit" class="btn btn-info" name="submitButton" value="Homepage" />
      </form>
    </div>
  </body>
</html>
