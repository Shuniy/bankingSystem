<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1" />
    <title>Debit or Credit Amount</title>
    <link rel="stylesheet" href="bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="styles.css">
  </head>
  <body>
    <div class="container-fluid dbcd">
      <h2>Make a Transaction</h2>
      <% String message = ""; if(!(request.getSession().getAttribute("message")
      == null)){ message =
      request.getSession().getAttribute("message").toString(); }
      if(!message.isEmpty()){ %>
      <h5><%= message %></h5>
      <%}%>
      <form action="DebitCredit" method="post">
        <div class="form-group">
          <label class="form-label" for="beneficiaryAccountNumber"
            >Enter Beneficiary Account Number</label
          >
          <input
            type="number"
            class="form-control"
            id="beneficiaryAccountNumber"
            name="beneficiaryAccountNumber"
            placeholder="Beneficiary Account Number"
          />
        </div>

        <div class="form-group">
          <label class="form-label" for="amount">Enter Amount</label>
          <input
            id="amount"
            type="number"
            class="form-control"
            name="amount"
            placeholder="Amount"
            value="0"
          />
        </div>

        <div class="form-group">
          <label class="form-label" for="description">Enter Description</label>
          <textarea
            id="description"
            class="form-control"
            name="description"
            placeholder="Description"
          ></textarea>
        </div>

        <div class="form-group">
          <label class="form-label" for="chequeNo">Enter Cheque Number</label>
          <input
            id="chequeNo"
            type="text"
            class="form-control"
            name="chequeNo"
            placeholder="Cheque No."
            value=""
          />
        </div>

        <input
          type="submit"
          class="btn btn-outline-success"
          name="submitButton"
          value="Debit From"
        />
        <input
          type="submit"
          class="btn btn-outline-danger"
          name="submitButton"
          value="Credit To"
        />
      </form>
      <form action="homepage.jsp">
        <input type="submit" class="btn btn-info" name="submitButton" value="Homepage" />
      </form>
    </div>
  </body>
</html>
