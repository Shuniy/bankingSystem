<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1" />
    <title>Card Authorized</title>
    <link rel="stylesheet" href="bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="styles.css" />
  </head>
  <body>
    <div class="container-fluid authorized">
      <h3>
        Card is Authorized to make a transaction of :
        <%=request.getSession().getAttribute("session_amount").toString() %>
      </h3>

      <form method="post" action="authorizeCreditCard.jsp">
        <input type="submit" class="btn btn-outline-success" value="Check other cards" />
      </form>
      <form action="homepage.jsp">
        <input type="submit" class="btn btn-info" name="submitButton" value="Homepage" />
      </form>
    </div>
  </body>
</html>
