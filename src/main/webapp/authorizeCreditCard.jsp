<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1" />
    <title>Authorize Credit Card</title>
    <link rel="stylesheet" href="bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="styles.css">
  </head>
  <body>
    <div class="container-fluid authorize">
      <h3>Authorize Credit Card</h3>
      <% String message = ""; if(!(request.getSession().getAttribute("message")
      == null)){ message =
      request.getSession().getAttribute("message").toString(); }
      if(!message.isEmpty()){ %>
      <h5><%= message %></h5>
      <%}%>
      <form action="Authorize" method="post">
        <div class="form-group">
          <label class="form-label" class="form-label class="form-label"" for="name">Enter Name</label>
          <input
            type="text"
            class="form-control"
            name="name"
            placeholder="Name"
          />
        </div>

        <div class="form-group">
          <label class="form-label" class="form-label" for="cardNumber">Enter Card Number</label>
          <input
            type="number"
            class="form-control"
            id="cardNumber"
            name="cardNumber"
            placeholder="Credit Card Number"
          />
        </div>

        <div class="form-group">
          <label class="form-label" for="cvv">Enter CVV/CVV2</label>
          <input
            type="number"
            class="form-control"
            id="cvv"
            name="cvv"
            placeholder="CVV"
          />
        </div>

        <div class="form-group">
          <label class="form-label" for="amount">Amount</label>
          <input
            type="number"
            class="form-control"
            name="amount"
            id="amount"
            placeholder="0"
          />
        </div>
        <input type="submit" class="btn btn-outline-success" value="Authorize" />
      </form>
      <form action="homepage.jsp">
        <input type="submit" class="btn btn-info" name="submitButton" value="Homepage" />
      </form>
    </div>
  </body>
</html>
