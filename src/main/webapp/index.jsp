<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <title>Banking System</title>
    <meta charset="windows-1252" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="styles.css" type="text/css" />
  </head>
  <body>
    <div class="container-fluid container">
      <h1>Banking System</h1>
      <%session.invalidate(); %>
      <form action="LoginCheck" method="post">
        <div class="form-group">
          <input
            type="number"
            class="form-control"
            name="accountNumber"
            placeholder="Account Number"
          />
        </div>
        <div class="form-group">
          <input
            type="password"
            class="form-control"
            name="password"
            placeholder="Password"
          />
        </div>
        <input type="submit" class="btn" value="Login" />
      </form>
    </div>
  </body>
</html>
