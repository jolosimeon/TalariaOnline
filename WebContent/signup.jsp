<%@page import="objects.HTMLInputFilter"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Register an Account</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js">
	</script>
 	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js">
 	</script>
 	<link rel="stylesheet" type="text/css" href="css/styles.css">
 	<link rel="stylesheet" href="http://jqueryvalidation.org/files/demo/site-demos.css">
 </head>
 <body>
     	<nav class="navbar navbar-custom" style="margin-bottom:0px;">
          <div class="navbar-header">
                <a class="navbar-link navbar-brand" href="index.jsp">Talaria Online</a>
            </div>
      </nav>
  <div class="container pagecontent">
        <div class="headroom"></div>        
        <div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
          <div class="panel panel-default">
            <div class="panel-body">
              <h3 class="thin text-center">Sign Up for a New Account</h3>
              <p class="text-center text-muted">Have an account already? <a href="login.jsp">Login!</a> </p>
              <hr>
            <form id = "signUpForm" action = "RegisterUser" method = "post" class="form-signin">
                <div class=" row top-margin">
                  <div class="col-sm-4">
                    <label class="control-label">First Name <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" name = "first_name" id = "first_name" placeholder = "First Name" required>
                  </div>
                  <div class="col-sm-3">
                    <label class="control-label">Middle Initial <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" name = "mid_initial" id = "mid_initial" placeholder = "M.I." required>
                  </div>
                  <div class="col-sm-5">
                    <label>Last Name <span class="text-danger">*</span> </label>
                    <input type="text" class="form-control" name = "last_name" id = "last_name" placeholder = "Last Name" required>
                  </div>
                </div>
                <div class=" row top-margin">
                  <div class="col-sm-6">
                    <label>Username <span class="text-danger">*</span> </label>
                    <input type="text" class="form-control" name = "username" id = "username" placeholder = "Username" required>
                  </div>
                  <div class="col-sm-6">
                    <label>Email Address <span class="text-danger">*</span> </label>
                    <input type="text" class="form-control" name = "email" placeholder = "Email" id = "email" required>
                  </div>
                </div>
                <div class="row top-margin">
                  <div class="col-sm-6">
                    <label>Password <span class="text-danger">*</span></label>
                    <input type="password" class="form-control" name = "pwd" placeholder = "Password" id = "pwd" required>
                  </div>
                  <div class="col-sm-6">
                    <label>Confirm Password <span class="text-danger" >*</span></label>
                    <input type="password" class="form-control" name = "confirmpwd" placeholder = "Re-enter Password" id = "confirmpwd" required>
                  </div>
                </div>
                <hr>
                <div class=" row top-margin">
                  <div class="col-md-12">
                    <label>Billing Address: <span class="text-danger">*</span> </label>
                  </div>
                  <div class="col-sm-2">
                    <label>House #<span class="text-danger"></span> </label>
                    <input type="text" class="form-control" name = "bill_housenum" id = "bill_housenum" placeholder = "#" required>
                  </div>
                  <div class="col-sm-5">
                    <label>Street Name <span class="text-danger"></span> </label>
                    <input type="text" class="form-control" name = "bill_stname" id = "bill_stname" placeholder = "Street" required>
                  </div>
                  <div class="col-sm-5">
                    <label>Subdivision <span class="text-danger"></span> </label>
                    <input type="text" class="form-control" name = "bill_subd" id = "bill_subd" placeholder = "Subdivision" required>
                  </div>
                  <div class="col-sm-4">
                    <label>City <span class="text-danger"></span> </label>
                    <input type="text" class="form-control" name = "bill_city" id = "bill_city" placeholder = "City/Province" required>
                  </div>
                  <div class="col-sm-3">
                    <label>Postal Code <span class="text-danger"></span> </label>
                    <input type="text" class="form-control" name = "bill_postcode" id = "bill_postcode" placeholder = "Postal Code" required>
                  </div>
                  <div class="col-sm-5">
                    <label>Country <span class="text-danger"></span> </label>
                    <input type="text" class="form-control" name = "bill_country" id = "bill_country" placeholder = "Country" required>
                  </div>
                </div>
                <hr>
                <div class=" row top-margin">
                  <div class="col-md-12">
                    <label>Shipping Address: <span class="text-danger">*</span> </label>
                  </div>
                  <div class="col-sm-2">
                    <label>House #<span class="text-danger"></span> </label>
                    <input type="text" class="form-control" name = "ship_housenum" id = "ship_housenum" placeholder = "#" required>
                  </div>
                  <div class="col-sm-5">
                    <label>Street Name <span class="text-danger"></span> </label>
                    <input type="text" class="form-control" name = "ship_stname" id = "ship_stname" placeholder = "Street" required>
                  </div>
                  <div class="col-sm-5">
                    <label>Subdivision <span class="text-danger"></span> </label>
                    <input type="text" class="form-control" name = "ship_subd" id = "ship_subd" placeholder = "Subdivision" required>
                  </div>
                  <div class="col-sm-4">
                    <label>City <span class="text-danger"></span> </label>
                    <input type="text" class="form-control" name = "ship_city" id = "ship_city" placeholder = "City/Province" required>
                  </div>
                  <div class="col-sm-3">
                    <label>Postal Code <span class="text-danger"></span> </label>
                    <input type="text" class="form-control" name = "ship_postcode" id = "ship_postcode" placeholder = "Postal Code" required>
                  </div>
                  <div class="col-sm-5">
                    <label>Country <span class="text-danger"></span> </label>
                    <input type="text" class="form-control" name = "ship_country" id = "ship_country" placeholder = "Country" required>
                  </div>
                </div>
                <hr>
                <div class=" row top-margin">
                  <div class="col-sm-6">
                    <label>Credit Card Number<span class="text-danger">*</span> </label>
                    <input type="text" class="form-control" name = "credit_card" id = "credit_card" placeholder = "Credit Card Number" required>
                  </div>
                  </div>
                  <hr>
                <div class="row">
                     <div class="col-lg-8">
                        <div class="col-lg-12 text-right">
                        <button class="btn btn-action" type="submit">Sign Up</button>
                        </div>
                    </div>
            
                </div>
              </form>
            </div>
          </div>

    </div>
    </div>
    <!--  <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>-->
	<script src="http://jqueryvalidation.org/files/dist/jquery.validate.min.js"></script>
	<script src="http://jqueryvalidation.org/files/dist/additional-methods.min.js"></script>
    
    <script>
	$( "#signUpForm" ).validate({
	  rules: {
		email: {
			email:true
		},  
	    pwd: "required",
	    confirmpwd: {
	      equalTo: "#pwd"
	    },
	    id_num : {
	    	remote: "checkID.jsp"
	    }
	  },
	  messages: {
          id_num: {
              remote: "That ID number already exists."
          }
	  }
	});
	</script>

    <!--<div class="row navbar-fixed-bottom">
      <div class = "col-md-12">
        <hr class = "break"/>
        <ul>
          <li class = "footer">? 2014</li>
          <li class = "footer"><a href = "#aboutus">About Us</a></li>
          <li class = "footer"><a href = "#contactus">Contact Us</a></li>
        </ul>
        <p style = "padding-left:4em">Icons from Glyphicons Free.</p>
    </div>
  </div>-->
 </body>
</html>