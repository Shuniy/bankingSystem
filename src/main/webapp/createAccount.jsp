<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1" />
    <title>Create Account</title>
    <link rel="stylesheet" href="bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="styles.css">
  </head>

  <body>
    <div class="container-fluid create">
      <form action="CreateAccount" method="post">
        <div class="form-group">
          <label class="form-label" for="name">Enter Name</label>
          <input
            type="text"
            class="form-control"
            name="name"
            placeholder="Name"
          />
        </div>

        <div class="form-group">
          <label class="form-label" for="dob">Select Date of Birth</label>
          <input
            type="date"
            class="form-control"
            id="dob"
            name="dob"
            placeholder="Date of Birth"
          />
        </div>

        <div class="form-group">
          <label class="form-label" for="address">Enter Address</label>
          <textarea
            class="form-control"
            id="address"
            name="address"
            placeholder="Address"
          ></textarea>
        </div>

        <div class="form-group">
          <label class="form-label" for="email">Enter Email</label>
          <input
            type="email"
            id="email"
            class="form-control"
            name="email"
            placeholder="Email"
          />
        </div>

        <div class="form-group">
          <label class="form-label" for="accountType">Choose a type of bank Account:</label>
          <select name="accountType" class="form-select mb-3" id="accountType">
            <option value="salary">Salary Account</option>
            <option value="saving">Savings Account</option>
            <option value="current">Current Account</option>
          </select>
        </div>
        <input type="submit" class="btn btn-primary" value="Create Account" />
      </form>
    </div>
  </body>
</html>
