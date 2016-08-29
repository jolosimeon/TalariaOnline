<%@page import="objects.HTMLInputFilter"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Login</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js">
	</script>
 	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js">
 	</script>
 	<link rel="stylesheet" type="text/css" href="css/styles.css">
 </head>
 <body>
 <%
 	String error = request.getParameter("error");
 	
 %>
 
 	<nav class="navbar navbar-custom">
  		<div class="navbar-header">
            <a class="navbar-link navbar-brand" href="index.jsp">Talaria Online</a>
        </div>
  </nav>
  <div class="container pagecontent">
    <div class="row">
      
      <!-- Article main content -->
      <article class="col-xs-12 maincontent">
        <div class="headroom"></div>
        
        <div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
          <div class="panel panel-default">
            <div class="panel-body">
              <h3 class="thin text-center">Log in to Your Account</h3>
              <p class="text-center text-muted">Don't have an account? Click <a href="signup.jsp">Sign Up</a>
              to sign up! </p>
              <hr>
              <% if (error != null && error.equals("1")) { %>
              	<font style ="color:red"> Invalid username or password.</font>
              <% } %>
              
              <form action= "Login" method="post" id = "loginform" class="form-signin">
                <div class="top-margin">
                  <label>Username <span class="text-danger">*</span></label>
                  <input type="text" class="form-control" name = "user" id = "user" required>
                </div>
                <div class="top-margin">
                  <label>Password <span class="text-danger">*</span></label>
                  <input type="password" class="form-control" name = "pwd" id = "pwd" required>
                </div>

                <hr>

                <div class="row">
                  <div class="col-lg-8">
                    <!--<b><a href="">Forgot password?</a></b>-->
                  </div>
                  <div class="col-lg-4 text-right">
                    <button class="btn btn-action" type="submit">Sign in</button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </article>
    </div>
    <!--  <div class="row navbar-fixed-bottom">
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
	
	<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="http://jqueryvalidation.org/files/dist/jquery.validate.min.js"></script>
	<script src="http://jqueryvalidation.org/files/dist/additional-methods.min.js"></script>
 </body>
</html>